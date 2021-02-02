package nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetemp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetEmp;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.screen.at.app.command.kmk.kmk004.MonthlyLaborTimeCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.MonthlyWorkTimeSetCommand;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb 雇用別月単位労働時間
 */
@NoArgsConstructor
@Setter
public class MonthlyWorkTimeSetEmpCommand extends MonthlyWorkTimeSetCommand {

	@Getter
	/** 雇用コード */
	private String employmentCode;

	public MonthlyWorkTimeSetEmp toDomain() {

		return MonthlyWorkTimeSetEmp.of(AppContexts.user().companyId(), new EmploymentCode(this.employmentCode),
				EnumAdaptor.valueOf(this.getLaborAttr(), LaborWorkTypeAttr.class), new YearMonth(this.getYearMonth()),
				this.getLaborTime().toDomain());
	}

	public MonthlyWorkTimeSetEmpCommand(String empCd, int laborAttr, int ym, MonthlyLaborTimeCommand laborTime) {
		super(laborAttr, ym, laborTime);
		this.employmentCode = empCd;
	}

}
