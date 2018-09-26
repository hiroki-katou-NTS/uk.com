package nts.uk.ctx.at.shared.pub.yearholidaygrant;

/**
 * 年間労働日数の計算基準
	0:年休付与日
	1:就業締め日
 * @author TanLV
 *
 */
public enum StandardCalculation {
	/** 年休付与日 */
	YEAR_HD_AWARD_DATE(0),
	/** 就業締め日 */
	WORK_CLOSURE_DATE(1);
	
	public final int value;
	
	StandardCalculation(int value) {
		this.value = value;
	}
}
