package nts.uk.ctx.at.request.app.command.application.common;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.AfterProcessDelete;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.ProcessDeleteResult;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.HolidayService;
import nts.uk.shr.com.context.AppContexts;
@Stateless
@Transactional
public class DeleteAppHandler extends CommandHandlerWithResult<AppDispInfoStartupDto, ProcessResult> {
	
	@Inject
	private AfterProcessDelete afterProcessDelete;
	
	@Inject
	private DetailBeforeUpdate beforeRegisterRepo;
	
	@Inject 
	private HolidayService holidayService;

	@Override
	protected ProcessResult handle(CommandHandlerContext<AppDispInfoStartupDto> context) {
		String companyID = AppContexts.user().companyId();
		ApplicationDto applicationDto = context.getCommand().getAppDetailScreenInfo().getApplication();
		String appID = applicationDto.getAppID();
		int version = applicationDto.getVersion();
		
		beforeRegisterRepo.exclusiveCheck(companyID, appID, version);
		//5.2(hieult)
		ProcessDeleteResult processDeleteResult = afterProcessDelete.screenAfterDelete(
				appID, 
				applicationDto.toDomain(), 
				context.getCommand().toDomain());
		// アルゴリズム「11.休出申請（振休変更）削除」を実行する
		if(processDeleteResult.getAppType()==ApplicationType.HOLIDAY_WORK_APPLICATION){
			holidayService.delHdWorkByAbsLeaveChange(appID);
		}
		return processDeleteResult.getProcessResult();
	}
}
