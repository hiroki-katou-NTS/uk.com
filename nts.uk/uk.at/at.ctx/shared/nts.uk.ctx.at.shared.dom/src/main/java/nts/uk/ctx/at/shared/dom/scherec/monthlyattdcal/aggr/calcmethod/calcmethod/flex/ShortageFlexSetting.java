package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.Month;

/**
 * フレックス不足設定
 * @author shuichi_ishida
 */
@Getter
public class ShortageFlexSetting extends DomainObject implements Serializable {

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 繰越設定 */
	private CarryforwardSetInShortageFlex carryforwardSet;
	/** 清算期間 */
	private SettlePeriod settlePeriod;
	/** 開始月 */
	private Month startMonth;
	/** 期間 */
	private SettlePeriodMonths period;
	
	/**
	 * コンストラクタ
	 */
	public ShortageFlexSetting(){
		
		this.carryforwardSet = CarryforwardSetInShortageFlex.CURRENT_MONTH_INTEGRATION;
		this.settlePeriod = SettlePeriod.SINGLE_MONTH;
		this.startMonth = new Month(Month.JANUARY);
		this.period = SettlePeriodMonths.TWO;
	}
	
	/**
	 * ファクトリー
	 * @param carryforwardSet 繰越設定
	 * @param settlePeriod 清算期間
	 * @param startMonth 開始月
	 * @param period 期間
	 * @return フレックス不足設定
	 */
	public static ShortageFlexSetting of(
			CarryforwardSetInShortageFlex carryforwardSet,
			SettlePeriod settlePeriod,
			Month startMonth,
			SettlePeriodMonths period){
		
		val domain = new ShortageFlexSetting();
		domain.carryforwardSet = carryforwardSet;
		domain.settlePeriod = settlePeriod;
		domain.startMonth = startMonth;
		domain.period = period;
		return domain;
	}
	
	/**
	 * フレックス清算期間の取得
	 * @param yearMonth 年月
	 * @return フレックス清算期間
	 */
	public SettlePeriodOfFlex getSettlePeriod(YearMonth yearMonth){
		
		SettlePeriodOfFlex result = SettlePeriodOfFlex.of(yearMonth, true, yearMonth, true);
		
		// 清算期間を確認する
		if (this.settlePeriod == SettlePeriod.SINGLE_MONTH) return result;
		
		// 「年月」の前年の「開始月」から、「年月」より後になるまでの、開始月のリストを作成する
		List<YearMonth> startYms = new ArrayList<>();		// 開始月リスト
		int periodMonths = this.period.value;
		YearMonth indexYm = YearMonth.of(yearMonth.year() - 1, this.startMonth.v());
		for (int i = 1; i <= 24; i++){
			startYms.add(indexYm);
			if (indexYm.greaterThan(yearMonth)) break;
			indexYm = indexYm.addMonths(periodMonths);
		}
		if (startYms.size() == 0) return result;
		
		// 開始月リストから「年月」以降の最小の年月を探す
		int targetIndex;
		YearMonth targetYm = startYms.get(0);	// 該当年月
		for (targetIndex = 0; targetIndex < startYms.size(); targetIndex++){
			targetYm = startYms.get(targetIndex);
			if (targetYm.greaterThanOrEqualTo(yearMonth)) break;
		}
		
		// 該当年月と「年月」を比較する　→　フレックス清算期間を出力する
		if (targetYm.equals(yearMonth)){
			YearMonth endYm = startYms.get(targetIndex + 1).addMonths(-1);
			result = SettlePeriodOfFlex.of(targetYm, true, endYm, false);
		}
		else{
			YearMonth startYm = startYms.get(targetIndex - 1);
			YearMonth endYm = targetYm.addMonths(-1);
			result = SettlePeriodOfFlex.of(startYm, false, endYm, endYm.equals(yearMonth));
		}
		return result;
	}
}
