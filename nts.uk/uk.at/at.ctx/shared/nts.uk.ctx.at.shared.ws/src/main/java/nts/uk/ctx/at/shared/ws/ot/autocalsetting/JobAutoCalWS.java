/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.ot.autocalsetting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.ot.autocalsetting.job.JobAutoCalSetCommand;
import nts.uk.ctx.at.shared.app.command.ot.autocalsetting.job.SaveJobAutoCalSetCommandHandler;
import nts.uk.ctx.at.shared.app.find.ot.autocalsetting.job.JobAutoCalSetFinder;
import nts.uk.ctx.at.shared.app.find.ot.autocalsetting.job.JobAutoCalSettingDto;

/**
 * The Class JobAutoCalWS.
 */
@Path("ctx/at/shared/ot/autocal/job")
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
	 * @param jobId
	 *            the job id
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
	 * @param command
	 *            the command
	 */
	@POST
	@Path("save")
	public void save(JobAutoCalSetCommand command) {
		this.saveJobAutoCalSetCommandHandler.handle(command);
	}

	/**
	 * Deledte.
	 *
	 * @param jobId
	 *            the job id
	 */
	@POST
	@Path("delete/{jobId}")
	public void deledte(@PathParam("jobId") String jobId) {
		this.jobAutoCalSetFinder.deleteByCode(jobId);
	}

}
