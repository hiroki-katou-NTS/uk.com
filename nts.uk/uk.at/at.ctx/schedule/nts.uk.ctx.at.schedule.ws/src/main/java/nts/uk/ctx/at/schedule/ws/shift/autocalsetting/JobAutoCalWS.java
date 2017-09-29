/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ws.shift.autocalsetting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.shift.autocalsettingjob.JobAutoCalSetCommand;
import nts.uk.ctx.at.schedule.app.command.shift.autocalsettingjob.SaveJobAutoCalSetCommandHandler;
import nts.uk.ctx.at.schedule.app.find.shift.autocalsetting.job.JobAutoCalSetFinder;
import nts.uk.ctx.at.schedule.app.find.shift.autocalsetting.job.JobAutoCalSettingDto;

/**
 * The Class JobAutoCalWS.
 */
@Path("ctx/at/schedule/shift/autocaljob")
@Produces("application/json")
public class JobAutoCalWS extends WebService {

	/** The job auto cal set finder. */
	@Inject
	private JobAutoCalSetFinder jobAutoCalSetFinder;

	/** The save job auto cal set command handler. */
	@Inject
	private SaveJobAutoCalSetCommandHandler saveJobAutoCalSetCommandHandler;

	/**
	 * Gets the job auto cal set finder.
	 *
	 * @param jobId the job id
	 * @return the job auto cal set finder
	 */
	@POST
	@Path("getautocaljob/{jobId}")
	public JobAutoCalSettingDto getJobAutoCalSetFinder(@PathParam("jobId") String jobId) {
		return this.jobAutoCalSetFinder.getJobAutoCalSetting(jobId);
	}

	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save(JobAutoCalSetCommand command) {
		this.saveJobAutoCalSetCommandHandler.handle(command);
	}

	/**
	 * Deledte by pattern cd.
	 *
	 * @param jobCd the job cd
	 */
	@POST
	@Path("delete/{jobId}")
	public void deledte(@PathParam("jobId") String jobId) {
		this.jobAutoCalSetFinder.deleteByCode(jobId);
	}

}
