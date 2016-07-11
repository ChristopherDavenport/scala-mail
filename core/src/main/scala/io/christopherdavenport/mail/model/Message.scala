package io.christopherdavenport.mail.model

import javax.mail.{Transport => jTransport}

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
                  ){
  def send(implicit ec: ExecutionContext): Future[Unit] = Message.send(this)(ec)
}


object Message{
  def send(message: Message)(implicit ec: ExecutionContext): Future[Unit] = {
    import io.christopherdavenport.mail.language.JavaConverters._
    Future{
      jTransport.send(message.asJava)
    }
  }
}