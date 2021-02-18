package nts.uk.ctx.at.record.pub.dailyresult;

import java.util.Optional;

import lombok.Builder;
import lombok.Data;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.日別実績.Export.在席状態を取得する.在席状態を取得する
 * 実績データに従う在席状態
 */
@Builder
@Data
public class AttendanceStateExport {
	// 在席状態
	private StatusClassfication attendanceState;

	// 勤務が出勤ですか
	private Optional<Boolean> workingNow;
}
