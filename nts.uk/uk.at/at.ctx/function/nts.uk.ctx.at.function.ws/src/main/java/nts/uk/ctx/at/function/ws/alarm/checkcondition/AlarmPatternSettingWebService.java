package nts.uk.ctx.at.function.ws.alarm.checkcondition;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.command.alarm.AddAlarmPatternSettingCommand;
import nts.uk.ctx.at.function.app.command.alarm.AddAlarmPatternSettingCommandHandler;
import nts.uk.ctx.at.function.app.command.alarm.DeleteAlarmPatternSettingCommand;
import nts.uk.ctx.at.function.app.command.alarm.DeleteAlarmPatternSettingCommandHandler;
import nts.uk.ctx.at.function.app.command.alarm.UpdateAlarmPatternSettingCommandHandler;
import nts.uk.ctx.at.function.app.find.alarm.AlarmCheckConditonCodeDto;
import nts.uk.ctx.at.function.app.find.alarm.AlarmPatternSettingDto;
import nts.uk.ctx.at.function.app.find.alarm.AlarmPatternSettingFinder;
import nts.uk.ctx.at.function.app.find.alarm.extractionrange.SpecifiedMonthDto;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmCategory;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

/**
 * Web Service for function KAL004_パターン設定
 * 
 * @author dxthuong
 *
 */
@Path("at/function/alarm")
@Produces("application/json")
public class AlarmPatternSettingWebService extends WebService{
	
	@Inject
	private AlarmPatternSettingFinder finder;
	
	@Inject
	private AddAlarmPatternSettingCommandHandler addCommandHandler; 
	
	@Inject
	private UpdateAlarmPatternSettingCommandHandler updateCommandHandler;
	
	@Inject
	private DeleteAlarmPatternSettingCommandHandler deleteCommandHandler;
	
	@Inject
	private I18NResourcesForUK i18n;
	
	@POST
	@Path("pattern/setting")
	public List<AlarmPatternSettingDto> getAlarmPatternSetting(){
		return finder.findAllAlarmPattern();
	}
	
	@POST
	@Path("check/condition/code")
	public List<AlarmCheckConditonCodeDto> getCheckConditionCode(){
		return finder.findAllAlarmCheckCondition();
	}
	
	@POST
	@Path("add/pattern/setting")
	public void createAlarmPatternSetting(AddAlarmPatternSettingCommand command) {
		addCommandHandler.handle(command);
	}
	
	@POST
	@Path("update/pattern/setting")
	public void updateAlarmPatternSetting(AddAlarmPatternSettingCommand command) {
		updateCommandHandler.handle(command);
	}
	
	@POST
	@Path("remove/pattern/setting")
	public void removeAlarmPatternSetting(DeleteAlarmPatternSettingCommand command) {
		deleteCommandHandler.handle(command);
	}
	
	@POST
	@Path("getSpecifiedMonth")
	public List<SpecifiedMonthDto> getSpecifiedMonth() {
		return finder.getSpecifiedMonth();
	}
	
	@POST
	@Path("get/enum/alarm/category")
	public List<EnumConstant> getEnumAlarmCategory(){
		return EnumAdaptor.convertToValueNameList(AlarmCategory.class, i18n);
	}
}
