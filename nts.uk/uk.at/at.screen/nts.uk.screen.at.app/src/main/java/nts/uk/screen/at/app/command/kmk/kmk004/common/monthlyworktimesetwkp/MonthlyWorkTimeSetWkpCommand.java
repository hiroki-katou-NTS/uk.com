package nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetwkp;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetWkp;
import nts.uk.screen.at.app.command.kmk.kmk004.MonthlyLaborTimeCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.MonthlyWorkTimeSetCommand;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 *
 *         職場別月単位労働時間
 */
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyWorkTimeSetWkpCommand extends MonthlyWorkTimeSetCommand {

	/** 職場ID */
	@Setter
	private String workplaceId;

	public MonthlyWorkTimeSetWkp toDomain() {

		return MonthlyWorkTimeSetWkp.of(AppContexts.user().companyId(), this.workplaceId,
				EnumAdaptor.valueOf(this.getLaborAttr(), LaborWorkTypeAttr.class), new YearMonth(this.getYearMonth()),
				this.getLaborTime().toDomain());
	}

	public MonthlyWorkTimeSetWkpCommand(String workplaceId, int laborAttr, int ym, MonthlyLaborTimeCommand laborTime) {
		super(laborAttr, ym, laborTime);
		this.workplaceId = workplaceId;
	}

}
