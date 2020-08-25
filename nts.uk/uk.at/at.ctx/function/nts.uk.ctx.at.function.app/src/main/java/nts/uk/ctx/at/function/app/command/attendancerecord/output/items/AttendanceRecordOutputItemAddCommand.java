package nts.uk.ctx.at.function.app.command.attendancerecord.output.items;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.ouput.items.AttendanceRecordOuputItems;

@Data
public class AttendanceRecordOutputItemAddCommand implements AttendanceRecordOuputItems.MementoGetter {

	private Boolean isNew;

	/**
	 * 会社ID
	 */
	private String cid;

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
	private int itemSelectionType;

}
