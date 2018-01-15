package nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.arc.time.YearMonth;

/**
 * 変形労働時間勤務の法定内集計設定
 * @author shuichu_ishida
 */
@Getter
public class LegalAggrSetOfIrg {

	/** 集計時間設定 */
	private AggregateTimeSet aggregateTimeSet;
	/** 時間外超過設定 */
	private ExcessOutsideTimeSet excessOutsideTimeSet;
	/** 変形労働計算の設定 */
	private CalcSettingOfIrregular calcSetOfIrregular;
	/** 精算期間 */
	private SettlementPeriodOfIrg settlementPeriod;
	
	/**
	 * コンストラクタ
	 */
	public LegalAggrSetOfIrg(){
		
		this.aggregateTimeSet = new AggregateTimeSet();
		this.excessOutsideTimeSet = new ExcessOutsideTimeSet();
		this.calcSetOfIrregular = new CalcSettingOfIrregular();
		this.settlementPeriod = new SettlementPeriodOfIrg();
	}

	/**
	 * ファクトリー
	 * @param aggreagteTimeSet 集計時間設定
	 * @param excessOutsideTimeSet 時間外超過設定
	 * @param calcSetOfIrregular 変形労働計算の設定
	 * @param settlementPeriod 精算期間
	 * @return 変形労働時間勤務の法定内集計設定
	 */
	public static LegalAggrSetOfIrg of(
			AggregateTimeSet aggreagteTimeSet,
			ExcessOutsideTimeSet excessOutsideTimeSet,
			CalcSettingOfIrregular calcSetOfIrregular,
			SettlementPeriodOfIrg settlementPeriod){
		
		val domain = new LegalAggrSetOfIrg();
		domain.aggregateTimeSet = aggreagteTimeSet;
		domain.excessOutsideTimeSet = excessOutsideTimeSet;
		domain.calcSetOfIrregular = calcSetOfIrregular;
		domain.settlementPeriod = settlementPeriod;
		return domain;
	}
	
	/**
	 * 判定年月が含まれる精算期間の過去年月リストを取得する
	 * @param yearMonth 判定年月
	 * @return 該当精算期間の過去年月リスト
	 */
	public List<YearMonth> getPastSettlementYearMonths(YearMonth yearMonth){
		
		List<YearMonth> pastYearMonths = new ArrayList<>();
		val settlementPeriods = this.settlementPeriod.getSettlementPeriods();
		boolean isExist = false;
		for (val settlementPeriod : settlementPeriods){
			pastYearMonths = new ArrayList<>();
			isExist = false;
			int startMonth = settlementPeriod.getStartMonth().v();
			int nowYear = yearMonth.year();
			if (startMonth > yearMonth.month()) nowYear--;
			YearMonth nowYearMonth = YearMonth.of(nowYear, startMonth);
			for (int i = 1; i <= 12; i++){
				if (nowYearMonth.equals(yearMonth)){
					isExist = true;
					break;
				}
				pastYearMonths.add(YearMonth.of(nowYearMonth.year(), nowYearMonth.month()));
				if (nowYearMonth.month() == settlementPeriod.getEndMonth().v()) break;
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
		
		val settlementPeriods = this.settlementPeriod.getSettlementPeriods();
		for (val settlementPeriod : settlementPeriods){
			if (settlementPeriod.getEndMonth().v().intValue() == yearMonth.month()) return true;
		}
		return false;
	}
}
