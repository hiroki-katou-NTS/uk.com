package nts.uk.ctx.at.function.infra.repository.attendancerecord.export.setting;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordStandardSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordStandardSettingRepository;

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

	@Override
	public Optional<AttendanceRecordStandardSetting> getStandardByCompanyId(String compnayId) {
		// TODO Auto-generated method stub
		return null;
	}

}
