package nts.uk.ctx.at.record.app.command.standardtime.employment;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfEmploymentDomainService;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfEmploymentRepostitory;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt 雇用 screen remove
 *
 */
@Stateless
public class RemoveAgreementTimeOfEmploymentCommandHandler
		extends CommandHandler<RemoveAgreementTimeOfEmploymentCommand> {

	@Inject
	private AgreementTimeOfEmploymentDomainService agreementTimeOfEmploymentDomainService;

	@Inject
	private AgreementTimeOfEmploymentRepostitory agreementTimeOfEmploymentRepostitory;

	@Override
	protected void handle(CommandHandlerContext<RemoveAgreementTimeOfEmploymentCommand> context) {
		RemoveAgreementTimeOfEmploymentCommand command = context.getCommand();
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		Optional<String> basicSettingId = this.agreementTimeOfEmploymentRepostitory.findEmploymentBasicSettingId(
				companyId, command.getEmploymentCategoryCode(),
				EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class));

		if (basicSettingId.isPresent()) {

			this.agreementTimeOfEmploymentDomainService.remove(companyId, command.getEmploymentCategoryCode(),
					command.getLaborSystemAtr(), basicSettingId.get());
		}
	}

}
