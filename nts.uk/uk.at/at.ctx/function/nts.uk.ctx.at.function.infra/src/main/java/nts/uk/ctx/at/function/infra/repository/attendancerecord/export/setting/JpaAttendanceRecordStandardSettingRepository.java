package nts.uk.ctx.at.function.infra.repository.attendancerecord.export.setting;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordStandardSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordStandardSettingRepository;

@Stateless
public class JpaAttendanceRecordStandardSettingRepository extends JpaRepository
		implements AttendanceRecordStandardSettingRepository {

	@Override
	public void add(AttendanceRecordStandardSetting domain) {

	}

	@Override
	public void update(AttendanceRecordStandardSetting domain) {

	}

	private static AttendanceRecordStandardSetting toEntity(AttendanceRecordStandardSetting domain) {
		AttendanceRecordStandardSetting entity = new AttendanceRecordStandardSetting();
//		domain.setMemento(entity);
		return entity;
	}

	@Override
	public Optional<AttendanceRecordStandardSetting> getStandardByCompanyId(String compnayId) {

		return null;
	}

}
