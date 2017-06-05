/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.vacation.setting.acquisitionrule;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.vacation.setting.acquisitionrule.command.AcquisitionRuleCommand;
import nts.uk.ctx.at.shared.app.vacation.setting.acquisitionrule.command.SaveAcquisitionRuleCommandHandler;
import nts.uk.ctx.at.shared.app.vacation.setting.acquisitionrule.find.AcquisitionRuleDto;
import nts.uk.ctx.at.shared.app.vacation.setting.acquisitionrule.find.AcquisitionRuleFinder;
import nts.uk.ctx.at.shared.app.vacation.setting.annualpaidleave.command.AnnualPaidLeaveSaveCommand;


/**
 * The Class AcquisitionRuleWs.
 */
@Path("ctx/at/share/vacation/setting/acquisitionrule/")
@Produces("application/json")
public class AcquisitionRuleWs extends WebService {
	
	/** The finder. */
	@Inject
	private AcquisitionRuleFinder finder;
	
	/** The save. */
	@Inject
	private SaveAcquisitionRuleCommandHandler save;

	/**
	 * Find by company id.
	 *
	 * @return the acquisition rule dto
	 */
	@POST
    @Path("find")
    public AcquisitionRuleDto findByCompanyId() {
        return this.finder.find();
    }
	
	/**
	 * Update.
	 *
	 * @param command the command
	 */
	@POST
    @Path("update")
    public void update(AcquisitionRuleCommand command) {
        this.save.handle(command);
    }
}
