package nts.uk.ctx.at.request.dom.application.overtime.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;
import nts.uk.ctx.at.request.dom.workrecord.dailyrecordprocess.dailycreationwork.BreakTimeZoneSetting;

/**
 * Refactor5
 * UKDesign.UniversalK.就業.KAF_申請.KAF005_残業申請.A：残業申請（新規登録）.アルゴリズム.16_勤務種類・就業時間帯を選択する
 * @author hoangnd
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectWorkOutput {
	// 勤務時間
	private WorkHours workHours;
	// 休憩時間帯設定
	private BreakTimeZoneSetting breakTimeZoneSetting;
	// 申請時間
	private ApplicationTime applicationTime;
}
