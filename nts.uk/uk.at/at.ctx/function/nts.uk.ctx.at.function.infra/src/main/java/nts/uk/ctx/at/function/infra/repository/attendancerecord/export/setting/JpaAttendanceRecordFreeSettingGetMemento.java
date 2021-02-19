package nts.uk.ctx.at.function.infra.repository.attendancerecord.export.setting;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordFreeSetting;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.export.setting.KfnmtRptWkAtdOut;

@Data
@AllArgsConstructor
public class JpaAttendanceRecordFreeSettingGetMemento implements AttendanceRecordFreeSetting.MementoGetter {

	/** The list kfnmt rpt wk atd out. */
	List<KfnmtRptWkAtdOut> listKfnmtRptWkAtdOut;

	/** The cid. */
	private String cid;

	/** The employee id. */
	private String employeeId;

	/** The item selection type. */
	private int itemSelectionType;

	@Override
	public List<AttendanceRecordExportSetting> getAttendanceRecordExportSettings() {
		List<AttendanceRecordExportSetting> result = this.listKfnmtRptWkAtdOut.stream()
				.map(i -> new AttendanceRecordExportSetting(i)).collect(Collectors.toList());
		return result;
	}
}
