package nts.uk.ctx.at.function.app.command.attendancerecord.output.items;

import java.util.List;

import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.app.command.attendancerecord.export.setting.AttendanceRecordExportSettingCommand;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ItemSelectionType;
import nts.uk.ctx.at.function.dom.attendancerecord.ouput.items.AttendanceRecordOuputItems;

@Setter
@NoArgsConstructor
public class AttendanceRecordOutputItemCommand implements AttendanceRecordOuputItems.MementoGetter {

	private Boolean isNew;

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 出力項目: 出勤簿の出力項目設定 (List)
	 */
	private List<AttendanceRecordExportSettingCommand> attendanceRecordExportSettings;

	/**
	 * 社員ID
	 */
	private String employeeId;

	/**
	 * 項目選択種類
	 */
	private int itemSelectionType;

	@Override
	public String getCid() {
		return this.cid;
	}

	@Override
	public List<AttendanceRecordExportSetting> getAttendanceRecordExportSettings() {
//		return this.attendanceRecordExportSettings.stream()
//												.map(command -> {
//													AttendanceRecordExportSetting attendanceRecordExportSetting = new AttendanceRecordExportSetting();
//													attendanceRecordExportSetting.setCode(command.getCode());
//												})
		return null;
	}

	@Override
	public String getEmployeeId() {
		return this.employeeId;
	}

	@Override
	public ItemSelectionType getItemSelectionType() {
		return ItemSelectionType.valueOf(this.itemSelectionType);
	}



}
