package nts.uk.ctx.at.function.infra.repository.attendancerecord;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnmtRptWkAtdOutframePK;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnmtRptWkAtdOutatd;

/**
 * @author tuannt-nws
 *
 */
public abstract class JpaAttendanceRecordRepository extends JpaRepository {
	
	/**
	 * Find attendance record items.
	 *
	 * @param kfnstAttndRecPK the kfnst attnd rec PK
	 * @return the list
	 */
	public List<KfnmtRptWkAtdOutatd> findAttendanceRecordItems(KfnmtRptWkAtdOutframePK kfnstAttndRecPK){
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KfnmtRptWkAtdOutatd> criteriaQuery = criteriaBuilder.createQuery(KfnmtRptWkAtdOutatd.class);
		Root<KfnmtRptWkAtdOutatd> root = criteriaQuery.from(KfnmtRptWkAtdOutatd.class);

		// Build query
		criteriaQuery.select(root);

		// create condition
		List<Predicate> predicates = new ArrayList<>();
//		predicates.add(criteriaBuilder.equal(root.get(KfnmtRptWkAtdOutatd_.layoutId), kfnstAttndRecPK.getLayoutId()));
//		predicates.add(criteriaBuilder.equal(root.get(KfnmtRptWkAtdOutatd_.columnIndex),kfnstAttndRecPK.getColumnIndex()));
//		predicates.add(
//				criteriaBuilder.equal(root.get(KfnmtRptWkAtdOutatd_.position), kfnstAttndRecPK.getPosition()));
//		predicates.add(
//				criteriaBuilder.equal(root.get(KfnmtRptWkAtdOutatd_.outputAtr), kfnstAttndRecPK.getOutputAtr()));

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KfnmtRptWkAtdOutatd> kfnstAttndRecItems = em.createQuery(criteriaQuery).getResultList();
		return kfnstAttndRecItems;
	}

	/**
	 * find all AttendanceRecordItem
	 *
	 * @param companyId
	 * @param exportSettingCode
	 * @return
	 */
	List<KfnmtRptWkAtdOutatd> findAllAttendanceRecordItem(String layoutId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KfnmtRptWkAtdOutatd> criteriaQuery = criteriaBuilder.createQuery(KfnmtRptWkAtdOutatd.class);
		Root<KfnmtRptWkAtdOutatd> root = criteriaQuery.from(KfnmtRptWkAtdOutatd.class);

		// Build query
		criteriaQuery.select(root);

		// create condition
		List<Predicate> predicates = new ArrayList<>();
//		predicates.add(criteriaBuilder.equal(root.get(KfnmtRptWkAtdOutatd_.layoutId), layoutId));

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KfnmtRptWkAtdOutatd> kfnstAttndRecItems = em.createQuery(criteriaQuery).getResultList();
		return kfnstAttndRecItems;
	}


	/**
	 * Removes the all attnd rec item.
	 *
	 * @param listKfnstAttndRecItem
	 *            the list kfnst attnd rec item
	 */
	public void removeAllAttndRecItem(List<KfnmtRptWkAtdOutatd> listKfnstAttndRecItem) {
		if (!listKfnstAttndRecItem.isEmpty()) {
			this.commandProxy().removeAll(listKfnstAttndRecItem);
			this.getEntityManager().flush();
		}

	}

}
