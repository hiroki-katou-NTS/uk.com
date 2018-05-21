package nts.uk.ctx.at.function.infra.repository.attendancerecord.export;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExport;
import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExportGetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExportRepository;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRec;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRecPK_;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRec_;

@Stateless
public class JpaAttendanceRecordExportRepository extends JpaRepository implements AttendanceRecordExportRepository {

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
			if (!findInList(domainList, item1))
				// if not exist, toDomain
				entityList.forEach(item2 -> {
					// find if the same columnIndex
					if (item1.getId().getColumnIndex() == item2.getId().getColumnIndex()
							&& item1.getId().getPosition() != item2.getId().getPosition()) {
						// toDomain
						AttendanceRecordExport domain = this.toDomain(item1, item2);
						// Add domain to list
						domainList.add(domain);
					}
				});
		});

		return domainList.stream().filter(item -> item != null).collect(Collectors.toList());
	}

	public Boolean findInList(List<AttendanceRecordExport> list, KfnstAttndRec item) {

		for (AttendanceRecordExport e : list) {
			if (e.getColumnIndex() == (int) item.getId().getColumnIndex())
				return true;
		}

		return false;
	}

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
			if (!findInList(domainList, item1))
				// if not exist, toDomain
				entityList.forEach(item2 -> {
					// find if the same columnIndex
					if (item1.getId().getColumnIndex() == item2.getId().getColumnIndex()
							&& item1.getId().getPosition() != item2.getId().getPosition()) {
						// toDomain
						AttendanceRecordExport domain = this.toDomain(item1, item2);
						// Add domain to list
						domainList.add(domain);
					}
				});
		});

		return domainList.stream().filter(item -> item != null).collect(Collectors.toList());
	}

	@Override
	public void updateAttendanceRecordExport(AttendanceRecordExport attendanceRecordExport) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addAttendanceRecordExport(AttendanceRecordExport attendanceRecordExport) {
		// TODO Auto-generated method stub

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

}
