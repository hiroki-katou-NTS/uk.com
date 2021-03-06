package nts.uk.ctx.at.shared.dom.yearholidaygrant.export;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.common.days.MonthlyDays;
import nts.uk.ctx.at.shared.dom.common.days.YearlyDays;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveConditionInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveNumberInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveNumberInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedPercent;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveGrant;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AttendanceRate;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantNum;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedHalfHdCnt;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimeHdDays;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimeHdTime;

/**
 * ??????????????????
 * 
 * @author shuichu_ishida
 */
@Getter
@Setter
public class NextAnnualLeaveGrant {

	/** ??????????????? */
	private GeneralDate grantDate;
	/** ???????????? */
	private Finally<LeaveGrantDayNumber> grantDays;
	/** ?????? */
	private GrantNum times;
	/** ????????? */
	private GeneralDate deadLine;
	/** ???????????????????????? */
	private Optional<LimitedTimeHdDays> timeAnnualLeaveMaxDays;
	/** ???????????????????????? */
	private Optional<LimitedTimeHdTime> timeAnnualLeaveMaxTime;
	/** ???????????????????????? */
	private Optional<LimitedHalfHdCnt> halfDayAnnualLeaveMaxTimes;
	/** ???????????? */
	private Optional<YearlyDays> prescribedDays;
	/** ???????????? */
	private Optional<YearlyDays> deductedDays;
	/** ???????????? */
	private Optional<YearlyDays> workingDays;
	/** ????????? */
	private Optional<AttendanceRate> attendanceRate;

	/**
	 * ?????????????????????
	 */
	public NextAnnualLeaveGrant() {

		this.grantDate = GeneralDate.today();
		this.grantDays = Finally.empty();
		this.times = new GrantNum(0);
		this.timeAnnualLeaveMaxDays = Optional.empty();
		this.timeAnnualLeaveMaxTime = Optional.empty();
		this.halfDayAnnualLeaveMaxTimes = Optional.empty();
		this.prescribedDays = Optional.empty();
		this.deductedDays = Optional.empty();
		this.workingDays = Optional.empty();
		this.attendanceRate = Optional.empty();
	}

	/**
	 * ????????????????????????????????????
	 * @param employeeId
	 * @return
	 */
	public AnnualLeaveGrantRemainingData toAnnualLeaveGrantRemainingData(String employeeId) {
		return new AnnualLeaveGrantRemainingData(employeeId, this.getGrantDate(),
				this.getDeadLine(), LeaveExpirationStatus.AVAILABLE, GrantRemainRegisterType.MONTH_CLOSE,
				this.toAnnualLeaveNumberInfo(), this.toAnnualLeaveConditionInfo());
	}

	/**
	 * ?????????????????????????????????
	 * @return
	 */
	private Optional<AnnualLeaveConditionInfo> toAnnualLeaveConditionInfo() {
		if (!this.existsConditionInfo())
			return Optional.empty();

		return Optional.of(AnnualLeaveConditionInfo.of(this.getPrescribedDaysOrZero(), this.getDeductedDaysOrZero(),
				this.getWorkingDaysOrZero()));
	}

	/**
	 * ????????????????????????????????????
	 * @return
	 */
	private boolean existsConditionInfo() {
		return this.getPrescribedDays().isPresent() || this.getDeductedDays().isPresent()
				|| this.getWorkingDays().isPresent();
	}

	/**
	 * ?????????????????????
	 * @return
	 */
	private AnnualLeaveNumberInfo toAnnualLeaveNumberInfo() {
		return new AnnualLeaveNumberInfo(this.toLeaveNumberInfo());
	}

	/**
	 * ????????????????????????
	 * @return
	 */
	private LeaveNumberInfo toLeaveNumberInfo() {
		return new LeaveNumberInfo(toLeaveGrantNumber(), new LeaveUsedNumber(), toLeaveRemainingNumber(),
				new LeaveUsedPercent(new BigDecimal(0)));
	}

	/**
	 * ?????????????????????
	 * @return
	 */
	private YearlyDays getPrescribedDaysOrZero() {
		return this.getPrescribedDays().map(c -> c).orElse(new YearlyDays(0.0));
	}

	/**
	 * ?????????????????????
	 * @return
	 */
	private YearlyDays getDeductedDaysOrZero() {
		return this.getDeductedDays().map(c -> c).orElse(new YearlyDays(0.0));
	}

	/**
	 * ?????????????????????
	 * @return
	 */
	private YearlyDays getWorkingDaysOrZero() {
		return this.getWorkingDays().map(c -> c).orElse(new YearlyDays(0.0));
	}
	
	/**
	 * ??????????????????
	 * @return
	 */
	private AttendanceRate getAttendanceRateOrZero() {
		return this.getAttendanceRate().map(c -> c).orElse(new AttendanceRate(0.0));
	}
	
	/**
	 * ????????????????????????
	 * @return
	 */
	private LeaveGrantNumber toLeaveGrantNumber() {
		return LeaveGrantNumber.of(getLeaveGrantDayNumber(), Optional.empty());
	}

	/**
	 * ?????????????????????
	 * @return
	 */
	private LeaveGrantDayNumber getLeaveGrantDayNumber() {
		if (this.getGrantDays().isPresent()) {
			return this.getGrantDays().get();
		} else {
			return new LeaveGrantDayNumber(0.0);
		}
	}

	/**
	 * ?????????????????????
	 * @return
	 */
	private LeaveRemainingNumber toLeaveRemainingNumber() {
		return LeaveRemainingNumber.of(toLeaveRemainingDayNumber(), Optional.empty());
	}

	/**
	 * ??????????????????????????????
	 * @return
	 */
	private LeaveRemainingDayNumber toLeaveRemainingDayNumber() {
		if (this.getGrantDays().isPresent()) {
			return new LeaveRemainingDayNumber(this.getGrantDays().get().v());
		} else {
			return new LeaveRemainingDayNumber(0.0);
		}
	}
	
	/**
	 * ???????????????????????????
	 * @param grantInfo
	 * @return
	 */
	public Optional<AnnualLeaveGrant> toAnnualLeaveGrant(Optional<AnnualLeaveGrant> grantInfo){
		// ?????????????????????????????????????????????
		double oldGrantDays = 0.0;
		if (grantInfo.isPresent()) {
			oldGrantDays = grantInfo.get().getGrantDays().v().doubleValue();
		}
		return Optional.of(AnnualLeaveGrant.of(
				new AnnualLeaveGrantDayNumber(oldGrantDays + getLeaveGrantDayNumber().v()),
				this.getWorkingDaysOrZero(),
				this.getPrescribedDaysOrZero(),
				this.getDeductedDaysOrZero(),
				new MonthlyDays(0.0), 
				new MonthlyDays(0.0),
				this.getAttendanceRateOrZero()));
	}
}
