package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * Refactor5
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業休出申請共通設定.アルゴリズム.初期表示する出退勤時刻を取得する
 * @author hoangnd
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
// 申請内容
public class OverTimeContent {
	// SPR連携時刻
	private Optional<WorkHours> SPRTime = Optional.empty();
	// 勤務種類コード
	private Optional<WorkTypeCode> workTypeCode = Optional.empty();
	// 実績時刻
	private Optional<WorkHours> actualTime = Optional.empty();
	// 就業時間帯コード
	private Optional<WorkTimeCode> workTimeCode = Optional.empty();
}
