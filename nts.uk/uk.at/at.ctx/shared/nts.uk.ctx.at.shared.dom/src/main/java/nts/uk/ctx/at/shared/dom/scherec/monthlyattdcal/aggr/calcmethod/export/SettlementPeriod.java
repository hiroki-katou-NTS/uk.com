package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.export;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.Month;

/**
 * 精算期間
 * @author shuichu_ishida
 */
@Getter
public class SettlementPeriod {

	/** 開始月 */
	private Month startMonth;
	/** 終了月 */
	private Month endMonth;
	
	/**
	 * コンストラクタ
	 * @param startMonth 開始月
	 * @param endMonth 終了月
	 */
	public SettlementPeriod(Month startMonth, Month endMonth){
		this.startMonth = startMonth;
		this.endMonth = endMonth;
	}
}
