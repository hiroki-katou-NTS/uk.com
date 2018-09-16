package nts.uk.ctx.at.request.ac.record.remainingnumber.annualholidaymanagement;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.workrecord.remainingnumbermanagement.AnnualHolidayManagementPub;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualholidaymanagement.AnnualHolidayManagementAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualholidaymanagement.AttendRateAtNextHolidayImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualholidaymanagement.NextAnnualLeaveGrantImport;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedHalfHdCnt;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimeHdDays;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimeHdTime;

@Stateless
public class AnnualHolidayManagementImpl implements AnnualHolidayManagementAdapter {

	@Inject
	private AnnualHolidayManagementPub annualPub;

	@Override
	public List<NextAnnualLeaveGrantImport> acquireNextHolidayGrantDate(String cId, String sId, GeneralDate baseDate) {
		return this.annualPub.acquireNextHolidayGrantDate(cId, sId,Optional.of(baseDate)).stream()
				.map(x -> new NextAnnualLeaveGrantImport(x.getGrantDate(),
						x.getGrantDays().v(),
						x.getTimes().v(),
						getLimitedHDDays(x.getTimeAnnualLeaveMaxDays()),
						getLimitedTimeHdTime(x.getTimeAnnualLeaveMaxTime()),
						getLimitedHalfHdCnt(x.getHalfDayAnnualLeaveMaxTimes())))
				.collect(Collectors.toList());
	}

	private Optional<Integer> getLimitedHalfHdCnt(Optional<LimitedHalfHdCnt> halfDayAnnualLeaveMaxTimes) {
		return halfDayAnnualLeaveMaxTimes.isPresent() ? Optional.of(halfDayAnnualLeaveMaxTimes.get().v())
				: Optional.empty();
	}

	private Optional<Integer> getLimitedTimeHdTime(Optional<LimitedTimeHdTime> timeAnnualLeaveMaxTime) {
		return timeAnnualLeaveMaxTime.isPresent() ? Optional.of(timeAnnualLeaveMaxTime.get().v()) : Optional.empty();
	}

	private Optional<Integer> getLimitedHDDays(Optional<LimitedTimeHdDays> timeAnnualLeaveMaxDays) {
		return timeAnnualLeaveMaxDays.isPresent() ? Optional.of(timeAnnualLeaveMaxDays.get().v()) : Optional.empty();

	}

	@Override
	public Optional<AttendRateAtNextHolidayImport> getDaysPerYear(String companyId, String employeeId) {
		return this.annualPub.getDaysPerYear(companyId, employeeId)
				.map(x -> new AttendRateAtNextHolidayImport(x.getNextHolidayGrantDate(),
						x.getNextHolidayGrantDays().v(),
						x.getAttendanceRate().v(), x.getAttendanceRate().v(),
						x.getPredeterminedDays().v(), x.getAnnualPerYearDays().v()));

	}

}
