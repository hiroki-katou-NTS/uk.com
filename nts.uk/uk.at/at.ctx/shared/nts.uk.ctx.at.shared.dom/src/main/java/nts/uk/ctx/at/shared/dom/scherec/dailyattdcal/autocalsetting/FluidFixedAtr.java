package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;

/**
 * 流動固定区分
 * @author keisuke_hoshina
 */
public enum FluidFixedAtr {
	FluidWork,//流動
	FixedWork;//固定
	
	/**
	 * 流動であるか判定する
	 * @return 流動である
	 */
	public boolean isFluidWork() {
		return FluidWork.equals(this);
	}
	
	/**
	 * 作る
	 * @param restTimezone 流動勤務の休憩時間帯
	 * @return 流動固定区分
	 */
	public static FluidFixedAtr of(Optional<FlowWorkRestTimezone> restTimezone) {
		if(!restTimezone.isPresent()) {
			return FixedWork;
		}
		if(restTimezone.get().isFixRestTime()) {
			return FixedWork;
		}
		return FluidWork;
	}
}
