package nts.uk.ctx.at.function.infra.repository.attendancerecord;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRecPK;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnstAttndRecItem;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnstAttndRecItem_;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

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
	public List<KfnstAttndRecItem> findAttendanceRecordItems(KfnstAttndRecPK kfnstAttndRecPK){
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KfnstAttndRecItem> criteriaQuery = criteriaBuilder.createQuery(KfnstAttndRecItem.class);
		Root<KfnstAttndRecItem> root = criteriaQuery.from(KfnstAttndRecItem.class);

		// Build query
		criteriaQuery.select(root);

		// create condition
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRecItem_.cid), kfnstAttndRecPK.getCid() ));
		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRecItem_.exportCd),	kfnstAttndRecPK.getExportCd()));
		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRecItem_.columnIndex),kfnstAttndRecPK.getColumnIndex()));
		predicates.add(
				criteriaBuilder.equal(root.get(KfnstAttndRecItem_.position), kfnstAttndRecPK.getPosition()));
		predicates.add(
				criteriaBuilder.equal(root.get(KfnstAttndRecItem_.outputAtr), kfnstAttndRecPK.getOutputAtr()));

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KfnstAttndRecItem> kfnstAttndRecItems = em.createQuery(criteriaQuery).getResultList();
		return kfnstAttndRecItems;
	}

	/**
	 * find all AttendanceRecordItem
	 *
	 * @param companyId
	 * @param exportSettingCode
	 * @return
	 */
	List<KfnstAttndRecItem> findAllAttendanceRecordItem(String companyId, ExportSettingCode exportSettingCode) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KfnstAttndRecItem> criteriaQuery = criteriaBuilder.createQuery(KfnstAttndRecItem.class);
		Root<KfnstAttndRecItem> root = criteriaQuery.from(KfnstAttndRecItem.class);

		// Build query
		criteriaQuery.select(root);

		// create condition
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRecItem_.cid), companyId));
		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRecItem_.exportCd),	exportSettingCode.v()));

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KfnstAttndRecItem> kfnstAttndRecItems = em.createQuery(criteriaQuery).getResultList();
		return kfnstAttndRecItems;
	}


	/**
	 * Removes the all attnd rec item.
	 *
	 * @param listKfnstAttndRecItem
	 *            the list kfnst attnd rec item
	 */
	public void removeAllAttndRecItem(List<KfnstAttndRecItem> listKfnstAttndRecItem) {
		if (!listKfnstAttndRecItem.isEmpty()) {
			this.commandProxy().removeAll(listKfnstAttndRecItem);
			this.getEntityManager().flush();
		}

	}

}
