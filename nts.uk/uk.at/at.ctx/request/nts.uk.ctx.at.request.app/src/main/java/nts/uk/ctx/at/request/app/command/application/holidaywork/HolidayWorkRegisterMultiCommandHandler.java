package nts.uk.ctx.at.request.app.command.application.holidaywork;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.app.command.application.overtime.AppOvertimeDetailCommand;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.HolidayWorkRegisterService;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;

public class HolidayWorkRegisterMultiCommandHandler extends CommandHandlerWithResult<RegisterMultiCommand, List<ProcessResult>>{

	@Inject
	private HolidayWorkRegisterService holidayWorkRegisterService;
	
	@Override
	protected List<ProcessResult> handle(CommandHandlerContext<RegisterMultiCommand> context) {
		RegisterMultiCommand param = context.getCommand();
		Application application = param.getAppHolidayWork().getApplication().toDomain();
		AppHolidayWork appHolidayWork = param.getAppHolidayWork().toDomain();
		if (appHolidayWork.getAppOvertimeDetail().isPresent()) {
			appHolidayWork.getAppOvertimeDetail().get().setAppId(application.getAppID());
		}
		appHolidayWork.setApplication(application);
		
		Map<String, AppOvertimeDetail> appOvertimeDetailMap = new HashMap<String, AppOvertimeDetail>();
		for (Map.Entry<String, AppOvertimeDetailCommand> entry : param.getAppOvertimeDetailMap().entrySet()) {
			appOvertimeDetailMap.put(entry.getKey(), entry.getValue().toDomain(param.getCompanyId(), ""));
		}
		
		return holidayWorkRegisterService.registerMulti(param.getCompanyId(), param.getEmpList(), 
				param.getAppHdWorkDispInfo().toDomain(), appHolidayWork, param.getApprovalRootContentMap(), appOvertimeDetailMap);
	}

}
