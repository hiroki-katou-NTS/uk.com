package nts.uk.ctx.at.shared.app.command.employeeworkway.medicalworkstyle;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle.LicenseClassification;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle.NurseClassifiCode;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle.NurseClassifiName;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle.NurseClassification;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle.NurseClassificationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author ThanhNX
 * 看護区分の更新
 */
@Stateless
public class UpdateNurseCategoryCommandHandler extends CommandHandler<NurseCategoryCommand>{

	@Inject
	private NurseClassificationRepository nurseClassificationRepository;
	
	@Override
	protected void handle(CommandHandlerContext<NurseCategoryCommand> context) {
		NurseCategoryCommand command = context.getCommand();
		// 1: get(会社ID, 看護区分コード)
		String companyId = AppContexts.user().companyId();
		Optional<NurseClassification> nurseClOpt = nurseClassificationRepository.getSpecifiNurseCategory(companyId,
				command.getNurseClassificationCode());
		if(!nurseClOpt.isPresent()) return;
		// 2: set()
		NurseClassification nurseClassification = new NurseClassification(new CompanyId(companyId),
				new NurseClassifiCode(command.getNurseClassificationCode()),
				new NurseClassifiName(command.getNurseClassificationName()),
				LicenseClassification.valueOf(command.getLicense()), command.isOfficeWorker());
		// 3: persist()
		nurseClassificationRepository.update(nurseClassification);
		
	}

}
