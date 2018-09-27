/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.vacation.setting.annualpaidleave;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.vacation.setting.annualpaidleave.OperationStartSetDailyPerformCommand;
import nts.uk.ctx.at.shared.app.command.vacation.setting.annualpaidleave.OperationStartSetDailyPerformCommandHandler;
import nts.uk.ctx.at.shared.app.find.vacation.setting.annualpaidleave.OperationStartSetDailyPerformFinder;
import nts.uk.ctx.at.shared.app.find.vacation.setting.annualpaidleave.dto.OperationStartSetDailyPerformDto;

/**
 * The Class OperationStartSetDailyPerformWS.
 */
@Path("ctx/at/share/vacation/setting/annualpaidleave/startsetdailyperform")
@Produces("application/json")
public class OperationStartSetDailyPerformWS extends WebService{
	
	/** The operation start set daily perform finder. */
	@Inject
	private OperationStartSetDailyPerformFinder operationStartSetDailyPerformFinder; 
	
	@Inject
	private OperationStartSetDailyPerformCommandHandler operationStartSetDailyPerformCommandHandler;
	
	@POST
	@Path("find")
	public OperationStartSetDailyPerformDto findByCid() {
	    return operationStartSetDailyPerformFinder.findByCid();
	}

	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save(OperationStartSetDailyPerformCommand command) {
	    this.operationStartSetDailyPerformCommandHandler.handle(command);
	}
}

