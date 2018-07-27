package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave.calculateremainnum;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnAndRsvRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.TempAnnualLeaveMngMode;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class RemainAnnualLeaveCalculation {

	@Inject
	private GetAnnAndRsvRemNumWithinPeriod getRemainNum;

	public AggrResultOfAnnAndRsvLeave calculateRemainAnnualHoliday(AggrPeriodEachActualClosure period, String empId) {
		String companyId = AppContexts.user().companyId();
		return getRemainNum.algorithm(companyId, empId, period.getPeriod(), TempAnnualLeaveMngMode.MONTHLY,
				period.getPeriod().end(), true, true, Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
	}
}
