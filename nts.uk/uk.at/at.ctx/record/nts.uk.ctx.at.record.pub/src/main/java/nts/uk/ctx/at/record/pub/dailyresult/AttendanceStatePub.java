package nts.uk.ctx.at.record.pub.dailyresult;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.日別実績.Export.在席状態を取得する.在席状態を取得する
 * 在席状態を取得する
 */
public interface AttendanceStatePub {
	/**
	 * [1] 取得する
	 * 
	 * @param sid 社員ID
	 * @return 実績データに従う在席状態
	 */
	public AttendanceStateExport getAttendanceState(String sid);
}
