package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.specificdays;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;
import nts.uk.ctx.at.record.dom.raisesalarytime.enums.SpecificDateAttr;
import nts.uk.ctx.at.record.dom.raisesalarytime.primitivevalue.SpecificDateItemNo;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 月別実績の特定日数
 * @author shuichu_ishida
 */
@Getter
public class SpecificDaysOfMonthly {

	/** 特定日数 */
	private Map<SpecificDateItemNo, AggregateSpecificDays> specificDays;
	
	/**
	 * コンストラクタ
	 */
	public SpecificDaysOfMonthly(){
		
		this.specificDays = new HashMap<>();
	}
	
	/**
	 * ファクトリー
	 * @param specificDaysList 特定日数リスト
	 * @return 月別実績の特定日数
	 */
	public static SpecificDaysOfMonthly of(List<AggregateSpecificDays> specificDaysList){
		
		val domain = new SpecificDaysOfMonthly();
		for (val specificDays : specificDaysList){
			val specificDayItemNo = specificDays.getSpecificDayItemNo();
			domain.specificDays.putIfAbsent(specificDayItemNo, specificDays);
		}
		return domain;
	}
	
	/**
	 * 集計
	 * @param workingSystem 労働制
	 * @param workType 勤務種類
	 * @param specificDateAttrOfDaily 日別実績の特定日区分
	 * @param isAttendanceDay 出勤しているかどうか
	 */
	public void aggregate(
			WorkingSystem workingSystem,
			WorkType workType,
			SpecificDateAttrOfDailyPerfor specificDateAttrOfDaily,
			boolean isAttendanceDay){

		if (workType == null) return;
		if (specificDateAttrOfDaily == null) return;
		
		// 勤務種類の判断
		//*****（未）　月別実績の縦計方法の設計が完了したら、親処理で読み込んで、ここまで渡してくる。その設定で、カウントする勤務種類か判断する。
		boolean isCount = false;
		if (!isCount) return;

		// 労働制を取得
		boolean isAdd = false;
		if (workingSystem == WorkingSystem.EXCLUDED_WORKING_CALCULATE){

			// 計算対象外の時、無条件で加算する
			isAdd = true;
		}
		else {
			
			// その他労働制の時、出勤している日なら、加算する
			if (isAttendanceDay){
				isAdd = true;
			}
		}

		// 休出かどうか判断
		boolean isHolidayWork = workType.getDailyWork().isHolidayWork();
		
		if (isAdd) {
			
			// 特定日日数を取得
			for (val specificDateAttrSheet : specificDateAttrOfDaily.getSpecificDateAttrSheets()){
	
				// 特定日とする＝NOT_USE　の時、その枠はカウントしない
				if (specificDateAttrSheet.getSpecificDateAttr() == SpecificDateAttr.NOT_USE) continue;
				
				// 該当枠に1日を加算する
				val specificDateItemNo = specificDateAttrSheet.getSpecificDateItemNo();
				this.specificDays.putIfAbsent(specificDateItemNo, new AggregateSpecificDays(specificDateItemNo));
				val targetSpecificDays = this.specificDays.get(specificDateItemNo);
				if (isHolidayWork){
					targetSpecificDays.addDaysToHolidayWorkSpecificDays(1.0);
				}
				else {
					targetSpecificDays.addDaysToSpecificDays(1.0);
				}
			}
		}
	}

	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(SpecificDaysOfMonthly target){

		for (val specificDay : this.specificDays.values()){
			val itemNo = specificDay.getSpecificDayItemNo();
			if (target.specificDays.containsKey(itemNo)){
				specificDay.addDaysToSpecificDays(target.specificDays.get(itemNo).getSpecificDays().v());
				specificDay.addDaysToHolidayWorkSpecificDays(target.specificDays.get(itemNo).getHolidayWorkSpecificDays().v());
			}
		}
		for (val targetSpecificDay : target.specificDays.values()){
			val itemNo = targetSpecificDay.getSpecificDayItemNo();
			this.specificDays.putIfAbsent(itemNo, targetSpecificDay);
		}
	}
}
