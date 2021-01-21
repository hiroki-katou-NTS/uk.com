package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;

/**
 * 時間年休使用時間
 * @author shuichu_ishida
 */
@Getter
public class TimeAnnualLeaveUsedTime implements Cloneable {

	/** 使用回数 */
	private UsedTimes usedTimes;
	/** 使用時間 */
	private UsedMinutes usedTime;
	/** 使用時間付与前 */
	private UsedMinutes usedTimeBeforeGrant;
	/** 使用時間付与後 */
	private Optional<UsedMinutes> usedTimeAfterGrant;
	
	/**
	 * コンストラクタ
	 */
	public TimeAnnualLeaveUsedTime(){
		
		this.usedTimes = new UsedTimes(0);
		this.usedTime = new UsedMinutes(0);
		this.usedTimeBeforeGrant = new UsedMinutes(0);
		this.usedTimeAfterGrant = Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param usedTimes 使用回数
	 * @param usedTime 使用時間
	 * @param usedTimeBeforeGrant 使用時間付与前
	 * @param usedTimeAfterGrant 使用時間付与後
	 * @return 時間年休使用時間
	 */
	public static TimeAnnualLeaveUsedTime of(
			UsedTimes usedTimes,
			UsedMinutes usedTime,
			UsedMinutes usedTimeBeforeGrant,
			Optional<UsedMinutes> usedTimeAfterGrant){
		
		TimeAnnualLeaveUsedTime domain = new TimeAnnualLeaveUsedTime();
		domain.usedTimes = usedTimes;
		domain.usedTime = usedTime;
		domain.usedTimeBeforeGrant = usedTimeBeforeGrant;
		domain.usedTimeAfterGrant = usedTimeAfterGrant;
		return domain;
	}
	
	@Override
	protected TimeAnnualLeaveUsedTime clone() {
		TimeAnnualLeaveUsedTime cloned = new TimeAnnualLeaveUsedTime();
		try {
			cloned.usedTimes = new UsedTimes(this.usedTime.v());
			cloned.usedTime = new UsedMinutes(this.usedTime.v());
			cloned.usedTimeBeforeGrant = new UsedMinutes(this.usedTimeBeforeGrant.v());
			if (this.usedTimeAfterGrant.isPresent()){
				cloned.usedTimeAfterGrant = Optional.of(
						new UsedMinutes(this.usedTimeAfterGrant.get().v()));
			}
		}
		catch (Exception e){
			throw new RuntimeException("TimeAnnualLeaveUsedTime clone error.");
		}
		return cloned;
	}
}
