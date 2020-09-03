package nts.uk.ctx.at.request.app.command.application.applicationlist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.app.command.application.common.ApproveAppHandler;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ApproveProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.shr.com.context.AppContexts;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.申請一覧承認登録ver4.申請一覧承認登録ver4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class AppListApproveCommandHandler extends CommandHandlerWithResult<AppListApproveCommand, AppListApproveResult>{
	
	private static final int PC = 0;
	private static final int MOBILE = 1;
	
	@Inject
	private ApproveAppHandler approveAppHandler;
	
	@Inject
	private CommonAlgorithm commonAlgorithm;
	
	@Override
	protected AppListApproveResult handle(CommandHandlerContext<AppListApproveCommand> context) {
		String companyID = AppContexts.user().companyId();
		AppListApproveResult result = new AppListApproveResult(new ArrayList<>(), new ArrayList<>());
		AppListApproveCommand command = context.getCommand();
		List<ListOfApplicationCmd> listOfApplicationCmds = command.getListOfApplicationCmds();
		for(ListOfApplicationCmd listOfApplicationCmd : listOfApplicationCmds) {
//			if(appListCommand.getAppStatus()==0) {
//				continue;
//			}
//			if(!command.isApprovalAll()) {
//				if(!appListCommand.isCheck()) {
//					continue;
//				}
//			}
			if(command.getDevice()==MOBILE) {
				Application application = listOfApplicationCmd.toDomainApplication();
				AppDispInfoStartupOutput appDispInfoStartupOutput = commonAlgorithm.getAppDispInfoStart(
						companyID, 
						application.getAppType(), 
						Collections.emptyList(), 
						new DatePeriod(application.getOpAppStartDate().get().getApplicationDate(), application.getOpAppEndDate().get().getApplicationDate()).datesBetween(), 
						true,
						Optional.empty(),
						Optional.empty());
				ApproveProcessResult approveProcessResult = approveAppHandler.approve(companyID, application.getAppID(), application, appDispInfoStartupOutput, "");
				if(approveProcessResult.isProcessDone()) {
					// result.getSuccessLst().add(listOfApplicationCmd);
				} else {
					
				}
			}
		}
		return result;
	}
	
	public ApproveProcessResult approveSingleApp() {
		return null;
	}

}
