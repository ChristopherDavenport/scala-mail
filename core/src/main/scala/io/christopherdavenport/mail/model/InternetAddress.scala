package io.christopherdavenport.mail.model

import javax.mail.internet.{InternetAddress => jInternetAddress}
import javax.mail.{Address => jAddress}
/**
  * Created by davenpcm on 7/10/16.
  */
case class InternetAddress(address: String, personalName: Option[String] = None, charset: Option[String] = None)

//object InternetAddress {
//  implicit class AsJava(internetAddress: InternetAddress){
//    def asJava : jInternetAddress =
//      new jInternetAddress(internetAddress.address, internetAddress.personalName.orNull, internetAddress.charset.orNull)
//    def asJavaAddress: jAddress = asJava.asInstanceOf[jAddress]
//  }
//}