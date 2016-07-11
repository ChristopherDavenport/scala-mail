package io.christopherdavenport.mail.model

import javax.mail.Transport
import javax.mail.internet.{MimeMessage => jMimeMessage}
import scala.concurrent.{ExecutionContext, Future}
/**
  * Created by davenpcm on 7/10/16.
  */
case class Message(
                    session: Session,
                    from: InternetAddress,
                    recipients: Seq[Recipient],
                    subject: Option[String] = None,
                    replyTo: Option[Seq[InternetAddress]] = None,
                    headers: Option[Seq[(String, String)]] = None,
                    bodyContent: String
                  ) {

  def send()(implicit ec: ExecutionContext): Future[Unit] = {
//    val msg = new jMimeMessage(session.asJava){
//      setFrom(from.asJava)
//      subject.foreach(setSubject(_))
//
//      val recipientByType = recipients.groupBy(_.recipientType)
//
//      val to = recipientByType.get(TO)
//        .map(_.map(_.internetAddress.asJavaAddress).toArray)
//      to.foreach(addRecipients(TO.asJava, _))
//      val cc = recipientByType.get(CC)
//        .map(_.map(_.internetAddress.asJavaAddress).toArray)
//      cc.foreach(addRecipients(TO.asJava, _))
//      val bcc = recipientByType.get(BCC)
//        .map(_.map(_.internetAddress.asJavaAddress).toArray)
//      bcc.foreach(addRecipients(TO.asJava, _))
//
//
//      replyTo.foreach(replyTo =>
//        setReplyTo(replyTo.map(_.asJava).toArray))
//
//      setText(bodyContent)
//    }
    import io.christopherdavenport.mail.language.JavaConverters._
    val msg = this.asJava

    Future{ Transport.send(msg) }
  }

}

//object Message{
//
//}
