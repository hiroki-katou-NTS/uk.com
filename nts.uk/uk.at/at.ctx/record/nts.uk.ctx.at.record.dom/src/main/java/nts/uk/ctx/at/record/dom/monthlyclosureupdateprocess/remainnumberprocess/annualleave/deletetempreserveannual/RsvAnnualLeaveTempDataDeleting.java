package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave.deletetempreserveannual;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.TempReserveLeaveMngRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author HungTT - <<Work>> 積立年休暫定データ削除
 *
 */

@Stateless
public class RsvAnnualLeaveTempDataDeleting {

	@Inject
	private TempReserveLeaveMngRepository tmpRsvLeaveMngRepo;

	/**
	 * 積立年休暫定データ削除
	 * 
	 * @param employeeId
	 * @param period
	 */
	public void deleteTempRsvAnnualLeaveData(String employeeId, DatePeriod period) {
		tmpRsvLeaveMngRepo.removeBetweenPeriod(employeeId, period);
	}

}
