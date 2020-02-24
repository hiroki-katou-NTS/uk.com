/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.workrule.shiftmaster;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.shared.app.command.workrule.shiftmaster.DeleteShiftMasterCommand;
import nts.uk.ctx.at.shared.app.command.workrule.shiftmaster.DeleteShiftMasterCommandHandler;
import nts.uk.ctx.at.shared.app.command.workrule.shiftmaster.RegisterShiftMasterCommand;
import nts.uk.ctx.at.shared.app.command.workrule.shiftmaster.RegisterShiftMasterCommandHandler;
import nts.uk.ctx.at.shared.app.find.workrule.shiftmaster.Ksm015bStartPageDto;
import nts.uk.ctx.at.shared.app.find.workrule.shiftmaster.ShiftMasterScreenQueryFinder;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.dto.ShiftMasterDto;

/**
 * The Class ShiftMasterWs.
 */
/**
 * @author anhdt
 *
 */
@Path("ctx/at/shared/workrule/shiftmaster")
@Produces("application/json")
public class ShiftMasterWs {
	
	@Inject
	private ShiftMasterScreenQueryFinder finder;
	
	@Inject
	private RegisterShiftMasterCommandHandler registerCmd;
	
	@Inject
	private DeleteShiftMasterCommandHandler deleteCmd;
	
	@POST
	@Path("startPage")
	public Ksm015bStartPageDto findAll(){
		return this.finder.startBScreen();
	}
	
	@POST
	@Path("getlist")
	public List<ShiftMasterDto> getlist(){
		return this.finder.getShiftMasters();
	}
	
	@POST
	@Path("register")
	public void register(RegisterShiftMasterCommand command){
		this.registerCmd.handle(command);
	}
	
	@POST
	@Path("delete")
	public void delete(DeleteShiftMasterCommand command){
		this.deleteCmd.handle(command);
	}
	
}
