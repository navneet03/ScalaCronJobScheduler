package models

import java.util.Properties
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import play.Logger

import controllers._


class MailJob(val from : String, val password : String, val subject : String, val to : String, val message : String) extends Command {
    
  override def execute() : Unit = {
	  val session : Session = Session.getDefaultInstance(MailJob.props, new javax.mail.Authenticator() {		   
		  protected override def getPasswordAuthentication() : PasswordAuthentication = {
			return new PasswordAuthentication(from, password)
		  }
		 });
		
		try {
			val mimeMessage : MimeMessage = new MimeMessage(session)
			mimeMessage.setFrom(new InternetAddress(to))
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to))
			mimeMessage.setSubject(subject)
			mimeMessage.setText(message)
		
			Transport.send(mimeMessage)
			System.out.println("message sent successfully")

		} catch {
		  case ex: MessagingException =>{
           Logger.info("javax.mail.AuthenticationFailedException: 535-5.7.8 Username and Password not accepted")
      }
			
		}
	}
}
object MailJob{
  
  val FROM : String = "from"
	val TO : String = "to"
	val MSG :String = "message"
	val SUB : String = "subject"
	val PASSWORD : String = "password"
	val IS_MAIL_JOB : String = "isMailJob"
  val props : Properties  = new Properties()
		props.put("mail.smtp.host", "smtp.gmail.com")
		props.put("mail.smtp.socketFactory.port", "465")
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
		props.put("mail.smtp.auth", "true")
		props.put("mail.smtp.port", "465")

}