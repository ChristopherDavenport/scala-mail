package io.christopherdavenport.mail.language



import language.implicitConversions
import io.christopherdavenport.mail.model.{Attachment, Authenticator, ByteAttachment, Html, InternetAddress, Message, Multipart, PasswordAuthentication, Session, Text}
import io.christopherdavenport.mail.model.RecipientTypes.{BCC, CC, TO}
import javax.mail.{Address => jAddress, Authenticator => jAuthenticator, PasswordAuthentication => jPasswordAuthentication, Session => jSession, Transport => jTransport}
import javax.mail.internet.{InternetAddress => jInternetAddress}
import java.util.{Properties => jProperties}
import javax.activation.{DataHandler, FileDataSource}
import javax.mail.internet.{MimeMessage => jMimeMessage}
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMultipart
import javax.mail.util.ByteArrayDataSource

/**
  * Created by davenpcm on 7/11/16.
  */
object JavaConversions {

  implicit def AuthenticatorConversion(a: Authenticator): jAuthenticator = {
    new jAuthenticator {
      protected override def getPasswordAuthentication: jPasswordAuthentication = a.passwordAuthentication
    }
  }

  implicit def PasswordAuthenticationConversion(p: PasswordAuthentication): jPasswordAuthentication = {
    new jPasswordAuthentication(p.userName, p.password)
  }

  implicit def InternetAddressConversion(ia: InternetAddress): jInternetAddress = {
    new jInternetAddress(ia.address, ia.personalName.orNull, ia.charset.orNull)
  }

  implicit def InternetAddressAsJavaAddressConversion(ia: InternetAddress): jAddress = {
    InternetAddressConversion(ia).asInstanceOf[jAddress]
  }

  implicit def SessionConversion(s: Session): jSession = {

    val properties = new jProperties{
      put("mail.smtp.host", s.host)
      put("mail.smtp.port", s.port.toString)

      s.passwordAuthentication.map(authBool => put("mail.smtp.auth", true.toString))
      s.useTtls.map(useTtlsBool => put("mail.smtp.starttls.enable", useTtlsBool.toString))
      s.socketFactory.map(customSF => put("mail.smtp.scoketFactory.class", customSF))
    }

    s.passwordAuthentication match {
      case None =>
        jSession.getInstance(properties)
      case Some(credentials) =>
        jSession.getInstance(properties, Authenticator(credentials) )
    }

  }

  implicit def MessageConversion(m: Message): jMimeMessage = {

    new jMimeMessage(m.session){
      setFrom(InternetAddressAsJavaAddressConversion(m.from))
      m.subject.foreach(setSubject(_))

      val recipientByType = m.recipients.groupBy(_.recipientType)

      val to = recipientByType.get(TO)
        .map(o =>
          o.map(r => InternetAddressAsJavaAddressConversion(r.internetAddress)
          ).toArray
        )
      to.foreach(addRecipients(TO.asJava, _))
      val cc = recipientByType.get(CC)
        .map(o =>
          o.map(r =>
            InternetAddressAsJavaAddressConversion(r.internetAddress)
          ).toArray
        )
      cc.foreach(addRecipients(TO.asJava, _))
      val bcc = recipientByType.get(BCC)
        .map(o =>
          o.map(r =>
            InternetAddressAsJavaAddressConversion(r.internetAddress)
          ).toArray
        )
      bcc.foreach(addRecipients(TO.asJava, _))


      m.replyTo.foreach(replyTo =>
        setReplyTo(replyTo.map(a => InternetAddressAsJavaAddressConversion(a)).toArray))

      setContent(m.bodyContent)
    }
  }

  implicit def HtmlMimePartConversion(html: Html): MimeBodyPart = {
    new MimeBodyPart{
      setContent(html.content, "text/html")
    }
  }

  implicit def TextMimePartConversion(text: Text): MimeBodyPart = {
    new MimeBodyPart{
      setContent(text.content, "text/plain")
    }
  }

  implicit def AttachmentMimePartConversion(attachment: Attachment): MimeBodyPart = {
    new MimeBodyPart{
      setDataHandler(new DataHandler(new FileDataSource(attachment.file)))
      setFileName(attachment.file.getName)
    }
  }

  implicit def ByteAttachmentMimePartConversion(byteAttachment: ByteAttachment): MimeBodyPart = {
    new MimeBodyPart{
      setDataHandler(new DataHandler(new ByteArrayDataSource(byteAttachment.bytes, byteAttachment.mimeType)))
      setFileName(byteAttachment.name)
    }
  }

  implicit def MultipartConversion(multiPart: Multipart): MimeMultipart = {
    val jSeq : Seq[MimeBodyPart] = multiPart.seq.map{
      case t : Text => TextMimePartConversion(t)
      case h : Html => HtmlMimePartConversion(h)
      case a : Attachment => AttachmentMimePartConversion(a)
      case b : ByteAttachment => ByteAttachmentMimePartConversion(b)
    }
    new MimeMultipart(){
      jSeq.foreach(addBodyPart(_))
    }
  }


}
