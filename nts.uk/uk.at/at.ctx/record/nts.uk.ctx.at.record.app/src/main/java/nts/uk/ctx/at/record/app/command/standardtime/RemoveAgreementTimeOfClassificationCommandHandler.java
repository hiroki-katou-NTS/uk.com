package nts.uk.ctx.at.record.app.command.standardtime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfClassificationRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.BasicAgreementSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt 分類 screen remove
 *
 */
@Stateless
public class RemoveAgreementTimeOfClassificationCommandHandler
		extends CommandHandler<RemoveAgreementTimeOfClassificationCommand> {

	@Inject
	private AgreementTimeOfClassificationRepository agreementTimeOfClassificationRepository;

	@Inject
	private BasicAgreementSettingRepository basicAgreementSettingRepository;

	@Override
	protected void handle(CommandHandlerContext<RemoveAgreementTimeOfClassificationCommand> context) {
		RemoveAgreementTimeOfClassificationCommand command = context.getCommand();
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		Optional<String> basicSettingId = this.agreementTimeOfClassificationRepository.findEmploymentBasicSettingID(
				companyId, EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class),
				command.getClassificationCode());
		
		if (basicSettingId.isPresent()) {
			this.basicAgreementSettingRepository.remove(basicSettingId.get());
			
			this.agreementTimeOfClassificationRepository.remove(companyId,
					EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class),
					command.getClassificationCode());
		}
	}

}
