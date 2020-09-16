package nts.uk.ctx.at.function.infra.repository.attendancerecord.export.setting;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordFreeSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordFreeSettingRepository;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.export.setting.KfnmtRptWkAtdOut;

@Stateless
public class JpaAttendanceRecordFreeSettingRepository extends JpaRepository
		implements AttendanceRecordFreeSettingRepository {
	
	
	// Select all
	private static final String QUERY_SELECT_ALL = "SELECT f FROM KfnmtRptWkAtdOut f";
	// Select one
	private static final String QUERY_SELECT_BY_COMPANY_AND_EMPLOYEE = QUERY_SELECT_ALL
			+ " WHERE f.cid = :cId AND f.sid = :sId";

	@Override
	public void add(AttendanceRecordFreeSetting domain) {
		// Convert data to entity
		KfnmtRptWkAtdOut entity = JpaAttendanceRecordFreeSettingRepository.toEntity(domain);
		// Insert entity
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(AttendanceRecordFreeSetting domain) {
		// Convert data to entity
		KfnmtRptWkAtdOut entity = JpaAttendanceRecordFreeSettingRepository.toEntity(domain);
		KfnmtRptWkAtdOut oldEntity = this.queryProxy().find(entity.getLayoutId(), KfnmtRptWkAtdOut.class).get();
//		oldEntity.setExclusVer(entity.getExclusVer());
//		oldEntity.setContractCD(entity.getContractCD());
//		oldEntity.setItemSelType(entity.getItemSelType());
//		oldEntity.setCid(entity.getCid());
//		oldEntity.setSid(entity.getSid());
//		oldEntity.setExportCD(entity.getExportCD());
//		oldEntity.setName(entity.getName());
//		oldEntity.setSealUseAtr(entity.isSealUseAtr());
//		oldEntity.setNameUseAtr(entity.getNameUseAtr());
//		oldEntity.setCharSizeType(entity.getCharSizeType());
//		oldEntity.setMonthAppDispAtr(entity.getMonthAppDispAtr());
		// update entity
		this.commandProxy().update(oldEntity);
	}

	@Override
	public Optional<AttendanceRecordFreeSetting> getOutputItemsByCompnayAndEmployee(String companyId,
			String employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<AttendanceRecordFreeSetting> findByCompanyEmployeeAndCode(String companyId, String employeeId,
			String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<AttendanceRecordFreeSetting> findByCompanyEmployeeCodeAndLayoutId(String companyId,
			String employeeId, String code, String layoutId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private AttendanceRecordExportSetting toDomain(KfnmtRptWkAtdOut entity) {
		return new AttendanceRecordExportSetting();
//		return new AttendanceRecordExportSetting(entity);
	}
	
	private static KfnmtRptWkAtdOut toEntity(AttendanceRecordFreeSetting domain) {
		KfnmtRptWkAtdOut entity = new KfnmtRptWkAtdOut();
//		domain.setMemento(entity);
		return entity;
	}

	@Override
	public Optional<AttendanceRecordExportSetting> findByLayoutId(String layoutId) {
		// 2 list group by;
		return null;
	}

	@Override
	public Optional<AttendanceRecordFreeSetting> findByCompanyEmployeeAndCodeAndSelection(String companyId, String employeeId,
			long code, int selectionType) {
		
		return null;
	}
}
