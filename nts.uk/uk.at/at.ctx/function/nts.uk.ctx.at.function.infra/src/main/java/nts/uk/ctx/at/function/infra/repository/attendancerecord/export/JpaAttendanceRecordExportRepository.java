package nts.uk.ctx.at.function.infra.repository.attendancerecord.export;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExport;
import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExportGetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExportRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRec;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRecPK_;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRec_;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Class JpaAttendanceRecordExportRepository.
 * 
 * @author NWS_QUANGNT
 */
@Stateless
public class JpaAttendanceRecordExportRepository extends JpaRepository implements AttendanceRecordExportRepository {

	static final long UPPER_POSITION = 1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.
	 * AttendanceRecordExportRepository#getAllAttendanceRecordExportDaily(java.
	 * lang.String, long)
	 */
	@Override
	public List<AttendanceRecordExport> getAllAttendanceRecordExportDaily(String companyId, long exportSettingCode) {

		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KfnstAttndRec> cq = criteriaBuilder.createQuery(KfnstAttndRec.class);
		Root<KfnstAttndRec> root = cq.from(KfnstAttndRec.class);

		// build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();

		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRec_.id).get(KfnstAttndRecPK_.cid), companyId));
		predicates.add(
				criteriaBuilder.equal(root.get(KfnstAttndRec_.id).get(KfnstAttndRecPK_.exportCd), exportSettingCode));
		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRec_.id).get(KfnstAttndRecPK_.outputAtr), 1));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KfnstAttndRec> entityList = em.createQuery(cq).getResultList();

		List<AttendanceRecordExport> domainList = new ArrayList<AttendanceRecordExport>();

		// for each item of entityList
		entityList.forEach(item1 -> {
			// find domain is exist?,
			if (!findInList(domainList, item1)) {
				long columnIndex1 = item1.getId().getColumnIndex();
				long position1 = item1.getId().getPosition();
				// if not exist, toDomain
				entityList.forEach(item2 -> {
					// find if the same columnIndex
					if (columnIndex1 == item2.getId().getColumnIndex() && position1 != item2.getId().getPosition()) {
						// toDomain
						AttendanceRecordExport domain = new AttendanceRecordExport();
						if (columnIndex1 == UPPER_POSITION)
							domain = this.toDomain(item1, item2);
						else
							domain = this.toDomain(item2, item1);
						// Add domain to list
						domainList.add(domain);
					}
				});
			}
			if (!findInList(domainList, item1)) {
				AttendanceRecordExport domain = new AttendanceRecordExport();
				domain = this.toDomain(item1, null);
				// Add domain to list
				domainList.add(domain);
			}
		});

		return domainList.stream().filter(item -> item != null).collect(Collectors.toList());
	}

	/**
	 * Find in list.
	 *
	 * @param list
	 *            the list
	 * @param item
	 *            the item
	 * @return the boolean
	 */
	public Boolean findInList(List<AttendanceRecordExport> list, KfnstAttndRec item) {

		for (AttendanceRecordExport e : list) {
			if (e.getColumnIndex() == (int) item.getId().getColumnIndex())
				return true;
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.
	 * AttendanceRecordExportRepository#getAllAttendanceRecordExportMonthly(java
	 * .lang.String, long)
	 */
	@Override
	public List<AttendanceRecordExport> getAllAttendanceRecordExportMonthly(String companyId, long exportSettingCode) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KfnstAttndRec> cq = criteriaBuilder.createQuery(KfnstAttndRec.class);
		Root<KfnstAttndRec> root = cq.from(KfnstAttndRec.class);

		// build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();

		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRec_.id).get(KfnstAttndRecPK_.cid), companyId));
		predicates.add(
				criteriaBuilder.equal(root.get(KfnstAttndRec_.id).get(KfnstAttndRecPK_.exportCd), exportSettingCode));
		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRec_.id).get(KfnstAttndRecPK_.outputAtr), 2));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KfnstAttndRec> entityList = em.createQuery(cq).getResultList();

		List<AttendanceRecordExport> domainList = new ArrayList<AttendanceRecordExport>();

		// for each item of entityList
		entityList.forEach(item1 -> {
			// find domain is exist?,
			if (!findInList(domainList, item1)) {

				long columnIndex1 = item1.getId().getColumnIndex();
				long position1 = item1.getId().getPosition();
				// if not exist, toDomain
				entityList.forEach(item2 -> {
					// find if the same columnIndex
					if (columnIndex1 == item2.getId().getColumnIndex() && position1 != item2.getId().getPosition()) {
						// toDomain
						AttendanceRecordExport domain = new AttendanceRecordExport();
						if (position1 == UPPER_POSITION)
							domain = this.toDomain(item1, item2);
						else
							domain = this.toDomain(item2, item1);
						// Add domain to list
						domainList.add(domain);
					}
				});
			}
			if (!findInList(domainList, item1)) {
				AttendanceRecordExport domain = new AttendanceRecordExport();
				domain = this.toDomain(item1, null);
				// Add domain to list
				domainList.add(domain);
			}
		});

		return domainList.stream().filter(item -> item != null).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.
	 * AttendanceRecordExportRepository#updateAttendanceRecordExport(nts.uk.ctx.
	 * at.function.dom.attendancerecord.export.AttendanceRecordExport)
	 */
	@Override
	public void updateAttendanceRecordExport(AttendanceRecordExport attendanceRecordExport) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.
	 * AttendanceRecordExportRepository#addAttendanceRecordExport(nts.uk.ctx.at.
	 * function.dom.attendancerecord.export.AttendanceRecordExport)
	 */
	@Override
	public void addAttendanceRecordExport(AttendanceRecordExport attendanceRecordExport) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.
	 * AttendanceRecordExportRepository#deleteAttendanceRecord(java.lang.String,
	 * nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * ExportSettingCode)
	 */
	@Override
	public void deleteAttendanceRecord(String companyId, ExportSettingCode exportSettingCode) {
		List<KfnstAttndRec> items = this.findAllAttendanceRecord(companyId, exportSettingCode);
		if (items != null && !items.isEmpty()) {
			this.removeAllAttndRec(items);
			this.getEntityManager().flush();
		}
	}

	/**
	 * To domain.
	 *
	 * @param upperEntity
	 *            the upper entity
	 * @param lowerEntity
	 *            the lower entity
	 * @return the attendance record export
	 */
	public AttendanceRecordExport toDomain(KfnstAttndRec upperEntity, KfnstAttndRec lowerEntity) {

		AttendanceRecordExportGetMemento memento = new JpaAttendanceRecordExportGetMemento(upperEntity, lowerEntity);

		return new AttendanceRecordExport(memento);

	}

	/**
	 * Find all attendance record.
	 *
	 * @param companyId
	 *            the company id
	 * @param exportSettingCode
	 *            the export setting code
	 * @return the list
	 */
	private List<KfnstAttndRec> findAllAttendanceRecord(String companyId, ExportSettingCode exportSettingCode) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KfnstAttndRec> criteriaQuery = criteriaBuilder.createQuery(KfnstAttndRec.class);
		Root<KfnstAttndRec> root = criteriaQuery.from(KfnstAttndRec.class);

		// Build query
		criteriaQuery.select(root);

		// create condition
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRec_.id).get(KfnstAttndRecPK_.cid), companyId));
		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRec_.id).get(KfnstAttndRecPK_.exportCd),
				exportSettingCode.v()));

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KfnstAttndRec> kfnstAttndRecs = em.createQuery(criteriaQuery).getResultList();
		return kfnstAttndRecs.isEmpty() ? new ArrayList<>() : kfnstAttndRecs;
	}

	/**
	 * Removes the all attnd rec.
	 *
	 * @param items
	 *            the items
	 */
	public void removeAllAttndRec(List<KfnstAttndRec> items) {
		if (!items.isEmpty()) {
			this.commandProxy().removeAll(items);
			this.getEntityManager().flush();
		}

	}

}
