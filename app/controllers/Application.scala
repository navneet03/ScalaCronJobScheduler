package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import org.quartz.JobDataMap

import models._

object Application extends Controller {

  def index = Action {
    Redirect("/scheduler")
  }
  
  def scheduler = Action {
		Ok(views.html.scheduler(SchedulerPatternModel.data))
	}
	
	def getSchedulerDetails = Action { request =>		
	  val json : JsValue = request.body.asJson.get		
		val SchedulerTimePattern : String = (json \ "SchedulerTimePattern").as[String]			
		val mailJobDataMap : JobDataMap = getMailJobDataMap(json)
		Ok(CronScheduler.ScheduleJob("mail", SchedulerTimePattern, mailJobDataMap))	
	}
    
  def getMailJobDataMap( json : JsValue ) : JobDataMap = {
	  val mailJobDataMap : JobDataMap = new JobDataMap()
		mailJobDataMap.put(MailJob.FROM, (json \ MailJob.FROM).as[String])
		mailJobDataMap.put(MailJob.TO, (json \ MailJob.TO).as[String])
		mailJobDataMap.put(MailJob.SUB, (json \ MailJob.SUB).as[String])
		mailJobDataMap.put(MailJob.MSG, (json \ MailJob.MSG).as[String])
		mailJobDataMap.put(MailJob.PASSWORD, (json \ MailJob.PASSWORD).as[String])
		mailJobDataMap.put(MailJob.IS_MAIL_JOB, true)
		return mailJobDataMap
	}
}

