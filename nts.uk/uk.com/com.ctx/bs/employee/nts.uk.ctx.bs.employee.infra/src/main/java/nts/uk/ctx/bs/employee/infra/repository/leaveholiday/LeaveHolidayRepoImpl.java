/**
 * 
 */
package nts.uk.ctx.bs.employee.infra.repository.leaveholiday;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.leaveholiday.LeaveHoliday;
import nts.uk.ctx.bs.employee.dom.leaveholiday.LeaveHolidayRepository;
import nts.uk.ctx.bs.employee.infra.entity.temporaryAbsence.BsymtTemporaryAbsence;

/**
 * @author danpv
 *
 */
@Stateless
public class LeaveHolidayRepoImpl extends JpaRepository implements LeaveHolidayRepository {

	private static final String GET_BY_EMPID_AND_STDDATE = "select lh from BsymtTemporaryAbsence lh"
			+ " where lh.bsymtTemporaryAbsencePK.sid = sid" + " and lh.startDate <= stdDate"
			+ " and lh.endDate >= stdDate";

	@Override
	public Optional<LeaveHoliday> getByEmpIdAndStandDate(String employeeId, GeneralDate standandDate) {
		List<BsymtTemporaryAbsence> datas = this.queryProxy()
				.query(GET_BY_EMPID_AND_STDDATE, BsymtTemporaryAbsence.class).setParameter("sid", employeeId)
				.setParameter("stdDate", standandDate).getList();
		if (!datas.isEmpty()) {
			BsymtTemporaryAbsence ent = datas.get(0);
			return Optional.of(LeaveHoliday.createLeaveHoliday(ent.bsymtTemporaryAbsencePK.sid, ent.leaveHolidayId,
					ent.startDate, ent.endDate, ent.leaveHolidayAtr, ent.familyMemberId, ent.reason, ent.multiple == 1,
					ent.birthday));
		}
		return Optional.empty();
	}

}
