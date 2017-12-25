package nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset;

/**
 * 流動固定休憩の計算方法
 * @author keisuke_hoshina
 *
 */

public enum FluidPrefixBreakTimeOfCalcMethod {
	ReferToMaster,
	ReferToSchedule,
	StampWithoutReference;
	
	/**
	 * 参照せずに打刻するであるか判定する
	 * @return　参照せずに打刻するである
	 */
	public boolean isStampWithoutReference() {
		return this.equals(StampWithoutReference);
	}
}
