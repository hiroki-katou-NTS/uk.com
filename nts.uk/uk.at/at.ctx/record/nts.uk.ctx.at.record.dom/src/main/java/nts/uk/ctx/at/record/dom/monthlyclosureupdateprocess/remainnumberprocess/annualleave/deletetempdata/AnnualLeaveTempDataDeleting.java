package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave.deletetempdata;

import javax.ejb.Stateless;
import javax.inject.Inject;

<<<<<<< HEAD
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemainRepository;
=======
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngRepository;
>>>>>>> pj/pr/develop
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 
 * @author HungTT - <<Work>> 年休暫定データ削除
 *
 */

@Stateless
public class AnnualLeaveTempDataDeleting {

	@Inject
	private InterimRemainRepository tmpAnnualLeaveRepo;
	
	

	/**
	 * 年休暫定データ削除
	 * 
	 * @param employeeId
	 * @param period
	 */
	public void deleteTempAnnualLeaveData(String employeeId, DatePeriod period) {
		tmpAnnualLeaveRepo.removeByPeriod(employeeId, period);
	}
}
