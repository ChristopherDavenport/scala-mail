package io.christopherdavenport.mail.language

import language.implicitConversions
import io.christopherdavenport.mail.model.{Attachment, Authenticator, ByteAttachment, Html, InternetAddress, Message, Multipart, PasswordAuthentication, Session, Text}
import javax.mail.{Authenticator => jAuthenticator}
import javax.mail.{PasswordAuthentication => jPasswordAuthentication}
import javax.mail.internet.{InternetAddress => jInternetAddress}
import javax.mail.{Session => jSession}
import javax.mail.{Message => jMessage}
import javax.mail.internet.{MimeBodyPart => jMimePart}
import javax.mail.internet.{MimeMultipart => jMultipart}

import JavaConversions.PasswordAuthenticationConversion
import JavaConversions.AuthenticatorConversion
import JavaConversions.InternetAddressConversion
import JavaConversions.SessionConversion
import JavaConversions.MessageConversion
import JavaConversions.AttachmentMimePartConversion
import JavaConversions.ByteAttachmentMimePartConversion
import JavaConversions.HtmlMimePartConversion
import JavaConversions.TextMimePartConversion
import JavaConversions.MultipartConversion
/**
  * Created by davenpcm on 7/11/16.
  */
object JavaConverters {

  class AsJava[C](op: => C){
    def asJava: C = op
  }

  implicit def AuthenticatorAsJava(a: Authenticator): AsJava[jAuthenticator] = {
    new AsJava(AuthenticatorConversion(a))
  }

  implicit def PasswordAuthenticationAsJava(p: PasswordAuthentication): AsJava[jPasswordAuthentication] = {
    new AsJava(PasswordAuthenticationConversion(p))
  }

  implicit def InternetAddressAsJava(ia: InternetAddress): AsJava[jInternetAddress] = {
    new AsJava(InternetAddressConversion(ia))
  }

  implicit def SessionAsJava(s: Session): AsJava[jSession] = {
    new AsJava(SessionConversion(s))
  }

  implicit def MessageAsJava(m: Message): AsJava[jMessage] = {
    new AsJava(MessageConversion(m))
  }

  implicit def AttachmentAsJava(a: Attachment): AsJava[jMimePart] = {
    new AsJava(AttachmentMimePartConversion(a))
  }

  implicit def ByteAttachmentAsJava(b: ByteAttachment): AsJava[jMimePart] = {
    new AsJava(ByteAttachmentMimePartConversion(b))
  }

  implicit def TextAsJava(t: Text): AsJava[jMimePart] = {
    new AsJava(TextMimePartConversion(t))
  }

  implicit def HtmlAsJava(h: Html): AsJava[jMimePart] = {
    new AsJava(HtmlMimePartConversion(h))
  }

  implicit def MultipartAsJava(m: Multipart): AsJava[jMultipart] = {
    new AsJava(MultipartConversion(m))
  }

}
