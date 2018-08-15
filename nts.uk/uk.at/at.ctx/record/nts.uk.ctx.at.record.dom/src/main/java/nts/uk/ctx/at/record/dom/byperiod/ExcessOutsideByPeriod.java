package nts.uk.ctx.at.record.dom.byperiod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.roundingset.RoundingSetOfMonthly;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.weekly.WeeklyCalculation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.outsideot.breakdown.OutsideOTBRDItem;

/**
 * 期間別の時間外超過
 * @author shuichu_ishida
 */
@Getter
public class ExcessOutsideByPeriod implements Cloneable {

	/** 時間外超過 */
	private Map<Integer, ExcessOutsideItemByPeriod> excessOutsideItems;
	
	/**
	 * コンストラクタ
	 */
	public ExcessOutsideByPeriod(){
		
		this.excessOutsideItems = new HashMap<>();
	}
	
	/**
	 * ファクトリー
	 * @param excessOutsideItems 時間外超過
	 * @return 期間別の時間外超過
	 */
	public static ExcessOutsideByPeriod of(
			List<ExcessOutsideItemByPeriod> excessOutsideItems){
		
		ExcessOutsideByPeriod domain = new ExcessOutsideByPeriod();
		for (val excessOutsideItem : excessOutsideItems){
			val breakdownNo = excessOutsideItem.getBreakdownNo();
			domain.excessOutsideItems.putIfAbsent(breakdownNo, excessOutsideItem);
		}
		return domain;
	}
	
	@Override
	public ExcessOutsideByPeriod clone() {
		ExcessOutsideByPeriod cloned = new ExcessOutsideByPeriod();
		try {
			for (val item : this.excessOutsideItems.entrySet()){
				cloned.excessOutsideItems.putIfAbsent(item.getKey(), item.getValue().clone());
			}
		}
		catch (Exception e){
			throw new RuntimeException("ExcessOutsideByPeriod clone error.");
		}
		return cloned;
	}
	
	/**
	 * 集計処理
	 * @param weeklyCalculation 週別の計算
	 * @param companySets 月別集計で必要な会社別設定
	 */
	public void aggregate(
			WeeklyCalculation weeklyCalculation,
			MonAggrCompanySettings companySets){
		
		// 丸め設定取得
		RoundingSetOfMonthly roundingSet = companySets.getRoundingSet();
		
		// 「時間外超過の内訳項目」を取得する
		List<OutsideOTBRDItem> outsideOTBDItems = companySets.getOutsideOTBDItems();
		for (val outsideOTBDItem : outsideOTBDItems){
			int breakdownNo = outsideOTBDItem.getBreakdownItemNo().value;
			
			// 勤怠項目IDの一覧を取得
			AttendanceTimeMonth breakdownItemTime = new AttendanceTimeMonth(0);
			for (val attendanceItemId : outsideOTBDItem.getAttendanceItemIds()){
				
				// 時間を合計する
				breakdownItemTime = breakdownItemTime.addMinutes(
						weeklyCalculation.getTimeOfAttendanceItemId(attendanceItemId, roundingSet, true).v());
			}
			
			// 時間外超過に追加する
			this.excessOutsideItems.put(breakdownNo,
					ExcessOutsideItemByPeriod.of(breakdownNo, breakdownItemTime));
		}
	}
	
	/**
	 * 集計処理
	 * @param monthlyCalculation 期間別の月の計算
	 * @param companySets 月別集計で必要な会社別設定
	 */
	public void aggregate(
			MonthlyCalculationByPeriod monthlyCalculation,
			MonAggrCompanySettings companySets){
		
		// 丸め設定取得
		RoundingSetOfMonthly roundingSet = companySets.getRoundingSet();
		
		// 「時間外超過の内訳項目」を取得する
		List<OutsideOTBRDItem> outsideOTBDItems = companySets.getOutsideOTBDItems();
		for (val outsideOTBDItem : outsideOTBDItems){
			int breakdownNo = outsideOTBDItem.getBreakdownItemNo().value;
			
			// 勤怠項目IDの一覧を取得
			AttendanceTimeMonth breakdownItemTime = new AttendanceTimeMonth(0);
			for (val attendanceItemId : outsideOTBDItem.getAttendanceItemIds()){
				
				// 時間を合計する
				breakdownItemTime = breakdownItemTime.addMinutes(
						monthlyCalculation.getTimeOfAttendanceItemId(attendanceItemId, roundingSet, true).v());
			}
			
			// 時間外超過に追加する
			this.excessOutsideItems.put(breakdownNo,
					ExcessOutsideItemByPeriod.of(breakdownNo, breakdownItemTime));
		}
	}
}
