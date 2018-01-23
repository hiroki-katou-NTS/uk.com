package nts.uk.ctx.at.record.app.command.standardtime.yearsetting;

import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.standardtime.AgreementYearSetting;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmOneYear;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorOneYear;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementYearSettingRepository;

/**
 * 
 * @author nampt 特例設定 screen add year
 *
 */
@Stateless
public class AddAgreementYearSettingCommandHandler extends CommandHandler<AddAgreementYearSettingCommand> {

	@Inject
	private AgreementYearSettingRepository agreementYearSettingRepository;

	@Override
	protected void handle(CommandHandlerContext<AddAgreementYearSettingCommand> context) {
		AddAgreementYearSettingCommand command = context.getCommand();
		
		if(this.agreementYearSettingRepository.checkExistData(command.getEmployeeId(), new BigDecimal(command.getYearValue()))){
			throw new BusinessException("Msg_61");
		};
		
		AgreementYearSetting agreementYearSetting = new AgreementYearSetting(
				command.getEmployeeId(),
				command.getYearValue(), 
				new ErrorOneYear(command.getErrorOneYear()),
				new AlarmOneYear(command.getAlarmOneYear()));
		
		agreementYearSetting.validate();

		this.agreementYearSettingRepository.add(agreementYearSetting);
	}

}
