package models

import org.quartz.Job
import org.quartz.JobDataMap
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException

import controllers._

class ScheduleJob extends Job {
  
  @throws(classOf[JobExecutionException])
  def execute(context : JobExecutionContext) = {
	  val  map : JobDataMap = context.getJobDetail().getJobDataMap()
		val  isMailJob : Boolean = map.getBoolean(MailJob.IS_MAIL_JOB)
		var cmd : Command = null
		if (isMailJob) {
		  cmd = getMailCommand(map)
		  cmd.execute()
		}							
	}

	def getMailCommand(map : JobDataMap) : Command = {
		val from : String = map.getString(MailJob.FROM)
		val to : String = map.getString(MailJob.TO)
		val msg : String = map.getString(MailJob.MSG)
		val sub : String = map.getString(MailJob.SUB)
		val pass : String = map.getString(MailJob.PASSWORD)
		return new MailJob(from, pass, sub, to, msg)
	}
}