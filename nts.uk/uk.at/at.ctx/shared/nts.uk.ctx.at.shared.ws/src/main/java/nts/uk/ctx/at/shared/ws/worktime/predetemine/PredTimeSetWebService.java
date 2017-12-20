/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.worktime.predetemine;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.pred.PredCommand;
import nts.uk.ctx.at.shared.app.command.pred.PredCommandHandler;
import nts.uk.ctx.at.shared.app.find.worktime.predset.PredetemineTimeSetFinder;
import nts.uk.ctx.at.shared.app.find.worktime.predset.dto.PredetemineTimeSettingDto;

/**
 * The Class PredTimeSetWebService.
 */
@Path("at/shared/pred")
@Produces("application/json")
@Stateless
public class PredTimeSetWebService extends WebService {

	/** The pred finder. */
	@Inject
	private PredetemineTimeSetFinder predetemineTimeSetFinder;

	@Inject
	private PredCommandHandler predCommandHandler;

	/**
	 * Find by code.
	 *
	 * @param workTimeCode
	 *            the work time code
	 * @return the pred dto
	 */
	@POST
	@Path("findByWorkTimeCode/{workTimeCode}")
	public PredetemineTimeSettingDto findByWorkTimeCode(@PathParam("workTimeCode") String workTimeCode) {
		return this.predetemineTimeSetFinder.findByWorkTimeCode(workTimeCode);
	}

	@POST
	@Path("save")
	public void savePred(PredCommand command) {
		this.predCommandHandler.handle(command);
	}

}
