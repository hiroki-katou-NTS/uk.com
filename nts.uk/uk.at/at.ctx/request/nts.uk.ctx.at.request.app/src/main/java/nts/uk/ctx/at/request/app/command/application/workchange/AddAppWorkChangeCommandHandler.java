package nts.uk.ctx.at.request.app.command.application.workchange;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.app.command.application.common.CreateApplicationCommand;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectionInformation_New;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.request.dom.application.workchange.IWorkChangeRegisterService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class AddAppWorkChangeCommandHandler extends CommandHandlerWithResult<AddAppWorkChangeCommand, ProcessResult> {

	private static final String COLON_STRING = ":";
	@Inject
	private IWorkChangeRegisterService workChangeRegisterService;
	
	@Override
	protected ProcessResult handle(CommandHandlerContext<AddAppWorkChangeCommand> context) {
		AddAppWorkChangeCommand addCommand = context.getCommand();

		// Application command
		CreateApplicationCommand appCommand = addCommand.getApplication();
		// Work change command
		AppWorkChangeCommand workChangeCommand = addCommand.getWorkChange();
		// 会社ID
		String companyId = AppContexts.user().companyId();
		// 申請ID
		String appID = IdentifierUtil.randomUniqueId();
		// 入力者 = 申請者
		// 申請者
		String applicantSID = AppContexts.user().employeeId();
		// 申請
		Application_New app = new Application_New(
				0L, 
				companyId, 
				appID,
				EnumAdaptor.valueOf(appCommand.getPrePostAtr(), PrePostAtr.class), 
				GeneralDateTime.now(), 
				applicantSID,
				new AppReason(Strings.EMPTY), 
				appCommand.getStartDate(),
				new AppReason(appCommand.getApplicationReason().replaceFirst(COLON_STRING, System.lineSeparator())),
				ApplicationType.WORK_CHANGE_APPLICATION, 
				applicantSID, 
				Optional.of(appCommand.getStartDate()),
				Optional.of(appCommand.getEndDate()), 
				ReflectionInformation_New.firstCreate());		
		// 勤務変更申請
		AppWorkChange workChangeDomain = AppWorkChange.createFromJavaType(
				companyId, 
				appID,
				workChangeCommand.getWorkTypeCd(), 
				workChangeCommand.getWorkTimeCd(),
				workChangeCommand.getExcludeHolidayAtr(), 
				workChangeCommand.getWorkChangeAtr(),
				workChangeCommand.getGoWorkAtr1(), 
				workChangeCommand.getBackHomeAtr1(),
				workChangeCommand.getBreakTimeStart1(), 
				workChangeCommand.getBreakTimeEnd1(),
				workChangeCommand.getWorkTimeStart1(), 
				workChangeCommand.getWorkTimeEnd1(),
				workChangeCommand.getWorkTimeStart2(), 
				workChangeCommand.getWorkTimeEnd2(),
				workChangeCommand.getGoWorkAtr2(), 
				workChangeCommand.getBackHomeAtr2());

		return workChangeRegisterService.registerData(workChangeDomain, app);
	}
}
