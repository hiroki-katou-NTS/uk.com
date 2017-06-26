package nts.uk.ctx.at.record.app.command.standardtime;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementYearSettingRepository;

/**
 * 
 * @author nampt 特例設定 screen update year
 *
 */
@Stateless
public class UpdateAgreementYearSettingCommandHandler extends CommandHandler<UpdateAgreementYearSettingCommand> {

	@Inject
	private AgreementYearSettingRepository agreementYearSettingRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateAgreementYearSettingCommand> context) {
		UpdateAgreementYearSettingCommand command = context.getCommand();

		this.agreementYearSettingRepository.update(command.getEmployeeId(), command.getYearValue(),
				command.getErrorOneYear(), command.getAlarmOneYear());
	}
}
