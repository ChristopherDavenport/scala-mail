package io.christopherdavenport.mail.model

import language.implicitConversions
import java.io.File
/**
  * Created by davenpcm on 7/11/16.
  */
trait BodyPart

object BodyPart{
  implicit def asMultiPart(bodyPart: BodyPart): Multipart = Multipart(bodyPart)
}

case class Text(content: String) extends BodyPart
case class Html(content: String) extends BodyPart
case class Attachment(file: File) extends BodyPart
case class ByteAttachment(bytes: Array[Byte], name: String, mimeType: String) extends BodyPart