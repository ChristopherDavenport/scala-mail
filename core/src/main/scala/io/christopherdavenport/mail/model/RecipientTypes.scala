package io.christopherdavenport.mail.model

import javax.mail.Message.{RecipientType => jRecipientType}

/**
  * Created by davenpcm on 7/10/16.
  */
trait RecipientType

object RecipientTypes{

  case object TO  extends RecipientType{
    def asJava: jRecipientType = jRecipientType.TO
  }

  case object CC extends RecipientType {
    def asJava: jRecipientType = jRecipientType.CC
  }
  case object BCC extends RecipientType {
    def asJava: jRecipientType = jRecipientType.BCC
  }

}

