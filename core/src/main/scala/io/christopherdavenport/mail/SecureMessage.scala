package io.christopherdavenport.mail

import io.christopherdavenport.mail.model._

import scala.concurrent.ExecutionContext

/**
  * Created by davenpcm on 7/31/16.
  */

case class SecureMessage(
                          userName: String,
                          password: String,
                          smtpServer: String,
                          smtpPort: Int
                        )(
                          recipients: Recipient*
                        )(
                          subject: String,
                          content: Multipart,
                          alias: Option[String] = None,
                          charset: Option[String] = None
                        ) {
  private val session = Session(
    smtpServer,
    smtpPort,
    Some(PasswordAuthentication(userName, password )),
    Some(true)
  )
  private val msg = Message(
    session,
    InternetAddress(userName, alias),
    recipients = recipients,
    bodyContent = content,
    subject = Some(subject)
  )

  def send(implicit ec: ExecutionContext) = msg.send
}