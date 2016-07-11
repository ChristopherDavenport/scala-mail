package io.christopherdavenport.mail.language

import language.implicitConversions
import io.christopherdavenport.mail.model.{Authenticator, InternetAddress, Message, PasswordAuthentication, Session}
import javax.mail.{Authenticator => jAuthenticator}
import javax.mail.{PasswordAuthentication => jPasswordAuthentication}
import javax.mail.internet.{InternetAddress => jInternetAddress}
import javax.mail.{Session => jSession}
import javax.mail.{Message => jMessage}

import JavaConversions.PasswordAuthenticationConversion
import JavaConversions.AuthenticatorConversion
import JavaConversions.InternetAddressConversion
import JavaConversions.SessionConversion
import JavaConversions.MessageConversion
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

}
