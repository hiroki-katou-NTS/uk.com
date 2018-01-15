package nts.uk.ctx.at.record.app.command.standardtime.monthsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.standardtime.AgreementMonthSetting;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmOneMonth;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorOneMonth;
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

		AgreementMonthSetting agreementMonthSetting = new AgreementMonthSetting(command.getEmployeeId(),
				new YearMonth(command.getYearMonthValue()), new ErrorOneMonth(command.getErrorOneMonth()),
				new AlarmOneMonth(command.getAlarmOneMonth()));
		
		agreementMonthSetting.validate();

		this.agreementMonthSettingRepository.update(agreementMonthSetting);
	}

}
