package nts.uk.ctx.at.record.dom.dailyprocess.calc;
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
}
