package nts.uk.ctx.at.record.app.command.standardtime.yearsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.standardtime.AgreementYearSetting;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmOneYear;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorOneYear;
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

		AgreementYearSetting agreementYearSetting = new AgreementYearSetting(
				command.getEmployeeId(),
				command.getYearValue(),
				new ErrorOneYear(command.getErrorOneYear()),
				new AlarmOneYear(command.getAlarmOneYear()));
		
		agreementYearSetting.validate();

		this.agreementYearSettingRepository.update(agreementYearSetting);
	}
}
