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
import nts.uk.ctx.at.shared.app.vacation.setting.annualpaidleave.command.AnnualPaidLeaveSaveCommand;
import nts.uk.ctx.at.shared.app.vacation.setting.annualpaidleave.command.AnnualPaidLeaveSaveCommandHandler;
import nts.uk.ctx.at.shared.app.vacation.setting.annualpaidleave.find.AnnualPaidLeaveFinder;
import nts.uk.ctx.at.shared.app.vacation.setting.annualpaidleave.find.dto.AnnualPaidLeaveSettingFindDto;

/**
 * The Class AnnualPaidLeaveWs.
 */
@Path("ctx/at/core/vacation/setting/annualpaidleave/")
@Produces("application/json")
public class AnnualPaidLeaveWs extends WebService {

    /** The annual finder. */
    @Inject
    private AnnualPaidLeaveFinder annualFinder;

    /** The annual paid handler. */
    @Inject
    AnnualPaidLeaveSaveCommandHandler annualPaidHandler;

    /**
     * Update.
     *
     * @param command
     *            the command
     */
    @POST
    @Path("update")
    public void update(AnnualPaidLeaveSaveCommand command) {
        this.annualPaidHandler.handle(command);
    }

    /**
     * Find by company id.
     *
     * @return the annual paid leave setting
     */
    @POST
    @Path("find")
    public AnnualPaidLeaveSettingFindDto findByCompanyId() {
        return this.annualFinder.findByCompanyId();
    }
}
