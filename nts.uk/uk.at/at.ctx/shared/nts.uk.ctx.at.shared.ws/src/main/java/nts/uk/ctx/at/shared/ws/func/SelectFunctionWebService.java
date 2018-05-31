package nts.uk.ctx.at.shared.ws.func;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.shared.app.command.flex.AddFlexWorkSettingCommand;
import nts.uk.ctx.at.shared.app.command.flex.AddFlexWorkSettingCommandHandler;
import nts.uk.ctx.at.shared.app.command.workrule.deformed.AddAggDeformedLaborSettingCommand;
import nts.uk.ctx.at.shared.app.command.workrule.deformed.AddAggDeformedLaborSettingCommandHandler;
import nts.uk.ctx.at.shared.app.command.workrule.workmanagementmultiple.AddWorkManagementMultipleCommand;
import nts.uk.ctx.at.shared.app.command.workrule.workmanagementmultiple.AddWorkManagementMultipleCommandHandler;
import nts.uk.ctx.at.shared.app.command.workrule.workuse.AddTemporaryWorkUseMntCommand;
import nts.uk.ctx.at.shared.app.command.workrule.workuse.AddTemporaryWorkUseMntCommandHandler;
import nts.uk.ctx.at.shared.app.find.workrule.func.SelectFunctionDto;
import nts.uk.ctx.at.shared.app.find.workrule.func.SelectFunctionFinder;
import nts.uk.ctx.at.shared.app.find.workrule.func.SettingFlexWorkDto;

/**
 * 機能の選択
 * @author HoangNDH
 *
 */
@Path("shared/selection/func")
@Produces("application/json")
public class SelectFunctionWebService {
	@Inject
	SelectFunctionFinder finder;
	
	@Inject
	AddAggDeformedLaborSettingCommandHandler aggSettingHandler;
	
	@Inject
	AddWorkManagementMultipleCommandHandler workMultipleHandler;
	
	@Inject
	AddTemporaryWorkUseMntCommandHandler tempWorkUseHandler;
	
	@Inject
	AddFlexWorkSettingCommandHandler flexWorkSetHandler;
	
	@Path("loadAllSetting")
	@POST
	public SelectFunctionDto loadAllSetting() {
		return finder.findAllSetting();
	}

	@Path("settingflexwork/get")
	@POST
	public SettingFlexWorkDto findSettingFlexWork() {
		return finder.findSettingFlexWork();
	}
	
	@Path("regAgg")
	@POST
	public void registerAgg(AddAggDeformedLaborSettingCommand command) {
		aggSettingHandler.handle(command);
	}
	
	@Path("regWorkMulti")
	@POST
	public void registerWorkMulti(AddWorkManagementMultipleCommand command) {
		workMultipleHandler.handle(command);
	}
	
	@Path("regTempWork")
	@POST
	public void registerTempWork(AddTemporaryWorkUseMntCommand command) {
		tempWorkUseHandler.handle(command);
	}
	
	@Path("regFlexWorkSet")
	@POST
	public void registerFlexWorkSet(AddFlexWorkSettingCommand command) {
		flexWorkSetHandler.handle(command);
	}
}
