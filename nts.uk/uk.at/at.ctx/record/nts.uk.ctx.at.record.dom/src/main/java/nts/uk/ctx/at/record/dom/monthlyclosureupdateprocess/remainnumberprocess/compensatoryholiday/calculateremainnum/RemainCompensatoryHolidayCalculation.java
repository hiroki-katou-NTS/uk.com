package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.compensatoryholiday.calculateremainnum;

import java.util.Collections;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngParam;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT - <<Work>> 代休残数計算
 *
 */

@Stateless
public class RemainCompensatoryHolidayCalculation {

	@Inject
	private BreakDayOffMngInPeriodQuery breakDayoffMng;

	// 代休残数計算
	public BreakDayOffRemainMngOfInPeriod calculateRemainCompensatory(AggrPeriodEachActualClosure period,
			String empId) {
		String companyId = AppContexts.user().companyId();
		BreakDayOffRemainMngParam param = new BreakDayOffRemainMngParam(companyId, empId, period.getPeriod(), true,
				period.getPeriod().end(), false, Collections.emptyList(), Collections.emptyList(),
				Collections.emptyList());
		return this.breakDayoffMng.getBreakDayOffMngInPeriod(param);
	}

}
