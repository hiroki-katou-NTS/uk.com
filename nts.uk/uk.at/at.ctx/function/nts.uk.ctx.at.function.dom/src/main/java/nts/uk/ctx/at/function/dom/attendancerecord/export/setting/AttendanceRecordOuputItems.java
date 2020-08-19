package nts.uk.ctx.at.function.dom.attendancerecord.export.setting;

import java.util.List;

import nts.arc.layer.dom.AggregateRoot;

/**
 * @author nws-ducnt
 *
 */
//	出勤簿の出力項目自由設定
public class AttendanceRecordOuputItems extends AggregateRoot{

	/**
	 * 会社ID
	 */
	private String companyId;
	
	/**
	 * 出力項目: 出勤簿の出力項目設定 (List)
	 */
	private List<AttendanceRecordExportSetting> attendanceRecordExportSettings;
	
	/**
	 * 社員ID
	 */
	private String employeeId;
	
	/**
	 * 項目選択種類
	 */
	private ItemSelectionType itemSelectionType;
}
