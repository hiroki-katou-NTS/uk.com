package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod;

import lombok.AllArgsConstructor;

/** 変形労働の途中入社退職時の月間割増計算方法 */
@AllArgsConstructor
public enum MonPremiumTimeCalcMethodInEntryOfDefo {

	/** 所定労働時間から割増時間を計算する */
	CALC_BY_STATUTORY_TIME(0),
	/** 在職期間中の法定労働時間の総枠を超過している時間を計算する。 */
	CALC_BY_RECORD_TOTAL_TIME(1);
	
	public int value;
}
