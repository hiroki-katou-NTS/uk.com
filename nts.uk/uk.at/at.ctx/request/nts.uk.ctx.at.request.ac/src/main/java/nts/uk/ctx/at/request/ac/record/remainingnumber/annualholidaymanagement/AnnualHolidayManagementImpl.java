package nts.uk.ctx.at.request.ac.record.remainingnumber.annualholidaymanagement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.uk.ctx.at.record.pub.workrecord.remainingnumbermanagement.AnnualHolidayManagementPub;
import nts.uk.ctx.at.record.pub.workrecord.remainingnumbermanagement.AttendRateAtNextHolidayExport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualholidaymanagement.AnnualHolidayManagementAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualholidaymanagement.AttendRateAtNextHolidayImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualholidaymanagement.NextAnnualLeaveGrantImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualholidaymanagement.nextannualleavegrantimport.AttendanceDaysMonthImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualholidaymanagement.nextannualleavegrantimport.AttendanceRateImport;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantDays;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantNum;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedHalfHdCnt;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimeHdDays;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimeHdTime;

public class AnnualHolidayManagementImpl implements AnnualHolidayManagementAdapter {

	@Inject
	private AnnualHolidayManagementPub annualPub;

	@Override
	public List<NextAnnualLeaveGrantImport> acquireNextHolidayGrantDate(String cId, String sId) {
		return this.annualPub.acquireNextHolidayGrantDate(cId, sId).stream()
				.map(x -> new NextAnnualLeaveGrantImport(x.getGrantDate(), new GrantDays(x.getGrantDays().v()),
						new GrantNum(x.getTimes().v()), getLimitedHDDays(x.getTimeAnnualLeaveMaxDays()),
						getLimitedTimeHdTime(x.getTimeAnnualLeaveMaxTime()),
						getLimitedHalfHdCnt(x.getHalfDayAnnualLeaveMaxTimes())))
				.collect(Collectors.toList());
	}

	private Optional<LimitedHalfHdCnt> getLimitedHalfHdCnt(Optional<LimitedHalfHdCnt> halfDayAnnualLeaveMaxTimes) {
		return halfDayAnnualLeaveMaxTimes.isPresent() ? Optional.of(halfDayAnnualLeaveMaxTimes.get())
				: Optional.empty();
	}

	private Optional<LimitedTimeHdTime> getLimitedTimeHdTime(Optional<LimitedTimeHdTime> timeAnnualLeaveMaxTime) {
		return timeAnnualLeaveMaxTime.isPresent() ? Optional.of(timeAnnualLeaveMaxTime.get()) : Optional.empty();
	}

	private Optional<LimitedTimeHdDays> getLimitedHDDays(Optional<LimitedTimeHdDays> timeAnnualLeaveMaxDays) {
		return timeAnnualLeaveMaxDays.isPresent() ? Optional.of(timeAnnualLeaveMaxDays.get()) : Optional.empty();

	}

	@Override
	public Optional<AttendRateAtNextHolidayImport> getDaysPerYear(String companyId, String employeeId) {
		Optional<AttendRateAtNextHolidayExport> nextAnualOpt = this.annualPub.getDaysPerYear(companyId, employeeId);
		return Optional.ofNullable(nextAnualOpt.get())
				.map(x -> new AttendRateAtNextHolidayImport(x.getNextHolidayGrantDate(),
						new GrantDays(x.getNextHolidayGrantDays().v()),
						new AttendanceRateImport(x.getAttendanceRate().v().doubleValue()),
						new AttendanceDaysMonthImport(x.getAttendanceRate().v().doubleValue()),
						new AttendanceDaysMonthImport(x.getPredeterminedDays().v().doubleValue()),
						new AttendanceDaysMonthImport(x.getAnnualPerYearDays().v().doubleValue())));

	}

}
