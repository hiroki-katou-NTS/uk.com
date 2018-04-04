package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.closurestatusmng;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagementRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT - <<Work>> 締め状態管理
 *
 */

@Stateless
public class ClosureStatusMng {

	@Inject
	private ClosureStatusManagementRepository closureSttRepo;

	public void closureStatusManage(AggrPeriodEachActualClosure period) {
		String employeeId = AppContexts.user().employeeId();
		ClosureStatusManagement statusMng = new ClosureStatusManagement(period.getYearMonth(), employeeId,
				period.getClosureId().value, period.getClosureDate(), period.getPeriod());
		closureSttRepo.add(statusMng);
	}
}
