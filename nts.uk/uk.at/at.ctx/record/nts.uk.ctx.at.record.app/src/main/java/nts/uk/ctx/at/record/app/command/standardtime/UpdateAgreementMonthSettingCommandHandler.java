package nts.uk.ctx.at.record.app.command.standardtime;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementMonthSettingRepository;

/**
 * 
 * @author nampt 特例設定 screen update month
 *
 */
@Stateless
public class UpdateAgreementMonthSettingCommandHandler extends CommandHandler<UpdateAgreementMonthSettingCommand> {

	@Inject
	private AgreementMonthSettingRepository agreementMonthSettingRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateAgreementMonthSettingCommand> context) {
		UpdateAgreementMonthSettingCommand command = context.getCommand();

		this.agreementMonthSettingRepository.update(command.getEmployeeId(), command.getYearMonthValue(),
				command.getErrorOneMonth(), command.getAlarmOneMonth());
	}

}
