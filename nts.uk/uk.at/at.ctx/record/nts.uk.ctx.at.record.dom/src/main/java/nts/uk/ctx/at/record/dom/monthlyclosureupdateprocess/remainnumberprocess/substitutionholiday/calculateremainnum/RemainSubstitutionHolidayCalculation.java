package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.substitutionholiday.calculateremainnum;

import java.util.Collections;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecMngInPeriodParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentMngInPeriodQuery;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT - <<Work>> 振休残数計算
 *
 */

@Stateless
public class RemainSubstitutionHolidayCalculation {

	@Inject
	private AbsenceReruitmentMngInPeriodQuery query;

	// 振休残数計算
	public AbsRecRemainMngOfInPeriod calculateRemainHoliday(AggrPeriodEachActualClosure period, String empId) {
		String companyId = AppContexts.user().companyId();
		AbsRecMngInPeriodParamInput param = new AbsRecMngInPeriodParamInput(companyId, empId, period.getPeriod(),
				period.getPeriod().end(), true, false, Collections.emptyList(), Collections.emptyList(),
				Collections.emptyList());
		return query.getAbsRecMngInPeriod(param);
	}

}
