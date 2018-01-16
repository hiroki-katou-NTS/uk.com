package nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.val;

/**
 * 変形労働の精算期間
 * @author shuichu_ishida
 */
@Getter
public class SettlementPeriodOfIrg {

	/** 精算期間 */
	private List<SettlementPeriod> settlementPeriods;
	
	/**
	 * コンストラクタ
	 */
	public SettlementPeriodOfIrg(){
		
		this.settlementPeriods = new ArrayList<>();
	}

	/**
	 * ファクトリー
	 * @param settlementPeriods 精算期間
	 * @return 変形労働の精算期間
	 */
	public static SettlementPeriodOfIrg of(
			List<SettlementPeriod> settlementPeriods){
		
		val domain = new SettlementPeriodOfIrg();
		domain.settlementPeriods = settlementPeriods;
		return domain;
	}
}
