package nts.uk.ctx.at.request.app.command.application.common;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.AfterProcessDelete;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.ProcessDeleteResult;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.HolidayService;
import nts.uk.shr.com.context.AppContexts;
@Stateless
@Transactional
public class UpdateApplicationDelete extends CommandHandlerWithResult<UpdateApplicationCommonCmd, ProcessResult> {
	
	@Inject
	private AfterProcessDelete afterProcessDelete;
	
	@Inject
	private DetailBeforeUpdate beforeRegisterRepo;
	
	@Inject 
	private HolidayService holidayService;

	@Override
	protected ProcessResult handle(CommandHandlerContext<UpdateApplicationCommonCmd> context) {
		String companyID = AppContexts.user().companyId();
		UpdateApplicationCommonCmd command = context.getCommand();
		beforeRegisterRepo.exclusiveCheck(companyID, command.getAppId(), command.getVersion());
		//5.2(hieult)
		ProcessDeleteResult processDeleteResult = afterProcessDelete.screenAfterDelete(companyID, context.getCommand().getAppId(), context.getCommand().getVersion());
		// アルゴリズム「11.休出申請（振休変更）削除」を実行する
		if(processDeleteResult.getAppType()==ApplicationType.BREAK_TIME_APPLICATION){
			holidayService.delHdWorkByAbsLeaveChange(context.getCommand().getAppId());
		}
		return processDeleteResult.getProcessResult();
	}
}
