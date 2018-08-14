package nts.uk.ctx.at.record.dom.byperiod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AggregateAnyItem;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.record.dom.optitem.applicable.EmpCondition;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyAmountMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimeMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimesMonth;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 期間別の任意項目
 * @author shuichu_ishida
 */
@Getter
public class AnyItemByPeriod implements Cloneable {

	/** 任意項目値 */
	private Map<Integer, AggregateAnyItem> anyItemValues;
	
	/**
	 * コンストラクタ
	 */
	public AnyItemByPeriod(){
		
		this.anyItemValues = new HashMap<>();
	}
	
	/**
	 * ファクトリー
	 * @param anyItemValues 任意項目値
	 * @return 期間別の任意項目
	 */
	public static AnyItemByPeriod of(
			List<AggregateAnyItem> anyItemValues){
		
		AnyItemByPeriod domain = new AnyItemByPeriod();
		for (val anyItemValue : anyItemValues){
			val anyItemNo = anyItemValue.getAnyItemNo();
			domain.anyItemValues.putIfAbsent(anyItemNo, anyItemValue);
		}
		return domain;
	}
	
	@Override
	public AnyItemByPeriod clone() {
		AnyItemByPeriod cloned = new AnyItemByPeriod();
		try {
			for (val value : this.anyItemValues.entrySet()){
				cloned.anyItemValues.putIfAbsent(value.getKey(), value.getValue().clone());
			}
		}
		catch (Exception e){
			throw new RuntimeException("AnyItemByPeriod clone error.");
		}
		return cloned;
	}
	
	/**
	 * 集計処理
	 * @param period 期間
	 * @param calcDailys 月の計算中の日別実績データ
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 */
	public void aggregate(
			DatePeriod period,
			MonthlyCalculatingDailys calcDailys,
			MonAggrCompanySettings companySets,
			MonAggrEmployeeSettings employeeSets){
		
		// 任意項目ごとに集計する
		Map<Integer, AggregateAnyItem> anyItemTotals = new HashMap<>();
		for (val anyItemValueOfDaily : calcDailys.getAnyItemValueOfDailyList()){
			if (!period.contains(anyItemValueOfDaily.getYmd())) continue;
			if (anyItemValueOfDaily.getItems() == null) continue;
			val ymd = anyItemValueOfDaily.getYmd();
			for (val item : anyItemValueOfDaily.getItems()){
				if (item.getItemNo() == null) continue;
				Integer itemNo = item.getItemNo().v();
				
				if (period.contains(ymd)){
					anyItemTotals.putIfAbsent(itemNo, new AggregateAnyItem(itemNo));
					anyItemTotals.get(itemNo).addFromDaily(item);
				}
			}
		}
		
		// 任意項目を取得
		for (val optionalItem : companySets.getOptionalItemMap().values()){
			Integer optionalItemNo = optionalItem.getOptionalItemNo().v();

			// 利用条件の判定
			Optional<EmpCondition> empCondition = Optional.empty();
			if (companySets.getEmpConditionMap().containsKey(optionalItemNo)){
				empCondition = Optional.of(companySets.getEmpConditionMap().get(optionalItemNo));
			}
			val bsEmploymentHistOpt = employeeSets.getEmployment(period.end());
			if (optionalItem.checkTermsOfUse(empCondition, bsEmploymentHistOpt))
			{
				// 属性に応じて初期化
				AnyTimeMonth anyTime = null;
				AnyTimesMonth anyTimes = null;
				AnyAmountMonth anyAmount = null;
				switch (optionalItem.getOptionalItemAtr()){
				case TIME:
					anyTime = new AnyTimeMonth(0);
					break;
				case NUMBER:
					anyTimes = new AnyTimesMonth(0.0);
					break;
				case AMOUNT:
					anyAmount = new AnyAmountMonth(0);
					break;
				}
				
				// 日別実績　縦計処理
				if (anyItemTotals.containsKey(optionalItemNo)){
					val anyItemTotal = anyItemTotals.get(optionalItemNo);
					if (anyItemTotal.getTime().isPresent()){
						if (anyTime == null) anyTime = new AnyTimeMonth(0);
						anyTime = anyTime.addMinutes(anyItemTotal.getTime().get().v());
					}
					if (anyItemTotal.getTimes().isPresent()){
						if (anyTimes == null) anyTimes = new AnyTimesMonth(0.0);
						anyTimes = anyTimes.addTimes(anyItemTotal.getTimes().get().v().doubleValue());
					}
					if (anyItemTotal.getAmount().isPresent()){
						if (anyAmount == null) anyAmount = new AnyAmountMonth(0);
						anyAmount = anyAmount.addAmount(anyItemTotal.getAmount().get().v());
					}
				}
				
				// 任意項目集計結果を返す
				this.anyItemValues.put(optionalItemNo,
						AggregateAnyItem.of(optionalItemNo, anyTime, anyTimes, anyAmount));
			}
		}
	}
}
