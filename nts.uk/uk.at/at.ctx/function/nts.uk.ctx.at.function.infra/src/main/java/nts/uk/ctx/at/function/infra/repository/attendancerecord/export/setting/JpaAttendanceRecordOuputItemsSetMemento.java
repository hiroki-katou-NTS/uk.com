package nts.uk.ctx.at.function.infra.repository.attendancerecord.export.setting;

import java.util.List;

import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordOuputItems;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordStandardSetting;

public class JpaAttendanceRecordOuputItemsSetMemento
		implements AttendanceRecordOuputItems.MementoSetter, AttendanceRecordStandardSetting.MementoSetter {

	@Override
	public void setCid(String cid) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAttendanceRecordExportSettings(List<AttendanceRecordExportSetting> attendanceRecordExportSettings) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEmployeeId(String employeeId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setItemSelectionType(int itemSelectionType) {
		// TODO Auto-generated method stub

	}

}
