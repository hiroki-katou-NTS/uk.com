package nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetcom;

import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetCom;
import nts.uk.screen.at.app.command.kmk.kmk004.MonthlyWorkTimeSetCommand;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb 会社別月単位労働時間
 */
@AllArgsConstructor
public class MonthlyWorkTimeSetComCommand extends MonthlyWorkTimeSetCommand {

	public MonthlyWorkTimeSetCom toDomain() {
		
		return MonthlyWorkTimeSetCom.of(AppContexts.user().companyId(),
				EnumAdaptor.valueOf(this.getLaborAttr(), LaborWorkTypeAttr.class), new YearMonth(this.getYm()),
				this.getLaborTime().toDomain());
	}

}
