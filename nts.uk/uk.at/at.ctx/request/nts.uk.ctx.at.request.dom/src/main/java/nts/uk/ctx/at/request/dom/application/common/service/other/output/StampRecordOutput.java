package nts.uk.ctx.at.request.dom.application.common.service.other.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.実績内容の取得.打刻実績
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
@Setter
public class StampRecordOutput {
	
	/**
	 * 介護時間帯
	 */
	private List<TimePlaceOutput> nursingTime;
	
	/**
	 * 休憩時間帯
	 */
	private List<TimePlaceOutput> breakTime;
	
	/**
	 * 勤務時間帯
	 */
	private List<TimePlaceOutput> workingTime;
	
	/**
	 * 外出時間帯
	 */
	private List<TimePlaceOutput> outingTime;
	
	/**
	 * 応援時間帯
	 */
	private List<TimePlaceOutput> supportTime;
	
	/**
	 * 育児時間帯
	 */
	private List<TimePlaceOutput> parentingTime;
	
	/**
	 * 臨時時間帯
	 */
	private List<TimePlaceOutput> extraordinaryTime;
	
}
