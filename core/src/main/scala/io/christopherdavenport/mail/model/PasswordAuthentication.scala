package io.christopherdavenport.mail.model

import language.implicitConversions
import javax.mail.{PasswordAuthentication => jPasswordAuthentication}

/**
  * Created by davenpcm on 7/10/16.
  */
case class PasswordAuthentication(
                                 userName: String,
                                 password: String
                                 )

//object PasswordAuthentication {

//  implicit class AsJava(passwordAuthentication: PasswordAuthentication){
//    def asJava: jPasswordAuthentication = {
//      new jPasswordAuthentication(passwordAuthentication.userName, passwordAuthentication.password)
//    }
//  }


//}
