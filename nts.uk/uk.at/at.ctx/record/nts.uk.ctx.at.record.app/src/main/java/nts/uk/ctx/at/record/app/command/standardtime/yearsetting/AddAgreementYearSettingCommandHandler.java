package nts.uk.ctx.at.record.app.command.standardtime.yearsetting;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementYearSetDomainService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementYearSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.service.WorkingConditionService;

/**
 * 
 * @author nampt 特例設定 screen add year
 *
 */
@Stateless
public class AddAgreementYearSettingCommandHandler extends CommandHandlerWithResult<AddAgreementYearSettingCommand, List<String>> {

	@Inject
	private AgreementYearSetDomainService agreementYearSetDomainService;
	
	@Inject
	private RecordDomRequireService requireService;

	@Override
	protected List<String> handle(CommandHandlerContext<AddAgreementYearSettingCommand> context) {
		AddAgreementYearSettingCommand command = context.getCommand();
		
		if(this.agreementYearSetDomainService.checkExistData(command.getEmployeeId(), new BigDecimal(command.getYearValue()))){
			throw new BusinessException("Msg_61");
		};
		
		AgreementYearSetting agreementYearSetting = new AgreementYearSetting(
				command.getEmployeeId(),
				command.getYearValue(), 
				new OneYearErrorAlarmTime());
				/** TODO: 36協定時間対応により、コメントアウトされた */
//				new ErrorOneYear(command.getErrorOneYear()),
//				new AlarmOneYear(command.getAlarmOneYear()));
		
//		agreementYearSetting.validate();
		
		Optional<WorkingConditionItem> workingConditionItem = WorkingConditionService
				.findWorkConditionByEmployee(requireService.createRequire(), 
						command.getEmployeeId(), GeneralDate.today());

		return this.agreementYearSetDomainService.add(agreementYearSetting, workingConditionItem);
	}

}
