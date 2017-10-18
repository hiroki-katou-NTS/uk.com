/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.ot.autocalsetting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.ot.autocalsetting.use.SaveUnitAutoCalSetCommandHandler;
import nts.uk.ctx.at.shared.app.command.ot.autocalsetting.use.UseUnitAutoCalSettingCommand;
import nts.uk.ctx.at.shared.app.find.ot.autocalsetting.unit.UnitAutoCalSetFinder;
import nts.uk.ctx.at.shared.app.find.ot.autocalsetting.unit.UseUnitAutoCalSettingDto;

/**
 * The Class UnitAutoCalWS.
 */
@Path("ctx/at/shared/ot/autocal/unit")
@Produces("application/json")
public class UnitAutoCalWS extends WebService {
	
	/** The unit auto cal set finder. */
	@Inject
	private UnitAutoCalSetFinder unitAutoCalSetFinder;
	
	/** The save unit auto cal set command handler. */
	@Inject
	private SaveUnitAutoCalSetCommandHandler saveUnitAutoCalSetCommandHandler;

	
	/**
	 * Gets the unit auto cal setting.
	 *
	 * @return the unit auto cal setting
	 */
	@POST
	@Path("find/autocalunit")
	public UseUnitAutoCalSettingDto getUnitAutoCalSetting() {
		return this.unitAutoCalSetFinder.getUseUnitAutoCalSetting();
	}
	
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save(UseUnitAutoCalSettingCommand command) {
		this.saveUnitAutoCalSetCommandHandler.handle(command);
	}
}
