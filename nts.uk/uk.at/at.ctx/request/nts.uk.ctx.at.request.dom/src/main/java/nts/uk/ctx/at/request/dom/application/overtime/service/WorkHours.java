package nts.uk.ctx.at.request.dom.application.overtime.service;
/**
 * Refactor5
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業休出申請共通設定.アルゴリズム.初期表示する出退勤時刻を取得する
 * @author hoangnd
 *
 */

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
@Data
// 勤務時間
public class WorkHours {
	// 開始時刻1
	private Optional<AttendanceTime> startTimeOp1;
	// 開始時刻2
	private Optional<AttendanceTime> startTimeOp2;
	// 終了時刻1
	private Optional<AttendanceTime> endTimeOp1;
	// 終了時刻2
	private Optional<AttendanceTime> endTimeOp2;
}
