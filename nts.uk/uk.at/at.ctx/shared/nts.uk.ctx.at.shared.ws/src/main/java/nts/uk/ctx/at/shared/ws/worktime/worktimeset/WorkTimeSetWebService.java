/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.worktime.worktimeset;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.WorkTimeSettingFinder;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto.WorkTimeSettingDto;

/**
 * The Class PredTimeSetWebService.
 */
@Path("at/shared/worktimeset")
@Produces("application/json")
@Stateless
public class WorkTimeSetWebService extends WebService {

	@Inject
	private WorkTimeSettingFinder workTimeSetFinder;

	/**
	 * Find by code.
	 *
	 * @param workTimeCode
	 *            the work time code
	 * @return the pred dto
	 */
	@POST
	@Path("findAll")
	public List<WorkTimeSettingDto> findAll() {
		return this.workTimeSetFinder.findAll();
	}

//	@POST
//	@Path("save")
//	public void savePred(PredCommand command) {
//		this.predCommandHandler.handle(command);
//	}

}
