package io.christopherdavenport.mail.language



import language.implicitConversions
import io.christopherdavenport.mail.model.{Authenticator, InternetAddress, Message, PasswordAuthentication, Session}
import io.christopherdavenport.mail.model.RecipientTypes.{BCC, CC, TO}
import javax.mail.{Address => jAddress, Authenticator => jAuthenticator, PasswordAuthentication => jPasswordAuthentication, Session => jSession, Transport => jTransport}
import javax.mail.internet.{InternetAddress => jInternetAddress}
import java.util.{Properties => jProperties}
import javax.mail.internet.{MimeMessage => jMimeMessage}

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

      setText(m.bodyContent)
    }
  }


}
