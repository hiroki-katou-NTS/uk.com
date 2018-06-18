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
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingGetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.SealColumnName;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstSealColumn;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstSealColumn_;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.export.setting.KfnstAttndRecOutSet;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.export.setting.KfnstAttndRecOutSetPK;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.export.setting.KfnstAttndRecOutSetPK_;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.export.setting.KfnstAttndRecOutSet_;

/**
 * The Class JpaAttendanceRecordExportSettingRepository.
 */
@Stateless
public class JpaAttendanceRecordExportSettingRepository extends JpaRepository
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
		CriteriaQuery<KfnstAttndRecOutSet> cq = criteriaBuilder.createQuery(KfnstAttndRecOutSet.class);
		Root<KfnstAttndRecOutSet> root = cq.from(KfnstAttndRecOutSet.class);

		// build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(
				criteriaBuilder.equal(root.get(KfnstAttndRecOutSet_.id).get(KfnstAttndRecOutSetPK_.cid), companyId));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KfnstAttndRecOutSet> attendanceEntity = em.createQuery(cq).getResultList();

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
	public Optional<AttendanceRecordExportSetting> getAttendanceRecExpSet(String companyId, long code) {

		KfnstAttndRecOutSetPK PK = new KfnstAttndRecOutSetPK(companyId, code);
		return this.queryProxy().find(PK, KfnstAttndRecOutSet.class).map(e -> toDomain(e));
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
		this.deleteSealStamp(attendanceRecordExpSet.getCompanyId(), attendanceRecordExpSet.getCode());

		// Add seal stamp List
		int order = 1;
		long expCode = attendanceRecordExpSet.getCode().v();
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
	private void deleteSealStamp(String companyId, ExportSettingCode code) {
		// Delete seal Stamp list
		List<KfnstSealColumn> sealStampList = this.findAllSealColumn(companyId, code.v());
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
		addKfnstAttndRecOutSet(attendanceRecordExpSet);
		if (attendanceRecordExpSet.getSealStamp() != null)
			addKfnstSealcolumns(attendanceRecordExpSet);
	}

	private void addKfnstSealcolumns(AttendanceRecordExportSetting attendanceRecordExpSet) {
		String cid = attendanceRecordExpSet.getCompanyId();
		ExportSettingCode settingCode = attendanceRecordExpSet.getCode();
		// remove Seal stamps
		deleteSealStamp(cid, settingCode);

		// Insert Seal Stamp List
		int order = 1;
		for (SealColumnName seal : attendanceRecordExpSet.getSealStamp()) {
			this.commandProxy().insert(toSealStampEntity(cid, settingCode.v(), seal, order++));
		}
		this.getEntityManager().flush();
	}

	private void addKfnstAttndRecOutSet(AttendanceRecordExportSetting attendanceRecordExpSet) {
		KfnstAttndRecOutSetPK pk = new KfnstAttndRecOutSetPK(attendanceRecordExpSet.getCompanyId(),
				attendanceRecordExpSet.getCode().v());
		Optional<KfnstAttndRecOutSet> entityFromDb = this.queryProxy().find(pk, KfnstAttndRecOutSet.class);
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
		deleteSealStamp(domain.getCompanyId(), domain.getCode());
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
	public AttendanceRecordExportSetting toDomain(KfnstAttndRecOutSet attendanceEntity) {
		List<KfnstSealColumn> sealColumnEntity = this.findAllSealColumn(attendanceEntity.getId().getCid(),
				attendanceEntity.getId().getExportCd());
		AttendanceRecordExportSettingGetMemento memento = new JpaAttendanceRecordExportSettingGetMemento(
				attendanceEntity, sealColumnEntity);

		return new AttendanceRecordExportSetting(memento);

	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the kfnst attnd rec out set
	 */
	public KfnstAttndRecOutSet toEntity(AttendanceRecordExportSetting domain) {

		KfnstAttndRecOutSetPK PK = new KfnstAttndRecOutSetPK(domain.getCompanyId(), domain.getCode().v());

		KfnstAttndRecOutSet entity = this.queryProxy().find(PK, KfnstAttndRecOutSet.class)
				.orElse(new KfnstAttndRecOutSet(PK, null, new BigDecimal(0), new BigDecimal(1)));

		JpaAttendanceRecordExportSettingSetMemento setMemento = new JpaAttendanceRecordExportSettingSetMemento(entity);
		domain.saveToMemento(setMemento);

		return setMemento.getEntity();
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
	private List<KfnstSealColumn> findAllSealColumn(String companyId, long exportCode) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KfnstSealColumn> cq = criteriaBuilder.createQuery(KfnstSealColumn.class);
		Root<KfnstSealColumn> root = cq.from(KfnstSealColumn.class);

		// build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KfnstSealColumn_.cid), companyId));
		predicates.add(criteriaBuilder.equal(root.get(KfnstSealColumn_.exportCd), exportCode));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));

		// query data and return
		return em.createQuery(cq).getResultList();

	}

}
