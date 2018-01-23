package nts.uk.ctx.at.record.app.command.standardtime.monthsetting;

import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.standardtime.AgreementMonthSetting;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmOneMonth;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorOneMonth;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementMonthSettingRepository;

/**
 * 
 * @author nampt 特例設定 screen add month
 *
 */
@Stateless
public class AddAgreementMonthSettingCommandHandler extends CommandHandler<AddAgreementMonthSettingCommand> {

	@Inject
	private AgreementMonthSettingRepository agreementMonthSettingRepository;

	@Override
	protected void handle(CommandHandlerContext<AddAgreementMonthSettingCommand> context) {
		AddAgreementMonthSettingCommand command = context.getCommand();
		
		if(this.agreementMonthSettingRepository.checkExistData(command.getEmployeeId(), new BigDecimal(command.getYearMonthValue()))){
			throw new BusinessException("Msg_61");
		};
		
		AgreementMonthSetting agreementMonthSetting = new AgreementMonthSetting(
				command.getEmployeeId(),
				new YearMonth(command.getYearMonthValue()),
				new ErrorOneMonth(command.getErrorOneMonth()),
				new AlarmOneMonth(command.getAlarmOneMonth()));
		
		agreementMonthSetting.validate();

		this.agreementMonthSettingRepository.add(agreementMonthSetting);
	}

}
