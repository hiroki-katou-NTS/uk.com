package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;

/**
 * 半日年休使用数
 * @author shuichu_ishida
 */
@Getter
public class HalfDayAnnLeaUsedNum implements Cloneable {

	/** 回数 */
	private UsedTimes times;
	/** 回数付与前 */
	private UsedTimes timesBeforeGrant;
	/** 回数付与後 */
	private Optional<UsedTimes> timesAfterGrant;

	/**
	 * コンストラクタ
	 */
	public HalfDayAnnLeaUsedNum(){
		
		this.times = new UsedTimes(0);
		this.timesBeforeGrant = new UsedTimes(0);
		this.timesAfterGrant = Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param times 回数
	 * @param timeBeforeGrant 回数付与前
	 * @param timesAfterGrant 回数付与後
	 * @return 半日年休使用数
	 */
	public static HalfDayAnnLeaUsedNum of(
			UsedTimes times, UsedTimes timeBeforeGrant, Optional<UsedTimes> timesAfterGrant){
		
		HalfDayAnnLeaUsedNum domain = new HalfDayAnnLeaUsedNum();
		domain.times = times;
		domain.timesBeforeGrant = timeBeforeGrant;
		domain.timesAfterGrant = timesAfterGrant;
		return domain;
	}
	
	@Override
	public HalfDayAnnLeaUsedNum clone() {
		HalfDayAnnLeaUsedNum cloned = new HalfDayAnnLeaUsedNum();
		try {
			cloned.times = new UsedTimes(this.times.v());
			cloned.timesBeforeGrant = new UsedTimes(this.timesBeforeGrant.v());
			if (this.timesAfterGrant.isPresent()){
				cloned.timesAfterGrant = Optional.of(new UsedTimes(this.timesAfterGrant.get().v()));
			}
		}
		catch (Exception e){
			throw new RuntimeException("HalfDayAnnLeaUsedNum clone error.");
		}
		return cloned;
	}
}
