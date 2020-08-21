package nts.uk.ctx.at.function.infra.repository.attendancerecord.standard.export;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.standard.setting.AttendanceRecordStandardSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.standard.setting.AttendanceRecordStandardSettingRepository;

@Stateless
public class JpaAttendanceRecordStandardSettingRepository extends JpaRepository implements AttendanceRecordStandardSettingRepository{

	@Override
	public void add(AttendanceRecordStandardSetting domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(AttendanceRecordStandardSetting domain) {
		// TODO Auto-generated method stub
		
	}
	
	private static AttendanceRecordStandardSetting toEntity(AttendanceRecordStandardSetting domain) {
		AttendanceRecordStandardSetting entity = new AttendanceRecordStandardSetting();
//		domain.setMemento(entity);
		return entity;
	}

}
