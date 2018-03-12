package nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex;

/**
 * フレックス不足時の繰越設定
 * @author shuichu_ishida
 */
public enum CarryforwardSetInShortageFlex {
	/** 当月積算 */
	CURRENT_MONTH_INTEGRATION(0),
	/** 翌月繰越 */
	NEXT_MONTH_CARRYFORWARD(1);
	
	public int value;
	private CarryforwardSetInShortageFlex(int value){
		this.value = value;
	}
}
