package nts.uk.ctx.at.function.infra.repository.attendancerecord.export.setting;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingGetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.SealColumnName;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnmtRptWkAtdOutseal;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.export.setting.KfnmtRptWkAtdOut;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class JpaAttendanceRecordExportSettingRepository.
 */
@Stateless
public class JpaAttendanceRecordExportSettingRepo extends JpaRepository
		implements AttendanceRecordExportSettingRepository {

	public static final String GET_FREE_SETTING_BY_EMPLOYEE_AND_COMPANY = "SELECT ot FROM KfnmtRptWkAtdOut ot"
			+ "	WHERE ot.cid = :companyId"
			+ "		AND ot.sid = :employeeId"
			+ "		AND ot.itemSelType = :itemSelType";
	
	public static final String GET_STANDARD_SETTING_BY_COMPANY = "SELECT ot FROM KfnmtRptWkAtdOut ot"
			+ "	WHERE ot.cid = :companyId"
			+ "		AND ot.itemSelType = :itemSelType";
	
	public static final String GET_SETTING_BY_COMPANY_AND_CODE = "SELECT ot FROM KfnmtRptWkAtdOut ot"
			+ "	WHERE ot.cid = :companyId"
			+ "		AND ot.itemSelType = :itemSelType"
			+ "		AND ot.itemCode = :itemCode";
	
	public static final String GET_SETTING_BY_EMPLOYEE_AND_COMPANY_AND_CODE = "SELECT ot FROM KfnmtRptWkAtdOut ot"
			+ "	WHERE ot.cid = :companyId"
			+ "		AND ot.sid = :employeeId"
			+ "		AND ot.itemSelType = :itemSelType"
			+ "		AND ot.itemCode = :itemCode";
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * AttendanceRecordExportSettingRepository#getAllAttendanceRecExpSet(java.
	 * lang.String)
	 */
	@Override
	public List<AttendanceRecordExportSetting> getAllAttendanceRecExpSet(String companyId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KfnmtRptWkAtdOut> cq = criteriaBuilder.createQuery(KfnmtRptWkAtdOut.class);
		Root<KfnmtRptWkAtdOut> root = cq.from(KfnmtRptWkAtdOut.class);

		// build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(
				criteriaBuilder.equal(root.get("cid"), companyId));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KfnmtRptWkAtdOut> attendanceEntity = em.createQuery(cq).getResultList();

		// return
		return attendanceEntity.isEmpty() ? new ArrayList<AttendanceRecordExportSetting>()
				: attendanceEntity.stream().map(entity -> toDomain(entity)).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * AttendanceRecordExportSettingRepository#getAttendanceRecExpSet(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public Optional<AttendanceRecordExportSetting> getAttendanceRecExpSet(String companyId, String code) {
		String sql = "SELECT ot FROM KfnmtRptWkAtdOut ot WHERE ot.exportCD = :exportCD AND ot.cid = :companyId ";
		Optional<AttendanceRecordExportSetting> oKfnmtRptWkAtdOut = this.queryProxy().query(sql, KfnmtRptWkAtdOut.class)
													.setParameter("exportCD", code)
													.setParameter("companyId", companyId)
													.getSingle(c -> this.toDomain(c));

		return oKfnmtRptWkAtdOut;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * AttendanceRecordExportSettingRepository#updateAttendanceRecExpSet(nts.uk.
	 * ctx.at.function.dom.attendancerecord.export.setting.
	 * AttendanceRecordExportSetting)
	 */
	@Override
	public void updateAttendanceRecExpSet(AttendanceRecordExportSetting attendanceRecordExpSet) {

		// update attendance info
		this.commandProxy().update(toEntity(attendanceRecordExpSet));

		// Delete seal stamp List
		this.deleteSealStamp(attendanceRecordExpSet.getCompanyId(),attendanceRecordExpSet.getLayoutId());

		// Add seal stamp List
		int order = 1;
		long expCode = Long.parseLong(attendanceRecordExpSet.getCode().v());
	
		String cid = attendanceRecordExpSet.getCompanyId();
		String layoutId = attendanceRecordExpSet.getLayoutId();
		for (SealColumnName seal : attendanceRecordExpSet.getSealStamp()) {
			this.commandProxy().insert(toSealStampEntity(cid, layoutId, seal, order++));
		}

	}

	/**
	 * To seal stamp entity.
	 *
	 * @param cId
	 *            the c id
	 * @param code
	 *            the code
	 * @param sealName
	 *            the seal name
	 * @return the kfnst seal column
	 */
	private KfnmtRptWkAtdOutseal toSealStampEntity(String cId, String layoutId, SealColumnName sealName, int order) {
		UUID columnId = UUID.randomUUID();
		
		KfnmtRptWkAtdOutseal outseal = new KfnmtRptWkAtdOutseal();
		outseal.setColumnId(columnId.toString());
		outseal.setExclusVer(0);
		outseal.setContractCd("111");
		outseal.setCid(cId);
		outseal.setLayoutId(layoutId);
		outseal.setSealStampName(sealName.toString());
		outseal.setSealOrder(new BigDecimal(order));
		return outseal;
	}

	/**
	 * Delete seal stamp.
	 *
	 * @param companyId
	 *            the company id
	 * @param code
	 *            the code
	 */
	private void deleteSealStamp(String companyId, String layoutId) {
		// Delete seal Stamp list
		List<KfnmtRptWkAtdOutseal> sealStampList = this.findAllSealColumn(companyId,layoutId);
		this.commandProxy().removeAll(sealStampList);
		this.getEntityManager().flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * AttendanceRecordExportSettingRepository#addAttendanceRecExpSet(nts.uk.ctx
	 * .at.function.dom.attendancerecord.export.setting.
	 * AttendanceRecordExportSetting)
	 */
	@Override
	public void addAttendanceRecExpSet(AttendanceRecordExportSetting attendanceRecordExpSet) {
		addKfnmtRptWkAtdOut(attendanceRecordExpSet);
		if (attendanceRecordExpSet.getSealStamp() != null)
			addKfnmtRptWkAtdOutseals(attendanceRecordExpSet);
	}

	private void addKfnmtRptWkAtdOutseals(AttendanceRecordExportSetting attendanceRecordExpSet) {
		String cid = attendanceRecordExpSet.getCompanyId();
		String layoutId = attendanceRecordExpSet.getLayoutId();
		ExportSettingCode settingCode = attendanceRecordExpSet.getCode();
		// remove Seal stamps
		deleteSealStamp(attendanceRecordExpSet.getCompanyId(),attendanceRecordExpSet.getLayoutId());

		// Insert Seal Stamp List
		int order = 1;
		for (SealColumnName seal : attendanceRecordExpSet.getSealStamp()) {
			this.commandProxy().insert(toSealStampEntity(cid, layoutId, seal, order++));
		}
		this.getEntityManager().flush();
	}

	private void addKfnmtRptWkAtdOut(AttendanceRecordExportSetting attendanceRecordExpSet) {
		KfnmtRptWkAtdOut pk = new KfnmtRptWkAtdOut();
		pk.setLayoutId(attendanceRecordExpSet.getLayoutId());
		Optional<KfnmtRptWkAtdOut> entityFromDb = this.queryProxy().find(pk.getLayoutId(), KfnmtRptWkAtdOut.class);
		if (entityFromDb.isPresent()) {
			this.commandProxy().update(toEntity(attendanceRecordExpSet));
		} else {
			// Insert Attendance
			this.commandProxy().insert(toEntity(attendanceRecordExpSet));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * AttendanceRecordExportSettingRepository#deleteAttendanceRecExpSet(nts.uk.
	 * ctx.at.function.dom.attendancerecord.export.setting.
	 * AttendanceRecordExportSetting)
	 */
	@Override
	public void deleteAttendanceRecExpSet(AttendanceRecordExportSetting domain) {
		this.commandProxy().remove(toEntity(domain));
		deleteSealStamp(domain.getCompanyId(),domain.getLayoutId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * AttendanceRecordExportSettingRepository#getSealStamp(java.lang.String,
	 * long)
	 */
	@Override
	public List<String> getSealStamp(String companyId, long code) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KfnmtRptWkAtdOutseal> cq = criteriaBuilder.createQuery(KfnmtRptWkAtdOutseal.class);
		Root<KfnmtRptWkAtdOutseal> root = cq.from(KfnmtRptWkAtdOutseal.class);

		// build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));

		// order by
		cq.orderBy(criteriaBuilder.asc(root.get("sealOrder")));

		// query data
		List<KfnmtRptWkAtdOutseal> sealStampEntity = em.createQuery(cq).getResultList();

		// return
		return sealStampEntity.isEmpty() ? new ArrayList<String>()
				: sealStampEntity.stream().map(KfnmtRptWkAtdOutseal::getSealStampName).collect(Collectors.toList());
	}

	/**
	 * To domain.
	 *
	 * @param attendanceEntity
	 *            the attendance entity
	 * @return the attendance record export setting
	 */
	public AttendanceRecordExportSetting toDomain(KfnmtRptWkAtdOut attendanceEntity) {
		List<KfnmtRptWkAtdOutseal> sealEntity = this.findAllSealColumn(attendanceEntity.getCid(), attendanceEntity.getLayoutId());
		
		AttendanceRecordExportSettingGetMemento memento = new JpaAttendanceRecordExportSettingGetMemento(
				attendanceEntity, sealEntity);

		return new AttendanceRecordExportSetting(memento);

	}


	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kfnmtrptwkatdout
	 */
	public KfnmtRptWkAtdOut toEntity(AttendanceRecordExportSetting domain) {
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		KfnmtRptWkAtdOut PK = new KfnmtRptWkAtdOut();
		PK.setLayoutId(domain.getLayoutId());
		PK.setContractCd("1");
		PK.setExclusVer(0);
		PK.setItemSelType(0);
		PK.setCid(companyId);
		PK.setSid(employeeId);
		PK.setExportCD(domain.getCode().v());
		PK.setName(domain.getName().v());
		PK.setSealUseAtr(new BigDecimal(0));
		PK.setNameUseAtr(new BigDecimal(0));
		PK.setCharSizeType(new BigDecimal(1));
		PK.setMonthAppDispAtr(new BigDecimal(0));
		KfnmtRptWkAtdOut entity = this.queryProxy().find(PK.getLayoutId(), KfnmtRptWkAtdOut.class)
				.orElse(PK);
		
		// new decimal 

		JpaAttendanceRecordExportSettingSetMemento setMemento = new JpaAttendanceRecordExportSettingSetMemento(entity);
		
		domain.saveToMemento(setMemento);

		return setMemento.getResEntity();
	}

	/**
	 * Find all seal column.
	 *
	 * @param companyId
	 *            the company id
	 * @param exportCode
	 *            the export code
	 * @return the list
	 */
	private List<KfnmtRptWkAtdOutseal> findAllSealColumn(String companyId, String layoutId) {

		List<KfnmtRptWkAtdOutseal> listKfnmtRptWkAtdOutseal = new ArrayList<>();
		String sql = "SELECT os FROM KfnmtRptWkAtdOutseal os WHERE os.cid = ? AND os.layoutId = ?";
		try (PreparedStatement statement = this.connection().prepareStatement(sql)) {
			statement.setString(1, companyId);
			statement.setString(2, layoutId);
			listKfnmtRptWkAtdOutseal.addAll(new NtsResultSet(statement.executeQuery()).getList(rec -> {
				KfnmtRptWkAtdOutseal entity = new KfnmtRptWkAtdOutseal();
				entity.setLayoutId(rec.getString("LAYOUT_ID"));
				entity.setColumnId(rec.getString("COLUMN_ID"));
				entity.setCid(rec.getString("CID"));
				entity.setSealStampName(rec.getString("SEAL_STAMP_NAME"));
				entity.setSealOrder(rec.getBigDecimal("SEAL_ORDER"));
				return entity;
			}));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listKfnmtRptWkAtdOutseal;
		
	}

}
