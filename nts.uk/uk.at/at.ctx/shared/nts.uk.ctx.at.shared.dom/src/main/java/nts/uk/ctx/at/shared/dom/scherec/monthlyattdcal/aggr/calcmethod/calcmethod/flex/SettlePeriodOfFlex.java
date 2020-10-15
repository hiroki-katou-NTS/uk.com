package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex;

import lombok.Getter;
import nts.arc.time.YearMonth;

/**
 * フレックス清算期間
 * @author shuichi_ishida
 */
@Getter
public class SettlePeriodOfFlex {

	/** 開始月 */
	private YearMonth startYm;
	/** 開始月かどうか */
	private Boolean isStartYm;
	/** 清算月 */
	private YearMonth settleYm;
	/** 清算月かどうか */
	private Boolean isSettleYm;
	
	/**
	 * コンストラクタ
	 */
	public SettlePeriodOfFlex(){
		
		this.startYm = YearMonth.of(999912);
		this.isStartYm = false;
		this.settleYm = YearMonth.of(999912);
		this.isSettleYm = false;
	}
	
	/**
	 * ファクトリー
	 * @param startYm 開始月
	 * @param isStartYm 開始月かどうか
	 * @param settleYm 清算月
	 * @param isSettleYm 清算月かどうか
	 * @return フレックス清算期間
	 */
	public static SettlePeriodOfFlex of(
			YearMonth startYm,
			Boolean isStartYm,
			YearMonth settleYm,
			Boolean isSettleYm){
		
		SettlePeriodOfFlex domain = new SettlePeriodOfFlex();
		domain.startYm = startYm;
		domain.isStartYm = isStartYm;
		domain.settleYm = settleYm;
		domain.isSettleYm = isSettleYm;
		return domain;
	}
}
