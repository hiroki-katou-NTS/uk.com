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
import nts.uk.ctx.at.shared.app.command.workrule.shiftmaster.DeleteShiftMasterOrgCommand;
import nts.uk.ctx.at.shared.app.command.workrule.shiftmaster.DeleteShiftMasterOrgCommandHandler;
import nts.uk.ctx.at.shared.app.command.workrule.shiftmaster.RegisterShiftMasterCommand;
import nts.uk.ctx.at.shared.app.command.workrule.shiftmaster.RegisterShiftMasterCommandHandler;
import nts.uk.ctx.at.shared.app.command.workrule.shiftmaster.RegisterShiftMasterOrgCommand;
import nts.uk.ctx.at.shared.app.command.workrule.shiftmaster.RegisterShiftMasterOrgCommandHandler;
import nts.uk.ctx.at.shared.app.find.workrule.shiftmaster.AlreadySettingWorkplaceDto;
import nts.uk.ctx.at.shared.app.find.workrule.shiftmaster.Ksm015StartPageDto;
import nts.uk.ctx.at.shared.app.find.workrule.shiftmaster.ShiftMasterFinder;
import nts.uk.ctx.at.shared.app.find.workrule.shiftmaster.ShiftMasterOrgFinder;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.dto.ShiftMasterDto;
import nts.uk.shr.com.context.AppContexts;

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
	private ShiftMasterFinder finder;
	
	@Inject
	private ShiftMasterOrgFinder orgFinder;
	
	@Inject
	private RegisterShiftMasterCommandHandler registerCmd;
	
	@Inject
	private DeleteShiftMasterCommandHandler deleteCmd;
	
	@Inject
	private RegisterShiftMasterOrgCommandHandler registerOrgCmd;
	
	@Inject
	private DeleteShiftMasterOrgCommandHandler deleteOrgCmd; 
	
	@POST
	@Path("startCPage")
	public Ksm015StartPageDto isForAttendent(){
		AlreadySettingWorkplaceDto configWorkplace = this.orgFinder.getAlreadySetting();
		return Ksm015StartPageDto.builder()
				.forAttendent(AppContexts.user().roles().forAttendance())
				.alreadyConfigWorkplaces(configWorkplace.getWorkplaceIds())
				.build() ;
	}
	
	@POST
	@Path("startPage")
	public Ksm015StartPageDto findAll(){
		return this.finder.startScreen();
	}
	
	@POST
	@Path("getlist")
	public List<ShiftMasterDto> getlist(){
		return this.finder.getShiftMasters();
	}
	
	@POST
	@Path("getlistByWorkPlace")
	public List<ShiftMasterDto> getlist(FindShiftMasterDto dto){
		return this.orgFinder.getShiftMastersByWorkPlace(dto.getWorkplaceId(), dto.getTargetUnit());
	}
	
	@POST
	@Path("getAlreadyConfigOrg")
	public AlreadySettingWorkplaceDto getAlreadyConfigOrg(){
		return this.orgFinder.getAlreadySetting();
	}
	
	@POST
	@Path("register/shiftmaster/org")
	public void registerShiftMasterOrg(RegisterShiftMasterOrgCommand dto){
		this.registerOrgCmd.handle(dto);;
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
	
	@POST
	@Path("delete/org")
	public void deleteOrg(DeleteShiftMasterOrgCommand command){
		this.deleteOrgCmd.handle(command);
	}
	
}
