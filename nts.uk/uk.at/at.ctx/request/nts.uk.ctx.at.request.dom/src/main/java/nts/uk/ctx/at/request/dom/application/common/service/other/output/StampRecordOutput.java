package nts.uk.ctx.at.request.dom.application.common.service.other.output;

import lombok.Getter;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.実績内容の取得.打刻実績
 * @author Doan Duy Hung
 *
 */
@Getter
public class StampRecordOutput {
	
	/**
	 * 介護時間帯
	 */
	private TimePlaceOutput nursingTime;
	
	/**
	 * 休憩時間帯
	 */
	private TimePlaceOutput breakTime;
	
	/**
	 * 勤務時間帯
	 */
	private TimePlaceOutput workingTime;
	
	/**
	 * 外出時間帯
	 */
	private TimePlaceOutput outingTime;
	
	/**
	 * 応援時間帯
	 */
	private TimePlaceOutput supportTime;
	
	/**
	 * 育児時間帯
	 */
	private TimePlaceOutput parentingTime;
	
	/**
	 * 臨時時間帯
	 */
	private TimePlaceOutput extraordinaryTime;
	
}
