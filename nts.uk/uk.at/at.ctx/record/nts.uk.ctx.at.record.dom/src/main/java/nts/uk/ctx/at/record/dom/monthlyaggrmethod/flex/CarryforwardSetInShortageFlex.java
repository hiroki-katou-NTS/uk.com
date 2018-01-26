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
	
	/**
	 * 当月積算か判定する
	 * @return true：当月積算、false：当月積算でない
	 */
	public boolean isCurrentMonthIntegration(){
		return this.equals(CURRENT_MONTH_INTEGRATION);
	}
	
	/**
	 * 翌月繰越か判定する
	 * @return true：翌月繰越、false：翌月繰越でない
	 */
	public boolean isNextMonthCarryforward(){
		return this.equals(NEXT_MONTH_CARRYFORWARD);
	}
	
	public int value;
	private CarryforwardSetInShortageFlex(int value){
		this.value = value;
	}
}
