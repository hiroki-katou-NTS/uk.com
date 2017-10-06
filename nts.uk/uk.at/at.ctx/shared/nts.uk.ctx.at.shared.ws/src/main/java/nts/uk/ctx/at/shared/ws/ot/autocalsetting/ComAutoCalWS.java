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
import nts.uk.ctx.at.shared.app.command.ot.autocalsetting.ComAutoCalSetCommand;
import nts.uk.ctx.at.shared.app.command.ot.autocalsetting.SaveComAutoCalSetCommandHandler;
import nts.uk.ctx.at.shared.app.find.ot.autocalsetting.ComAutoCalSetFinder;
import nts.uk.ctx.at.shared.app.find.ot.autocalsetting.ComAutoCalSettingDto;

/**
 * The Class AutomaticCalculationWS.
 */
@Path("ctx/at/shared/ot/autocal/com")
@Produces("application/json")
public class ComAutoCalWS extends WebService {
	/** The total times finder. */
	@Inject
	private ComAutoCalSetFinder comAutoCalSetFinder;
	
	/** The save total times command handler. */
	@Inject
	private SaveComAutoCalSetCommandHandler saveComAutoCalSetCommandHandler;

	
	/**
	 * Find enum auto cal atr overtime.
	 *
	 * @return the list
	 */
	@POST
	@Path("getautocalcom")
	public ComAutoCalSettingDto getTotalTimesDetail() {
		return this.comAutoCalSetFinder.getComAutoCalSetting();
	}
	
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save(ComAutoCalSetCommand command) {
		this.saveComAutoCalSetCommandHandler.handle(command);
	}
}
