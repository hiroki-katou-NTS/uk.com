package nts.uk.ctx.at.function.infra.repository.attendancerecord.export.setting;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordOuputItems;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordOuputItemsRepository;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.export.setting.KfnmtRptWkAtdOut;

@Stateless
public class JpaAttendanceRecordOuputItemsRepository extends JpaRepository
		implements AttendanceRecordOuputItemsRepository {
	// Select all
	private static final String QUERY_SELECT_ALL = "SELECT f FROM KfnmtRptWkAtdOut f";
	// Select one
	private static final String QUERY_SELECT_BY_COMPANY_AND_EMPLOYEE = QUERY_SELECT_ALL
			+ " WHERE f.cid = :cId AND f.sid = :sId";

	@Override
	public void add(AttendanceRecordOuputItems domain) {
		// Convert data to entity
		KfnmtRptWkAtdOut entity = JpaAttendanceRecordOuputItemsRepository.toEntity(domain);
		// Insert entity
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(AttendanceRecordOuputItems domain) {
		// Convert data to entity
		KfnmtRptWkAtdOut entity = JpaAttendanceRecordOuputItemsRepository.toEntity(domain);
		KfnmtRptWkAtdOut oldEntity = this.queryProxy().find(entity.getLayoutID(), KfnmtRptWkAtdOut.class).get();
		oldEntity.setExclusVer(entity.getExclusVer());
		oldEntity.setContractCD(entity.getContractCD());
		oldEntity.setItemSelType(entity.getItemSelType());
		oldEntity.setCid(entity.getCid());
		oldEntity.setSid(entity.getSid());
		oldEntity.setExportCD(entity.getExportCD());
		oldEntity.setName(entity.getName());
		oldEntity.setSealUseAtr(entity.isSealUseAtr());
		oldEntity.setNameUseAtr(entity.getNameUseAtr());
		oldEntity.setCharSizeType(entity.getCharSizeType());
		oldEntity.setMonthAppDispAtr(entity.getMonthAppDispAtr());
		// update entity
		this.commandProxy().update(oldEntity);
	}

	private static KfnmtRptWkAtdOut toEntity(AttendanceRecordOuputItems domain) {
		KfnmtRptWkAtdOut entity = new KfnmtRptWkAtdOut();
//		domain.setMemento(entity);
		return entity;
	}

	@Override
	public Optional<AttendanceRecordOuputItems> getOutputItemsByCompnayAndEmployee(String companyId,
			String employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<AttendanceRecordOuputItems> findByCompanyEmployeeAndCode(String companyId, String employeeId,
			String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<AttendanceRecordOuputItems> findByCompanyEmployeeCodeAndLayoutId(String companyId,
			String employeeId, String code, String layoutId) {
		// TODO Auto-generated method stub
		return null;
	}
}
