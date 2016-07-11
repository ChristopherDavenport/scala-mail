package io.christopherdavenport.mail.model

import language.implicitConversions
import javax.mail.{Session => jSession}
import java.util.{Properties => jProperties}
/**
  * Created by davenpcm on 7/10/16.
  */
case class Session(
                    host: String,
                    port: Int,
                    passwordAuthentication: Option[PasswordAuthentication],
                    useTtls: Option[Boolean] = None,
                    socketFactory: Option[String] = None
                  )

object Session {
  implicit class AsJava(session: Session){
    def asJava: jSession = {

      val properties = new jProperties{
        put("mail.smtp.host", session.host)
        put("mail.smtp.port", session.port.toString)

        session.passwordAuthentication.map(authBool => put("mail.smtp.auth", true.toString))
        session.useTtls.map(useTtlsBool => put("mail.smtp.starttls.enable", useTtlsBool.toString))
        session.socketFactory.map(customSF => put("mail.smtp.scoketFactory.class", customSF))
      }

      session.passwordAuthentication match {
        case None =>
          jSession.getInstance(properties)
        case Some(credentials) =>
          jSession.getInstance(properties, Authenticator(credentials).asJava )
      }

    }
  }



}
