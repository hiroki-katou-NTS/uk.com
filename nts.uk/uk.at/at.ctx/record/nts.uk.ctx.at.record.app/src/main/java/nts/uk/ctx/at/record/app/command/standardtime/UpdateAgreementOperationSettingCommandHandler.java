package nts.uk.ctx.at.record.app.command.standardtime;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * @author nampt 運用 screen update
 *
 */
@Stateless
public class UpdateAgreementOperationSettingCommandHandler
		extends CommandHandler<UpdateAgreementOperationSettingCommand> {

	@Inject
	private AgreementOperationSettingRepository agreementOperationSettingRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateAgreementOperationSettingCommand> context) {
		UpdateAgreementOperationSettingCommand command = context.getCommand();
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		this.agreementOperationSettingRepository.update(companyId, command.getStartingMonth(),
				command.getNumberTimesOverLimitType(), command.getClosingDateType(), command.getClosingDateAtr(),
				command.getYearlyWorkTableAtr(), command.getAlarmListAtr());
	}

}
