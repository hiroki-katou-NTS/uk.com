package nts.uk.ctx.at.shared.dom.yearholidaygrant.export;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
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
	
	/**
	 * コンストラクタ
	 */
	public NextAnnualLeaveGrant(){
		
		this.grantDate = GeneralDate.today();
		this.grantDays = new GrantDays(BigDecimal.valueOf(0));
		this.times = new GrantNum(0);
		this.timeAnnualLeaveMaxDays = Optional.empty();
		this.timeAnnualLeaveMaxTime = Optional.empty();
		this.halfDayAnnualLeaveMaxTimes = Optional.empty();
	}
	
	/**
	 * ファクトリー
	 * @param grantDate 付与年月日
	 * @param grantDays 付与日数
	 * @param times 回数
	 * @param timeAnnualLeaveMaxDays 時間年休上限日数
	 * @param timeAnnualLeaveMaxTime　時間年休上限時間
	 * @param halfDayAnnualLeaveMaxTimes 半日年休上限回数
	 * @return 次回年休付与
	 */
	public static NextAnnualLeaveGrant of(
			GeneralDate grantDate,
			GrantDays grantDays,
			GrantNum times,
			Optional<LimitedTimeHdDays> timeAnnualLeaveMaxDays,
			Optional<LimitedTimeHdTime> timeAnnualLeaveMaxTime,
			Optional<LimitedHalfHdCnt> halfDayAnnualLeaveMaxTimes){
	
		NextAnnualLeaveGrant domain = new NextAnnualLeaveGrant();
		domain.grantDate = grantDate;
		domain.grantDays = grantDays;
		domain.times = times;
		domain.timeAnnualLeaveMaxDays = timeAnnualLeaveMaxDays;
		domain.timeAnnualLeaveMaxTime = timeAnnualLeaveMaxTime;
		domain.halfDayAnnualLeaveMaxTimes = halfDayAnnualLeaveMaxTimes;
		return domain;
	}
}
