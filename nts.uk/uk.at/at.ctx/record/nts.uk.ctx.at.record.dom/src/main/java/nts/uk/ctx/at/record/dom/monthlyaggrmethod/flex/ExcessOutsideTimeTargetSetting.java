package nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex;

/**
 * 時間外超過対象設定
 * @author shuichu_ishida
 */
public enum ExcessOutsideTimeTargetSetting {
	/** 法定外フレックスのみ */
	ONLY_ILLEGAL_FLEX(0),
	/** 法定内フレックスを含む */
	INCLUDE_LEGAL_FLEX(1);
	
	public int value;
	private ExcessOutsideTimeTargetSetting(int value){
		this.value = value;
	}
}
