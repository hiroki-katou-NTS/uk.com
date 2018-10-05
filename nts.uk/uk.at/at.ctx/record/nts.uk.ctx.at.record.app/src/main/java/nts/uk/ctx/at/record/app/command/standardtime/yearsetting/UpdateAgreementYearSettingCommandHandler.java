package nts.uk.ctx.at.record.app.command.standardtime.yearsetting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.standardtime.AgreementYearSetting;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmOneYear;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorOneYear;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementYearSetDomainService;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.service.WorkingConditionService;

/**
 * 
 * @author nampt 特例設定 screen update year
 *
 */
@Stateless
public class UpdateAgreementYearSettingCommandHandler extends CommandHandlerWithResult<UpdateAgreementYearSettingCommand, List<String>> {

	@Inject
	private AgreementYearSetDomainService agreementYearSetDomainService;
	
	@Inject
	private WorkingConditionService workingConditionService;

	@Override
	protected List<String> handle(CommandHandlerContext<UpdateAgreementYearSettingCommand> context) {
		UpdateAgreementYearSettingCommand command = context.getCommand();

		AgreementYearSetting agreementYearSetting = new AgreementYearSetting(
				command.getEmployeeId(),
				command.getYearValue(),
				new ErrorOneYear(command.getErrorOneYear()),
				new AlarmOneYear(command.getAlarmOneYear()));
		
//		agreementYearSetting.validate();
		
		Optional<WorkingConditionItem> workingConditionItem = this.workingConditionService.findWorkConditionByEmployee(command.getEmployeeId(), GeneralDate.today());
		Integer yearMonthValueOld = command.getYearMonthValueOld(); 
		return this.agreementYearSetDomainService.update(agreementYearSetting, workingConditionItem, yearMonthValueOld);
	}
}
