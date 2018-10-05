package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave.calculateremainnum;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnAndRsvRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT - <<Work>> 年休残数計算
 *
 */

@Stateless
public class RemainAnnualLeaveCalculation {

	@Inject
	private GetAnnAndRsvRemNumWithinPeriod getRemainNum;

	// 年休残数計算
	public AggrResultOfAnnAndRsvLeave calculateRemainAnnualHoliday(AggrPeriodEachActualClosure period, String empId) {
		String companyId = AppContexts.user().companyId();
		return getRemainNum.algorithm(companyId, empId, period.getPeriod(), InterimRemainMngMode.MONTHLY,
				period.getPeriod().end(), true, true, Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
	}
}
