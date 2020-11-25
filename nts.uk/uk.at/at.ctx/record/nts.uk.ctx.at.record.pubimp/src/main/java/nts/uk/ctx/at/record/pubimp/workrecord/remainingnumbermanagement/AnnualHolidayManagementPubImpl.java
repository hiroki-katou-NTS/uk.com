package nts.uk.ctx.at.record.pubimp.workrecord.remainingnumbermanagement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.remainingnumbermanagement.RCAnnualHolidayManagement;
import nts.uk.ctx.at.record.pub.workrecord.remainingnumbermanagement.AnnualHolidayManagementPub;
import nts.uk.ctx.at.record.pub.workrecord.remainingnumbermanagement.AttendRateAtNextHolidayExport;
import nts.uk.ctx.at.record.pub.workrecord.remainingnumbermanagement.NextAnnualLeaveGrantExport;

@Stateless
public class AnnualHolidayManagementPubImpl implements AnnualHolidayManagementPub {

	@Inject
	private RCAnnualHolidayManagement rAnnualHolidayManagement;

	/**
	 * RequestList210 次回年休付与日を取得する
	 * 
	 * @param cId
	 * @param sId
	 * @return
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<NextAnnualLeaveGrantExport> acquireNextHolidayGrantDate(String companyId, String employeeId,
			Optional<GeneralDate> referenceDate) {
		return rAnnualHolidayManagement.acquireNextHolidayGrantDate(companyId, employeeId, referenceDate).stream()
				.map(x -> new NextAnnualLeaveGrantExport(x.getGrantDate(), x.getGrantDays(), x.getTimes(),
						x.getTimeAnnualLeaveMaxDays(), x.getTimeAnnualLeaveMaxTime(),
						x.getHalfDayAnnualLeaveMaxTimes()))
				.collect(Collectors.toList());
	}

	/**
	 * RequestList323 次回年休付与時点の出勤率・出勤日数・所定日数・年間所定日数を取得する
	 * 
	 * @param companyId  会社ID
	 * @param employeeId 社員ID
	 * @return 次回年休付与時点出勤率
	 */

	@Override
	public Optional<AttendRateAtNextHolidayExport> getDaysPerYear(String companyId, String employeeId) {

		return rAnnualHolidayManagement.getDaysPerYear(companyId, employeeId).map(x -> {
			return new AttendRateAtNextHolidayExport(x.getNextHolidayGrantDate(), x.getNextHolidayGrantDays(),
					x.getAttendanceRate(), x.getAttendanceDays(), x.getPredeterminedDays(), x.getAnnualPerYearDays());
		});

	}

}
