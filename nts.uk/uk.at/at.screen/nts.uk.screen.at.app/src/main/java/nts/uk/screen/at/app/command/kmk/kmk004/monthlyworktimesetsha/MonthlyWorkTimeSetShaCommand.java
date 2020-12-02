package nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetsha;

import lombok.Data;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetSha;
import nts.uk.screen.at.app.command.kmk.kmk004.MonthlyLaborTimeCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.MonthlyWorkTimeSetCommand;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb 社員別月単位労働時間
 */
@Data
public class MonthlyWorkTimeSetShaCommand extends MonthlyWorkTimeSetCommand {

	@Getter
	/** 社員ID */
	private String empId;

	public MonthlyWorkTimeSetSha toDomain() {

		return MonthlyWorkTimeSetSha.of(AppContexts.user().companyId(), this.empId,
				EnumAdaptor.valueOf(this.getLaborAttr(), LaborWorkTypeAttr.class), new YearMonth(this.getYm()),
				this.getLaborTime().toDomain());
	}

	public MonthlyWorkTimeSetShaCommand(String empId, int laborAttr, int ym, MonthlyLaborTimeCommand laborTime) {
		super(laborAttr, ym, laborTime);
		this.empId = empId;
	}

}
