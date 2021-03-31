package nts.uk.ctx.at.shared.dom.worktype;

import java.util.HashMap;
import java.util.Map;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;

/**
 * @author dan_pv
 * 半日ごとの勤務種類の分類
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.勤務種類.半日ごとの勤務種類の分類
 *
 */
@Value
public class HalfDayWorkTypeClassification implements DomainValue{
	
	/**
	 * 午前の分類
	 */
	private final WorkTypeClassification morningClass;
	
	/**
	 * 午後の分類
	 */
	private final WorkTypeClassification afternoonClass;
	
	/**
	 * 全日指定で作成する
	 * @param wholeDay 全日の分類
	 * @return
	 */
	public static HalfDayWorkTypeClassification createWholeDay(WorkTypeClassification wholeDay) {
		return new HalfDayWorkTypeClassification(wholeDay, wholeDay);
	}
	
	/**
	 * 午前と午後を指定して作成する
	 * @param morning 午前の分類
	 * @param afternoon 午後の分類
	 * @return
	 */
	public static HalfDayWorkTypeClassification createWithAmAndPm(WorkTypeClassification morning, WorkTypeClassification afternoon) {
		return new HalfDayWorkTypeClassification(morning, afternoon);
	}
	
	/**
	 * 全日同じ分類か
	 * @return
	 */
	public boolean isSameWorkTypeClassificationWholeDay() {
		return this.morningClass == this.afternoonClass;
	}
	
	/**
	 * Mapとして取得する	
	 * @return
	 */
	public Map<AmPmAtr, WorkTypeClassification> getAsMap() {
		Map<AmPmAtr, WorkTypeClassification> result = new HashMap<>();
		result.put(AmPmAtr.AM, this.morningClass);
		result.put(AmPmAtr.PM, this.afternoonClass);
		
		return result;
	}

}
