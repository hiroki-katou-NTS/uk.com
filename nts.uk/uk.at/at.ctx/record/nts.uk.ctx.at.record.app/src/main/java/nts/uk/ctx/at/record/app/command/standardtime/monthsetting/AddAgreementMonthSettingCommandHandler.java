package nts.uk.ctx.at.record.app.command.standardtime.monthsetting;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementMonthSetDomainService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementMonthSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.service.WorkingConditionService;

/**
 * 
 * @author nampt 特例設定 screen add month
 *
 */
@Stateless
public class AddAgreementMonthSettingCommandHandler extends CommandHandlerWithResult<AddAgreementMonthSettingCommand, List<String>> {

	@Inject
	private AgreementMonthSetDomainService agreementMonthSetDomainService;

	@Inject
	private RecordDomRequireService requireService;

	@Override
	protected List<String> handle(CommandHandlerContext<AddAgreementMonthSettingCommand> context) {
		AddAgreementMonthSettingCommand command = context.getCommand();
		
		if(this.agreementMonthSetDomainService.checkExistData(command.getEmployeeId(), new BigDecimal(command.getYearMonthValue()))){
			throw new BusinessException("Msg_61");
		};
		
		AgreementMonthSetting agreementMonthSetting = new AgreementMonthSetting(
				command.getEmployeeId(),
				new YearMonth(command.getYearMonthValue()),
				/** TODO: 36協定時間対応により、コメントアウトされた */
				new OneMonthErrorAlarmTime());
//				new ErrorOneMonth(command.getErrorOneMonth()),
//				new AlarmOneMonth(command.getAlarmOneMonth()));
		
//		agreementMonthSetting.validate();
		
		Optional<WorkingConditionItem> workingConditionItem = WorkingConditionService
				.findWorkConditionByEmployee(requireService.createRequire(), 
						command.getEmployeeId(), GeneralDate.today());

		return this.agreementMonthSetDomainService.add(agreementMonthSetting, workingConditionItem);
	}

}
