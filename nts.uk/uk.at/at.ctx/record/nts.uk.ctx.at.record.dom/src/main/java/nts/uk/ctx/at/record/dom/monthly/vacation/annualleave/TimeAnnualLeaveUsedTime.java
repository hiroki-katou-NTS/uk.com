package nts.uk.ctx.at.record.dom.monthly.vacation.annualleave;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.UsedMinutes;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;

/**
 * 時間年休使用時間
 * @author shuichu_ishida
 */
@Getter
public class TimeAnnualLeaveUsedTime {

	/** 使用回数 */
	private UsedTimes usedTimes;
	/** 使用時間 */
	private UsedMinutes usedTime;
	/** 使用時間付与後 */
	private Optional<UsedMinutes> usedTimeAfterGrant;
	
	/**
	 * コンストラクタ
	 */
	public TimeAnnualLeaveUsedTime(){
		
		this.usedTimes = new UsedTimes(0);
		this.usedTime = new UsedMinutes(0);
		this.usedTimeAfterGrant = Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param usedTimes 使用回数
	 * @param usedTime 使用時間
	 * @param usedTimeAfterGrant 使用時間付与後
	 * @return 時間年休使用時間
	 */
	public static TimeAnnualLeaveUsedTime of(
			UsedTimes usedTimes,
			UsedMinutes usedTime,
			Optional<UsedMinutes> usedTimeAfterGrant){
		
		TimeAnnualLeaveUsedTime domain = new TimeAnnualLeaveUsedTime();
		domain.usedTimes = usedTimes;
		domain.usedTime = usedTime;
		domain.usedTimeAfterGrant = usedTimeAfterGrant;
		return domain;
	}
}
