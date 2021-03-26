package nts.uk.ctx.office.dom.status.adapter;

import java.util.Optional;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.office.dom.status.StatusClassfication;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.ステータス.Imported.在席状態を取得する.在席状態を取得する
 * 在席状態
 */
@Builder
@Data
public class AttendanceStateImport {
	// 在席状態
	private StatusClassfication attendanceState;

	// 勤務が出勤ですか
	private Optional<Boolean> workingNow;
}
