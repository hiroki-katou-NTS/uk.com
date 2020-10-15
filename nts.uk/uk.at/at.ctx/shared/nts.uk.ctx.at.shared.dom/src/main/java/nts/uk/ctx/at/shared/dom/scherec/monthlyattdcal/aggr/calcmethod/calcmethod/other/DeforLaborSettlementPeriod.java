package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other;

import java.io.Serializable;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.Month;

/**
 * 変形労働の精算期間
 */
@Getter
public class DeforLaborSettlementPeriod implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 開始月 */
	private Month startMonth;

	/** 期間 */
	private Month period;

	/** 繰り返し区分 */
	private boolean repeat;

	/**
	 * Instantiates a new defor labor settlement period.
	 *
	 * @param startMonth 開始月
	 * @param period 期間
	 * @param repeatAtr 繰り返し区分
	 */
	public DeforLaborSettlementPeriod(Month startMonth, Month period, boolean repeat) {
		super();
		this.startMonth = startMonth;
		this.period = period;
		this.repeat = repeat;
	}

}
