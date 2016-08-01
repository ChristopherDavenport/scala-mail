package io.christopherdavenport.mail.model

/**
  * Created by davenpcm on 7/10/16.
  */
case class InternetAddress(
                            address: String,
                            personalName: Option[String] = None,
                            charset: Option[String] = None
                          )

