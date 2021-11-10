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
 * 次回年休付与
 * 
 * @author shuichu_ishida
 */
@Getter
@Setter
public class NextAnnualLeaveGrant {

	/** 付与年月日 */
	private GeneralDate grantDate;
	/** 付与日数 */
	private Finally<LeaveGrantDayNumber> grantDays;
	/** 回数 */
	private GrantNum times;
	/** 期限日 */
	private GeneralDate deadLine;
	/** 時間年休上限日数 */
	private Optional<LimitedTimeHdDays> timeAnnualLeaveMaxDays;
	/** 時間年休上限時間 */
	private Optional<LimitedTimeHdTime> timeAnnualLeaveMaxTime;
	/** 半日年休上限回数 */
	private Optional<LimitedHalfHdCnt> halfDayAnnualLeaveMaxTimes;
	/** 所定日数 */
	private Optional<YearlyDays> prescribedDays;
	/** 控除日数 */
	private Optional<YearlyDays> deductedDays;
	/** 労働日数 */
	private Optional<YearlyDays> workingDays;
	/** 出勤率 */
	private Optional<AttendanceRate> attendanceRate;

	/**
	 * コンストラクタ
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
	 * 年休付与残数データを作成
	 * @param employeeId
	 * @return
	 */
	public AnnualLeaveGrantRemainingData toAnnualLeaveGrantRemainingData(String employeeId) {
		return new AnnualLeaveGrantRemainingData(employeeId, this.getGrantDate(),
				this.getDeadLine(), LeaveExpirationStatus.AVAILABLE, GrantRemainRegisterType.MONTH_CLOSE,
				this.toAnnualLeaveNumberInfo(), this.toAnnualLeaveConditionInfo());
	}

	/**
	 * 年休付与条件情報を作成
	 * @return
	 */
	private Optional<AnnualLeaveConditionInfo> toAnnualLeaveConditionInfo() {
		if (!this.existsConditionInfo())
			return Optional.empty();

		return Optional.of(AnnualLeaveConditionInfo.of(this.getPrescribedDaysOrZero(), this.getDeductedDaysOrZero(),
				this.getWorkingDaysOrZero()));
	}

	/**
	 * 付与条件情報が存在するか
	 * @return
	 */
	private boolean existsConditionInfo() {
		return this.getPrescribedDays().isPresent() || this.getDeductedDays().isPresent()
				|| this.getWorkingDays().isPresent();
	}

	/**
	 * 年休明細を作成
	 * @return
	 */
	private AnnualLeaveNumberInfo toAnnualLeaveNumberInfo() {
		return new AnnualLeaveNumberInfo(this.toLeaveNumberInfo());
	}

	/**
	 * 休暇数情報を作成
	 * @return
	 */
	private LeaveNumberInfo toLeaveNumberInfo() {
		return new LeaveNumberInfo(toLeaveGrantNumber(), new LeaveUsedNumber(), toLeaveRemainingNumber(),
				new LeaveUsedPercent(new BigDecimal(0)));
	}

	/**
	 * 所定日数を取得
	 * @return
	 */
	private YearlyDays getPrescribedDaysOrZero() {
		return this.getPrescribedDays().map(c -> c).orElse(new YearlyDays(0.0));
	}

	/**
	 * 控除日数を取得
	 * @return
	 */
	private YearlyDays getDeductedDaysOrZero() {
		return this.getDeductedDays().map(c -> c).orElse(new YearlyDays(0.0));
	}

	/**
	 * 労働日数を取得
	 * @return
	 */
	private YearlyDays getWorkingDaysOrZero() {
		return this.getWorkingDays().map(c -> c).orElse(new YearlyDays(0.0));
	}
	
	/**
	 * 出勤率を取得
	 * @return
	 */
	private AttendanceRate getAttendanceRateOrZero() {
		return this.getAttendanceRate().map(c -> c).orElse(new AttendanceRate(0.0));
	}
	
	/**
	 * 休暇付与数を作成
	 * @return
	 */
	private LeaveGrantNumber toLeaveGrantNumber() {
		return LeaveGrantNumber.of(getLeaveGrantDayNumber(), Optional.empty());
	}

	/**
	 * 付与日数を取得
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
	 * 休暇残数を作成
	 * @return
	 */
	private LeaveRemainingNumber toLeaveRemainingNumber() {
		return LeaveRemainingNumber.of(toLeaveRemainingDayNumber(), Optional.empty());
	}

	/**
	 * 月別休暇残日数を作成
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
	 * 年休付与情報を作成
	 * @param grantInfo
	 * @return
	 */
	public Optional<AnnualLeaveGrant> toAnnualLeaveGrant(Optional<AnnualLeaveGrant> grantInfo){
		// 付与情報に付与時の情報をセット
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
