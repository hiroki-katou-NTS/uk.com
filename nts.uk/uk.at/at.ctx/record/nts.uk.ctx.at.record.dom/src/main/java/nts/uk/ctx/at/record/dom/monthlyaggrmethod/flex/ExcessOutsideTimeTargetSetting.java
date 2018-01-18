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

	/**
	 * 法定外フレックスのみか判定する
	 * @return true：法定外フレックスのみ、false：法定外フレックスのみでない
	 */
	public boolean isOnlyIllegalFlex(){
		return this.equals(ONLY_ILLEGAL_FLEX);
	}

	/**
	 * 法定内フレックスを含むか判定する
	 * @return true：法定内フレックスを含む、false：法定内フレックスを含まない
	 */
	public boolean isIncludeLegalFlex(){
		return this.equals(INCLUDE_LEGAL_FLEX);
	}
	
	public int value;
	private ExcessOutsideTimeTargetSetting(int value){
		this.value = value;
	}
}
