package io.christopherdavenport.mail.model

import javax.mail.{Authenticator => jAuthenticator}
import javax.mail.{PasswordAuthentication => jPasswordAuthentication}
import language.implicitConversions
/**
  * Created by davenpcm on 7/10/16.
  */
case class Authenticator(passwordAuthentication: PasswordAuthentication)

object Authenticator {
  implicit class AsJava(authenticator: Authenticator){
    def asJava : jAuthenticator = {
      new jAuthenticator {
        protected override def getPasswordAuthentication: jPasswordAuthentication =
          authenticator.passwordAuthentication.asJava
      }
    }
  }
}
