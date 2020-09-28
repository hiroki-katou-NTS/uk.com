package nts.uk.ctx.at.shared.dom.yearholidaygrant.export;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.YearDayNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AttendanceRate;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantDays;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantNum;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedHalfHdCnt;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimeHdDays;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimeHdTime;

/**
 * 次回年休付与
 * @author shuichu_ishida
 */
@Getter
@Setter
public class NextAnnualLeaveGrant {

	/** 付与年月日 */
	private GeneralDate grantDate;
	/** 付与日数 */
	private GrantDays grantDays;
	/** 回数 */
	private GrantNum times;
	/** 時間年休上限日数 */
	private Optional<LimitedTimeHdDays> timeAnnualLeaveMaxDays;
	/** 時間年休上限時間 */
	private Optional<LimitedTimeHdTime> timeAnnualLeaveMaxTime;
	/** 半日年休上限回数 */
	private Optional<LimitedHalfHdCnt> halfDayAnnualLeaveMaxTimes;
	/** 所定日数 */
	private Optional<YearDayNumber> prescribedDays;
	/** 控除日数 */
	private Optional<YearDayNumber> deductedDays;
	/** 労働日数 */
	private Optional<YearDayNumber> workingDays;
	/** 出勤率 */
	private Optional<AttendanceRate> attendanceRate;
	
	/**
	 * コンストラクタ
	 */
	public NextAnnualLeaveGrant(){
		
		this.grantDate = GeneralDate.today();
		this.grantDays = new GrantDays(0.0);
		this.times = new GrantNum(0);
		this.timeAnnualLeaveMaxDays = Optional.empty();
		this.timeAnnualLeaveMaxTime = Optional.empty();
		this.halfDayAnnualLeaveMaxTimes = Optional.empty();
		this.prescribedDays = Optional.empty();
		this.deductedDays = Optional.empty();
		this.workingDays = Optional.empty();
		this.attendanceRate = Optional.empty();
	}
}
