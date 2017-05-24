/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.vacation.setting.annualpaidleave;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import vacation.setting.annualpaidleave.command.AnnualPaidLeaveUpateCommand;
import vacation.setting.annualpaidleave.command.AnnualPaidLeaveUpateCommandHandler;

@Path("ctx/pr/core/vacation/setting/annualpaidleave/")
@Produces("application/json")
public class AnnualPaidLeaveWs extends WebService {
    
    @Inject
    AnnualPaidLeaveUpateCommandHandler annualPaidHandler;
    
    @POST
    @Path("update")
    public void update(AnnualPaidLeaveUpateCommand command) {
        this.annualPaidHandler.handle(command);
    }
}
