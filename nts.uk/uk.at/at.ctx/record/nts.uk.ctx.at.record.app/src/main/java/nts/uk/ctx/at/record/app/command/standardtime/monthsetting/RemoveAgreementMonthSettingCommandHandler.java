package nts.uk.ctx.at.record.app.command.standardtime.monthsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementMonthSettingRepository;

/**
 * 
 * @author nampt 特例設定 screen remove month
 *
 */
@Stateless
public class RemoveAgreementMonthSettingCommandHandler extends CommandHandler<RemoveAgreementMonthSettingCommand> {

	@Inject
	private AgreementMonthSettingRepository agreementMonthSettingRepository;

	@Override
	protected void handle(CommandHandlerContext<RemoveAgreementMonthSettingCommand> context) {
		RemoveAgreementMonthSettingCommand command = context.getCommand();

		this.agreementMonthSettingRepository.delete(command.getEmployeeId(), command.getYearMonthValue());
	}

}
