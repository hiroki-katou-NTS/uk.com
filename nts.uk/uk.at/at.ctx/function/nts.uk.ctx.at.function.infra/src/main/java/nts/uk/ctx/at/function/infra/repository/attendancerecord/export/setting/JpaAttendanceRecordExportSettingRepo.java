package nts.uk.ctx.at.function.infra.repository.attendancerecord.export.setting;

import java.math.BigDecimal;
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
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingGetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.SealColumnName;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnmtRptWkAtdOutseal;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstSealColumn;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstSealColumn_;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.export.setting.KfnmtRptWkAtdOut;

/**
 * The Class JpaAttendanceRecordExportSettingRepository.
 */
@Stateless
public class JpaAttendanceRecordExportSettingRepo extends JpaRepository
		implements AttendanceRecordExportSettingRepository {

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
	public Optional<AttendanceRecordExportSetting> getAttendanceRecExpSet(String layoutId) {

		KfnmtRptWkAtdOut pk = new KfnmtRptWkAtdOut();
		pk.setLayoutID(layoutId);
		return this.queryProxy().find(pk, KfnmtRptWkAtdOut.class).map(e -> toDomain(e));
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
		this.deleteSealStamp(attendanceRecordExpSet.getLayoutId());

		// Add seal stamp List
		int order = 1;
		long expCode = Long.parseLong(attendanceRecordExpSet.getCode().v());
		String cid = attendanceRecordExpSet.getCompanyId();
		for (SealColumnName seal : attendanceRecordExpSet.getSealStamp()) {
			this.commandProxy().insert(toSealStampEntity(cid, expCode, seal, order++));
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
	private KfnstSealColumn toSealStampEntity(String cId, long code, SealColumnName sealName, int order) {
		UUID columnId = UUID.randomUUID();

		return new KfnstSealColumn(columnId.toString(), cId, new BigDecimal(code), sealName.toString(),
				new BigDecimal(order));
	}

	/**
	 * Delete seal stamp.
	 *
	 * @param companyId
	 *            the company id
	 * @param code
	 *            the code
	 */
	private void deleteSealStamp(String layoutId) {
		// Delete seal Stamp list
		List<KfnmtRptWkAtdOutseal> sealStampList = this.findAllSealColumn(layoutId);
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
			addKfnstSealcolumns(attendanceRecordExpSet);
	}

	private void addKfnstSealcolumns(AttendanceRecordExportSetting attendanceRecordExpSet) {
		String cid = attendanceRecordExpSet.getCompanyId();
		ExportSettingCode settingCode = attendanceRecordExpSet.getCode();
		// remove Seal stamps
		deleteSealStamp(attendanceRecordExpSet.getLayoutId());

		// Insert Seal Stamp List
		int order = 1;
		for (SealColumnName seal : attendanceRecordExpSet.getSealStamp()) {
			this.commandProxy().insert(toSealStampEntity(cid, Long.parseLong(settingCode.v()), seal, order++));
		}
		this.getEntityManager().flush();
	}

	private void addKfnmtRptWkAtdOut(AttendanceRecordExportSetting attendanceRecordExpSet) {
		KfnmtRptWkAtdOut pk = new KfnmtRptWkAtdOut();
		pk.setLayoutID(attendanceRecordExpSet.getLayoutId());
		Optional<KfnmtRptWkAtdOut> entityFromDb = this.queryProxy().find(pk, KfnmtRptWkAtdOut.class);
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
		deleteSealStamp(domain.getLayoutId());
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
		CriteriaQuery<KfnstSealColumn> cq = criteriaBuilder.createQuery(KfnstSealColumn.class);
		Root<KfnstSealColumn> root = cq.from(KfnstSealColumn.class);

		// build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KfnstSealColumn_.cid), companyId));
		predicates.add(criteriaBuilder.equal(root.get(KfnstSealColumn_.exportCd), code));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));

		// order by
		cq.orderBy(criteriaBuilder.asc(root.get(KfnstSealColumn_.sealOrder)));

		// query data
		List<KfnstSealColumn> sealStampEntity = em.createQuery(cq).getResultList();

		// return
		return sealStampEntity.isEmpty() ? new ArrayList<String>()
				: sealStampEntity.stream().map(KfnstSealColumn::getSealStampName).collect(Collectors.toList());
	}

	/**
	 * To domain.
	 *
	 * @param attendanceEntity
	 *            the attendance entity
	 * @return the attendance record export setting
	 */
	public AttendanceRecordExportSetting toDomain(KfnmtRptWkAtdOut attendanceEntity) {
//		List<KfnstSealColumn> sealColumnEntity = this.findAllSealColumn(attendanceEntity.getLayoutID());
		List<KfnmtRptWkAtdOutseal> sealEntity = this.findAllSealColumn(attendanceEntity.getLayoutID());
//		AttendanceRecordExportSettingGetMemento memento = new JpaAttendanceRecordExportSettingGetMemento(
//				attendanceEntity, sealColumnEntity);
		
		AttendanceRecordExportSettingGetMemento memento = new JpaAttendanceRecordExportSettingGetMemento(
				attendanceEntity, sealEntity);


		return new AttendanceRecordExportSetting(memento);

	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the kfnst attnd rec out set
	 */
	public KfnmtRptWkAtdOut toEntity(AttendanceRecordExportSetting domain) {

		KfnmtRptWkAtdOut PK = new KfnmtRptWkAtdOut();
		PK.setLayoutID(domain.getLayoutId());
		KfnmtRptWkAtdOut entity = this.queryProxy().find(PK, KfnmtRptWkAtdOut.class)
				.orElse(new KfnmtRptWkAtdOut());
		
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
	private List<KfnmtRptWkAtdOutseal> findAllSealColumn(String layoutId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KfnmtRptWkAtdOutseal> cq = criteriaBuilder.createQuery(KfnmtRptWkAtdOutseal.class);
		Root<KfnmtRptWkAtdOutseal> root = cq.from(KfnmtRptWkAtdOutseal.class);

		// build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
// TODO
		predicates.add(criteriaBuilder.equal(root.get("layoutid"), layoutId));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));

		// query data and return
		return em.createQuery(cq).getResultList();

	}

}
