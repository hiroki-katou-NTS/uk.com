package nts.uk.ctx.at.aggregation.dom.form9;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
/**
 * 様式９の出力医療時間
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.社員の出力医療時間を取得する.様式９の出力医療時間
 * @author lan_lt
 *
 */
@Value
public class Form9OutputMedicalTime {
	
	/** 時間 **/
	private final AttendanceTime time;
	
	/** 申し送り時間控除日か **/
	private final boolean isDeductionDateFromDeliveryTime;

}
