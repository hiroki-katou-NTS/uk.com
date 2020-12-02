package nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetemp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetEmp;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.screen.at.app.command.kmk.kmk004.MonthlyWorkTimeSetCommand;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb 雇用別月単位労働時間
 */
@AllArgsConstructor
public class MonthlyWorkTimeSetEmpCommand extends MonthlyWorkTimeSetCommand {

	@Getter
	/** 社員ID */
	private EmploymentCode employment;

	public MonthlyWorkTimeSetEmp toDomain() {

		return MonthlyWorkTimeSetEmp.of(AppContexts.user().companyId(), employment,
				EnumAdaptor.valueOf(this.getLaborAttr(), LaborWorkTypeAttr.class), new YearMonth(this.getYm()),
				this.getLaborTime().toDomain());
	}

}
