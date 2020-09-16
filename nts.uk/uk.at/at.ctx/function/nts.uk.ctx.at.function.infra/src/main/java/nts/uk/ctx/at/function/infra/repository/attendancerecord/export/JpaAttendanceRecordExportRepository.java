package nts.uk.ctx.at.function.infra.repository.attendancerecord.export;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.tuple.MutablePair;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExport;
import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExportGetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExportRepository;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnmtRptWkAtdOutframe;
/**
 * The Class JpaAttendanceRecordExportRepository.
 * 
 * @author NWS_QUANGNT
 */
@Stateless
public class JpaAttendanceRecordExportRepository extends JpaRepository implements AttendanceRecordExportRepository {

	static final long UPPER_POSITION = 1;
	static final long LOWER_POSITION = 2;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.
	 * AttendanceRecordExportRepository#getAllAttendanceRecordExportDaily(java.
	 * lang.String, long)
	 */
	@Override
	public List<AttendanceRecordExport> getAllAttendanceRecordExportDaily(String layoutId) {

		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KfnmtRptWkAtdOutframe> cq = criteriaBuilder.createQuery(KfnmtRptWkAtdOutframe.class);
		Root<KfnmtRptWkAtdOutframe> root = cq.from(KfnmtRptWkAtdOutframe.class);

		// build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();

//		predicates.add(criteriaBuilder.equal(root.get(KfnmtRptWkAtdOutframe_.id).get(KfnmtRptWkAtdOutframePK_.layoutId), layoutId));
//		predicates.add(criteriaBuilder.equal(root.get(KfnmtRptWkAtdOutframe_.id).get(KfnmtRptWkAtdOutframePK_.outputAtr), 1));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KfnmtRptWkAtdOutframe> entityList = em.createQuery(cq).getResultList();

		List<AttendanceRecordExport> domainList = domainsFrom(entityList);

		return domainList.stream().filter(Objects::nonNull).collect(Collectors.toList());
	}

	/**
	 *
	 * @param entityList
	 * @return
	 */
	private List<AttendanceRecordExport> domainsFrom(List<KfnmtRptWkAtdOutframe> entityList) {
		List<AttendanceRecordExport> domainList = new ArrayList<>();

		Map<Long, MutablePair<KfnmtRptWkAtdOutframe,KfnmtRptWkAtdOutframe>> map = new HashMap<>();

		for (KfnmtRptWkAtdOutframe item : entityList) {
			boolean isNew = false;
			long key = item.getId().getColumnIndex();
			MutablePair<KfnmtRptWkAtdOutframe, KfnmtRptWkAtdOutframe> exist = map.get(key);
			if (exist == null) {
				exist = new MutablePair<>();
				isNew = true;
			}

			if (item.getId().getPosition() == LOWER_POSITION) {
				exist.setRight(item);
			} else if (item.getId().getPosition() == UPPER_POSITION) {
				exist.setLeft(item);
			}

			if (isNew)
				map.put(key, exist);
		}

		map.forEach((k, v) -> {
			AttendanceRecordExport domain = new AttendanceRecordExport();
			domain= toDomain(v.left,v.right);
			domainList.add(domain);
		});

		return domainList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.
	 * AttendanceRecordExportRepository#getAllAttendanceRecordExportMonthly(java
	 * .lang.String, long)
	 */
	@Override
	public List<AttendanceRecordExport> getAllAttendanceRecordExportMonthly(String layoutId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KfnmtRptWkAtdOutframe> cq = criteriaBuilder.createQuery(KfnmtRptWkAtdOutframe.class);
		Root<KfnmtRptWkAtdOutframe> root = cq.from(KfnmtRptWkAtdOutframe.class);

		// build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();

//		predicates.add(criteriaBuilder.equal(root.get(KfnmtRptWkAtdOutframe_.id).get(KfnmtRptWkAtdOutframePK_.layoutId), layoutId));
//		predicates.add(criteriaBuilder.equal(root.get(KfnmtRptWkAtdOutframe_.id).get(KfnmtRptWkAtdOutframePK_.outputAtr), 2));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KfnmtRptWkAtdOutframe> entityList = em.createQuery(cq).getResultList();

//		Map<Long, MutablePair<KfnmtRptWkAtdOutframe,KfnmtRptWkAtdOutframe>> map = new HashMap<>();

		List<AttendanceRecordExport> domainList = domainsFrom(entityList);

		return domainList.stream().filter(Objects::nonNull).collect(Collectors.toList());
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
	public void deleteAttendanceRecord(String layoutId) {
		List<KfnmtRptWkAtdOutframe> items = this.findAllAttendanceRecord(layoutId);
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
	public AttendanceRecordExport toDomain(KfnmtRptWkAtdOutframe upperEntity, KfnmtRptWkAtdOutframe lowerEntity) {

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
	private List<KfnmtRptWkAtdOutframe> findAllAttendanceRecord(String layoutId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KfnmtRptWkAtdOutframe> criteriaQuery = criteriaBuilder.createQuery(KfnmtRptWkAtdOutframe.class);
		Root<KfnmtRptWkAtdOutframe> root = criteriaQuery.from(KfnmtRptWkAtdOutframe.class);

		// Build query
		criteriaQuery.select(root);

		// create condition
		List<Predicate> predicates = new ArrayList<>();
//		predicates.add(criteriaBuilder.equal(root.get(KfnmtRptWkAtdOutframe_.id).get(KfnmtRptWkAtdOutframePK_.layoutId), layoutId));


		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KfnmtRptWkAtdOutframe> kfnstAttndRecs = em.createQuery(criteriaQuery).getResultList();
		return kfnstAttndRecs.isEmpty() ? new ArrayList<>() : kfnstAttndRecs;
	}

	/**
	 * Removes the all attnd rec.
	 *
	 * @param items
	 *            the items
	 */
	public void removeAllAttndRec(List<KfnmtRptWkAtdOutframe> items) {
		if (!items.isEmpty()) {
			this.commandProxy().removeAll(items);
			this.getEntityManager().flush();
		}

	}

}
