package nts.uk.ctx.at.function.infra.repository.attendancerecord.export.setting;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordStandardSetting;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.export.setting.KfnmtRptWkAtdOut;

@Data
@AllArgsConstructor
public class JpaAttendanceRecordStandardSettingGetMemento
		implements AttendanceRecordStandardSetting.MementoGetter {
	
	private List<KfnmtRptWkAtdOut> kfnmtRptWkAtdOut;
	
	private String cid;
	
	private int itemSelectionType;

	@Override
	public List<AttendanceRecordExportSetting> getAttendanceRecordExportSettings() {
		List<AttendanceRecordExportSetting> result = this.kfnmtRptWkAtdOut.stream()
				.map(i -> new AttendanceRecordExportSetting(i))
				.collect(Collectors.toList());
		return result;
	}

}
