package nts.uk.ctx.at.request.app.command.application.common;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.common.service.setting.PreBeforeApplicationService;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSet;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetRepository;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSettingRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
@Transactional
public class CopyAppEmploymentSetCommandHandler extends CommandHandler<CopyAppEmploymentSetCommand> {

	@Inject
	private PreBeforeApplicationService service;

//	@Inject
//	AppEmploymentSettingRepository employmentSetting;

	// refactor 4
	@Inject
	private AppEmploymentSetRepository appEmploymentSetRepo;

	@Override
	protected void handle(CommandHandlerContext<CopyAppEmploymentSetCommand> context) {
		CopyAppEmploymentSetCommand command = context.getCommand();
		// 会社ID
		String companyId = AppContexts.user().companyId();
		Optional<AppEmploymentSet> sourceData1 = appEmploymentSetRepo.findByCompanyIDAndEmploymentCD(companyId, command.getEmploymentCode());
		service.copyAppEmploymentSet_New(companyId, sourceData1, command.getTargetEmploymentCodes());
	}

}
