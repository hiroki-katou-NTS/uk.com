package nts.uk.ctx.at.function.infra.repository.attendancerecord.export;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExport;
import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExportRepository;

@Stateless
public class JpaAttendanceRecordExportRepository implements AttendanceRecordExportRepository{

	@Override
	public List<AttendanceRecordExport> getAllAttendanceRecordExport(String companyId, String exportSettingCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AttendanceRecordExport> getAttendanceRecordExportByIndex(String companyId, String exportSettingCode,
			int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateAttendanceRecordExport(AttendanceRecordExport attendanceRecordExport) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addAttendanceRecordExport(AttendanceRecordExport attendanceRecordExport) {
		// TODO Auto-generated method stub
		
	}

}
