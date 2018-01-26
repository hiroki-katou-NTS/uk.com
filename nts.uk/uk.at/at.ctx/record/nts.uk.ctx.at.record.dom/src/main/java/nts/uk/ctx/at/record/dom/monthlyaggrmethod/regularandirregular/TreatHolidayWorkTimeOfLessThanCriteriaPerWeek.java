package nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;

/**
 * 1週間の基準時間未満の休日出勤時間の扱い
 * @author shuichu_ishida
 */
@Getter
public class TreatHolidayWorkTimeOfLessThanCriteriaPerWeek {

	/** 自動的に除く休出枠 */
	private List<HolidayWorkFrameNo> autoExcludeHolidayWorkFrames;
	
	/**
	 * コンストラクタ
	 */
	public TreatHolidayWorkTimeOfLessThanCriteriaPerWeek(){
		
		this.autoExcludeHolidayWorkFrames = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param autoExcludeHolidayWorkFrames 自動的に除く休出枠
	 * @return 1週間の基準時間未満の休日出勤時間の扱い
	 */
	public static TreatHolidayWorkTimeOfLessThanCriteriaPerWeek of(
			List<HolidayWorkFrameNo> autoExcludeHolidayWorkFrames){
		
		val domain = new TreatHolidayWorkTimeOfLessThanCriteriaPerWeek();
		domain.autoExcludeHolidayWorkFrames = autoExcludeHolidayWorkFrames;
		return domain;
	}
	
	/**
	 * ファクトリー
	 * @param treatHolidayWorkTimes 休出時間の扱い01～10　（エンティティ）
	 * @return 1週間の基準時間未満の休日出勤時間の扱い
	 */
	public static TreatHolidayWorkTimeOfLessThanCriteriaPerWeek of(
			Map<Integer, Integer> treatHolidayWorkTimes){
		
		val domain = new TreatHolidayWorkTimeOfLessThanCriteriaPerWeek();
		domain.autoExcludeHolidayWorkFrames = new ArrayList<>();
		for (Integer i = 1; i <= 10; i++){
			if (!treatHolidayWorkTimes.containsKey(i)) continue;
			if (treatHolidayWorkTimes.get(i) == 1) {
				// 自動的に除く休出枠の時
				domain.autoExcludeHolidayWorkFrames.add(new HolidayWorkFrameNo(i));
			}
		}
		return domain;
	}
}
