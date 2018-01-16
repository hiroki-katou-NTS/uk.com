package nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

/**
 * 1日の基準時間未満の残業時間の扱い
 * @author shuichu_ishida
 */
@Getter
public class TreatOverTimeOfLessThanCriteriaPerDay {

	/** 自動的に除く残業枠 */
	private List<OverTimeFrameNo> autoExcludeOverTimeFrames;
	/** 法定内の残業枠 */
	private List<OverTimeFrameNo> legalOverTimeFrames;
	
	/**
	 * コンストラクタ
	 */
	public TreatOverTimeOfLessThanCriteriaPerDay(){
		
		this.autoExcludeOverTimeFrames = new ArrayList<>();
		this.legalOverTimeFrames = new ArrayList<>();
	}

	/**
	 * ファクトリー
	 * @param autoExcludeOverTimeFrames 自動的に除く残業枠
	 * @param legalOverTimeFrames 法定内の残業枠
	 * @return 1日の基準時間未満の残業時間の扱い
	 */
	public static TreatOverTimeOfLessThanCriteriaPerDay of(
			List<OverTimeFrameNo> autoExcludeOverTimeFrames,
			List<OverTimeFrameNo> legalOverTimeFrames){
		
		val domain = new TreatOverTimeOfLessThanCriteriaPerDay();
		domain.autoExcludeOverTimeFrames = autoExcludeOverTimeFrames;
		domain.legalOverTimeFrames = legalOverTimeFrames;
		return domain;
	}
	
	/**
	 * ファクトリー
	 * @param treatOverTimes 残業時間の扱い01～10　（エンティティ）
	 * @return 1日の基準時間未満の残業時間の扱い
	 */
	public static TreatOverTimeOfLessThanCriteriaPerDay of(
			Map<Integer, Integer> treatOverTimes){
		
		val domain = new TreatOverTimeOfLessThanCriteriaPerDay();
		domain.autoExcludeOverTimeFrames = new ArrayList<>();
		domain.legalOverTimeFrames = new ArrayList<>();
		for (Integer i = 1; i <= 10; i++){
			if (!treatOverTimes.containsKey(i)) continue;
			if (treatOverTimes.get(i) == 1) {
				// 自動的に除く残業枠の時
				domain.autoExcludeOverTimeFrames.add(new OverTimeFrameNo(i));
			}
			if (treatOverTimes.get(i) == 2) {
				// 法定内の残業枠の時
				domain.legalOverTimeFrames.add(new OverTimeFrameNo(i));
			}
		}
		return domain;
	}
}
