package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingTimes;

/**
 * 半日年休残数
 * @author shuichu_ishida
 */
@Getter
public class HalfDayAnnLeaRemainingNum implements Cloneable {

	/** 回数 */
	private RemainingTimes times;
	/** 回数付与前 */
	private RemainingTimes timesBeforeGrant;
	/** 回数付与後 */
	private Optional<RemainingTimes> timesAfterGrant;
	
	/**
	 * コンストラクタ
	 */
	public HalfDayAnnLeaRemainingNum(){
		
		this.times = new RemainingTimes(0);
		this.timesBeforeGrant = new RemainingTimes(0);
		this.timesAfterGrant = Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param times 回数
	 * @param timesBeforeGrant 回数付与前
	 * @param timesAfterGrant 回数付与後
	 * @return 半日年休残数
	 */
	public static HalfDayAnnLeaRemainingNum of(
			RemainingTimes times, RemainingTimes timesBeforeGrant, Optional<RemainingTimes> timesAfterGrant){
		
		HalfDayAnnLeaRemainingNum domain = new HalfDayAnnLeaRemainingNum();
		domain.times = times;
		domain.timesBeforeGrant = timesBeforeGrant;
		domain.timesAfterGrant = timesAfterGrant;
		return domain;
	}
	
	@Override
	public HalfDayAnnLeaRemainingNum clone() {
		HalfDayAnnLeaRemainingNum cloned = new HalfDayAnnLeaRemainingNum();
		try {
			cloned.times = new RemainingTimes(this.times.v());
			cloned.timesBeforeGrant = new RemainingTimes(this.timesBeforeGrant.v());
			if (this.timesAfterGrant.isPresent()){
				cloned.timesAfterGrant = Optional.of(new RemainingTimes(this.timesAfterGrant.get().v()));
			}
		}
		catch (Exception e){
			throw new RuntimeException("HalfDayAnnLeaRemainingNum clone error.");
		}
		return cloned;
	}
}
