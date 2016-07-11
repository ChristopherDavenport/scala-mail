package io.christopherdavenport.mail.model

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