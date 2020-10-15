package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roundingset;

/**
 * 時間外超過の端数処理
 * @author shuichu_ishida
 */
public enum RoundingProcessOfExcessOutsideTime {
	/** 切り捨て */
	ROUNDING_DOWN(0),
	/** 切り上げ */
	ROUNDING_UP(1),
	/** 要素の丸めに従う */
	FOLLOW_ELEMENTS(2);
	
	public int value;
	private RoundingProcessOfExcessOutsideTime(int value){
		this.value = value;
	}
}
