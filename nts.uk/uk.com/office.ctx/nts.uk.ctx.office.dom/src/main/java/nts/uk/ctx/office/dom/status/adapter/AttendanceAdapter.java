package nts.uk.ctx.office.dom.status.adapter;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.ステータス.Imported.在席状態を取得する.在席状態を取得する
 * 在席状態を取得する
 */
public interface AttendanceAdapter {
	/**
	 * [1]取得する
	 * 
	 * @param sid 社員ID
	 * @return 在席状態
	 */
	public AttendanceStateImport getAttendace(String sid);
}
