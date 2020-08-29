package nts.uk.ctx.at.function.infra.repository.attendancerecord.export.setting;

import java.util.List;

import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordOuputItems;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordStandardSetting;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.export.setting.KfnmtRptWkAtdOut;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnmtRptWkAtdOutatd;

public class JpaAttendanceRecordOuputItemsGetMemento implements AttendanceRecordOuputItems.MementoGetter ,  AttendanceRecordStandardSetting.MementoGetter{

	private List<KfnmtRptWkAtdOut> kfnmtRptWkAtdOuts;
	
	private List<KfnmtRptWkAtdOutatd> kfnmtRptWkAtdOutatd;
	

	@Override
	public String getCid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AttendanceRecordExportSetting> getAttendanceRecordExportSettings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEmployeeId() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getItemSelectionType() {
		// TODO Auto-generated method stub
		return 0;
	}

}
