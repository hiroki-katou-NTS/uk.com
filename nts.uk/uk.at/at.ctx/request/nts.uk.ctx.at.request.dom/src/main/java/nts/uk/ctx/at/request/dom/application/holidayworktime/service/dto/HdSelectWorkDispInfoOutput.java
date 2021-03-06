package nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkHours;
import nts.uk.ctx.at.request.dom.workrecord.dailyrecordprocess.dailycreationwork.BreakTimeZoneSetting;

/**
 * 勤務種類・就業時間帯選択時の表示情報
 * @author huylq
 *Refactor5
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HdSelectWorkDispInfoOutput {
	
	/**
	 * 勤務時間
	 */
	private WorkHours workHours;
	
	/**
	 * 実績の申請時間
	 */
	private ApplicationTime actualApplicationTime;
	
	/**
	 * 休憩時間帯設定リスト
	 */
	private Optional<BreakTimeZoneSetting> breakTimeZoneSettingList;
}
