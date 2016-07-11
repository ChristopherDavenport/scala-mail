package io.christopherdavenport.mail.model

/**
  * Created by davenpcm on 7/10/16.
  */
case class Recipient(recipientType: RecipientType, internetAddress: InternetAddress)

//object Recipient{
//
//  object RecipientTypes{
//    val TO = io.christopherdavenport.mail.model.TO
//    val CC = io.christopherdavenport.mail.model.CC
//    val BCC = io.christopherdavenport.mail.model.BCC
//  }
//
//}
