package nts.uk.ctx.at.shared.dom.monthlyaggrmethod.regularandirregular;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.arc.time.YearMonth;

/**
 * 変形労働精算期間
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
	 * @return 変形労働精算期間
	 */
	public static SettlementPeriodOfIrg of(
			List<SettlementPeriod> settlementPeriods){
		
		val domain = new SettlementPeriodOfIrg();
		domain.settlementPeriods = settlementPeriods;
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
