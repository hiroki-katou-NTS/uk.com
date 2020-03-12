package nts.uk.ctx.at.schedule.app.command.employeeinfo.medicalworkstyle;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.LicenseClassification;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.NurseClassifiCode;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.NurseClassifiName;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.NurseClassification;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.NurseClassificationRepository;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author ThanhNX 看護区分の新規作成
 */
@Stateless
public class AddNurseCategoryCommandHandler extends CommandHandler<NurseCategoryCommand> {

	@Inject
	private NurseClassificationRepository nurseClassificationRepository;

	@Override
	protected void handle(CommandHandlerContext<NurseCategoryCommand> context) {
		NurseCategoryCommand command = context.getCommand();
		// 1: get(会社ID, 看護区分コード)
		String companyId = AppContexts.user().companyId();
		Optional<NurseClassification> nurseClOpt = nurseClassificationRepository.getSpecifiNurseCategory(companyId,
				command.getNurseClassificationCode());

		// 2: not 看護区分.isEmpty
		if (nurseClOpt.isPresent()) {
			throw new BusinessException("Msg_3");
		}

		// 3: create()
		NurseClassification nurseClassification = new NurseClassification(new CompanyId(companyId),
				new NurseClassifiCode(command.getNurseClassificationCode()),
				new NurseClassifiName(command.getNurseClassificationName()),
				LicenseClassification.valueOf(command.getLicense()), command.isOfficeWorker());
		// 4: persist()
		nurseClassificationRepository.insert(nurseClassification);
	}

}
