package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.export;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.Month;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.DeforWorkTimeAggrSet;

/**
 * 変形労働精算期間の取得
 * @author shuichu_ishida
 */
@Getter
public class GetSettlementPeriodOfDefor {

	/** 精算期間 */
	private List<SettlementPeriod> settlementPeriods;
	
	/**
	 * コンストラクタ
	 */
	public GetSettlementPeriodOfDefor(){
		
		this.settlementPeriods = new ArrayList<>();
	}

	/**
	 * ファクトリー
	 * @param settlementPeriods 精算期間
	 * @return 変形労働精算期間
	 */
	public static GetSettlementPeriodOfDefor of(
			List<SettlementPeriod> settlementPeriods){
		
		val domain = new GetSettlementPeriodOfDefor();
		domain.settlementPeriods = settlementPeriods;
		return domain;
	}
	
	/**
	 * ファクトリー
	 * @param deforAggrSet 変形労働時間勤務の法定内集計設定
	 * @return 変形労働精算期間の取得
	 */
	public static GetSettlementPeriodOfDefor createFromDeforAggrSet(DeforWorkTimeAggrSet deforAggrSet){
		
		GetSettlementPeriodOfDefor domain = new GetSettlementPeriodOfDefor();
		
		// 精算期間を取得
		val srcSetlPeriod = deforAggrSet.getSettlementPeriod();
		
		// 繰り返し区分の判断
		if (srcSetlPeriod.isRepeat()){
			// 繰り返しあり
			int srcStartMonth = srcSetlPeriod.getStartMonth().v();
			int calcStartMonth = srcStartMonth;
			int period = srcSetlPeriod.getPeriod().v();
			int calcEndMonth = srcStartMonth + period - 1;
			for (int calcNowMonth = srcStartMonth; calcNowMonth < srcStartMonth + 12; calcNowMonth++){
				if (calcNowMonth == calcStartMonth){
					
					// 複数月の期間を作成
					int startMonth = calcStartMonth;
					if (startMonth > 12) startMonth -= 12;
					int endMonth = calcEndMonth;
					if (endMonth > 12) endMonth -= 12;
					domain.settlementPeriods.add(
							new SettlementPeriod(new Month(startMonth), new Month(endMonth)));
					
					calcNowMonth = calcEndMonth;
					// 繰り返した時の次の終了月が、開始月から12か月内に収まる場合は、複数月を繰り返す
					if (calcEndMonth + 1 + period - 1 < srcStartMonth + 12){
						calcStartMonth = calcEndMonth + 1;
						calcEndMonth = calcStartMonth + period - 1;
					}
					continue;
				}
				
				// 単月の期間を作成
				int singleMonth = calcNowMonth;
				if (singleMonth > 12) singleMonth -= 12;
				domain.settlementPeriods.add(
						new SettlementPeriod(new Month(singleMonth), new Month(singleMonth)));
			}
		}
		else {
			// 繰り返しなし
			int startMonth = srcSetlPeriod.getStartMonth().v();
			int period = srcSetlPeriod.getPeriod().v();
			int calcEndMonth = startMonth + period - 1;
			for (int calcNowMonth = startMonth; calcNowMonth < startMonth + 12; calcNowMonth++){
				if (calcNowMonth == startMonth){
					
					// 複数月の期間を作成
					int endMonth = calcEndMonth;
					if (endMonth > 12) endMonth -= 12;
					domain.settlementPeriods.add(
							new SettlementPeriod(new Month(startMonth), new Month(endMonth)));
					
					calcNowMonth = calcEndMonth;
					continue;
				}
				
				// 単月の期間を作成
				int singleMonth = calcNowMonth;
				if (singleMonth > 12) singleMonth -= 12;
				domain.settlementPeriods.add(
						new SettlementPeriod(new Month(singleMonth), new Month(singleMonth)));
			}
		}
		
		// 変形労働精算期間を返す
		return domain;
	}
	
	/**
	 * 判定年月が含まれる精算期間の過去年月リストを取得する
	 * @param yearMonth 判定年月
	 * @return 該当精算期間の過去年月リスト　（当月は含まない）
	 */
	public List<YearMonth> getPastSettlementYearMonths(YearMonth yearMonth){
		
		List<YearMonth> pastYearMonths = new ArrayList<>();
		boolean isExist = false;
		for (val settlementPeriod : this.settlementPeriods){
			pastYearMonths = new ArrayList<>();
			isExist = false;
			int startMonth = settlementPeriod.getStartMonth().v();
			int endMonth = settlementPeriod.getEndMonth().v();
			int nowYear = yearMonth.year();
			if (startMonth > yearMonth.month()) nowYear--;
			YearMonth nowYearMonth = YearMonth.of(nowYear, startMonth);
			for (int i = 1; i <= 12; i++){
				if (nowYearMonth.equals(yearMonth)){
					isExist = true;
					break;
				}
				pastYearMonths.add(YearMonth.of(nowYearMonth.year(), nowYearMonth.month()));
				if (nowYearMonth.month() == endMonth) break;
				nowYearMonth = nowYearMonth.addMonths(1);
			}
			if (isExist) break;
		}
		if (!isExist) pastYearMonths = new ArrayList<>();
		return pastYearMonths;
	}
	
	/**
	 * 判定年月と同じ精算終了月があるか判定する
	 * @param yearMonth 判定年月
	 * @return true：同じ終了月がある、false：同じ終了月がない
	 */
	public boolean isSameSettlementEndMonth(YearMonth yearMonth){
		
		for (val settlementPeriod : this.settlementPeriods){
			if (settlementPeriod.getEndMonth().v().intValue() == yearMonth.month()) return true;
		}
		return false;
	}
	
	/**
	 * 精算月か確認する
	 * @param yearMonth 判定年月
	 * @param isRetireMonth 退職月かどうか
	 * @return true：精算月、false：精算月でない
	 */
	public boolean isSettlementMonth(YearMonth yearMonth, boolean isRetireMonth){
		if (this.isSameSettlementEndMonth(yearMonth)) return true;
		if (isRetireMonth) return true;
		return false;
	}
	
	/**
	 * 判定年月が単月か判定する
	 * @param yearMonth 判定年月
	 * @return true：単月、false：複数月
	 */
	public boolean isSingleMonth(YearMonth yearMonth){
		
		for (val settlementPeriod : this.settlementPeriods){
			int startMonth = settlementPeriod.getStartMonth().v();
			int endMonth = settlementPeriod.getEndMonth().v();
			int nowYear = yearMonth.year();
			if (startMonth > yearMonth.month()) nowYear--;
			YearMonth nowYearMonth = YearMonth.of(nowYear, startMonth);
			for (int i = 1; i <= 12; i++){
				if (nowYearMonth.equals(yearMonth)){
					// 判定年月に該当する期間の開始月と終了月が同じなら単月
					return (startMonth == endMonth);
				}
				if (nowYearMonth.month() == endMonth) break;
				nowYearMonth = nowYearMonth.addMonths(1);
			}
		}
		// 該当期間が見つからなければ、単月扱い
		return true;
	}
}
