/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.outsideot.breakdown.attendance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.outsideot.breakdown.attendance.OutsideOTBRDItemAtenRepository;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.attendance.KshstOverTimeBrdAten;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.attendance.KshstOverTimeBrdAtenPK;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.attendance.KshstOverTimeBrdAtenPK_;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.attendance.KshstOverTimeBrdAten_;

/**
 * The Class JpaOvertimeBRDItemAtenRepository.
 */
@Stateless
public class JpaOvertimeBRDItemAtenRepository extends JpaRepository implements OutsideOTBRDItemAtenRepository{

	@Override
	public void saveAll(List<Integer> attendanceItemIds, String companyId, int breakdownItemNo) {
		
		// entity add all
		List<KshstOverTimeBrdAten> entityAddAll = new ArrayList<>();
		
		// entity remove all
		List<KshstOverTimeBrdAten> entityRemoveAll = new ArrayList<>();
		
		// entity get all
		List<KshstOverTimeBrdAten> entityGetAll = this.findAtenById(companyId, breakdownItemNo);
				
		// to map entity
		Map<Integer, KshstOverTimeBrdAten> mapEntity = entityGetAll.stream()
				.collect(Collectors.toMap((entity) -> {
					return entity.getKshstOverTimeBrdAtenPK().getAttendanceItemId();
				}, Function.identity()));
		
		// check map entity add
		attendanceItemIds.forEach(itemId -> {
			if (!mapEntity.containsKey(itemId)) {
				entityAddAll.add(new KshstOverTimeBrdAten(
						new KshstOverTimeBrdAtenPK(companyId, breakdownItemNo, itemId)));
			}
		});
		
		// to map attendance item id
		Map<Integer, Integer> mapAttendanceItemId = attendanceItemIds.stream()
				.collect(Collectors.toMap((itemId) -> {
					return itemId;
				}, Function.identity()));
		
		entityGetAll.forEach(entityItem->{
			if(!mapAttendanceItemId.containsKey(entityItem.getKshstOverTimeBrdAtenPK().getAttendanceItemId())){
				entityRemoveAll.add(mapEntity.get(entityItem.getKshstOverTimeBrdAtenPK().getAttendanceItemId()));
			}
		});
		
		// insert all entity
		this.commandProxy().insertAll(entityAddAll);
		
		// remove all entity
		this.commandProxy().removeAll(entityRemoveAll);
	}

	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @param breakdownItemNo the breakdown item no
	 * @return the list
	 */
	@Override
	public List<Integer> findAll(String companyId, int breakdownItemNo) {
		return this.findAtenById(companyId, breakdownItemNo).stream()
				.map(entity -> entity.getKshstOverTimeBrdAtenPK().getAttendanceItemId())
				.collect(Collectors.toList());
	}

	/**
	 * Find aten by id.
	 *
	 * @param companyId the company id
	 * @param breakdownItemNo the breakdown item no
	 * @return the list
	 */
	private List<KshstOverTimeBrdAten> findAtenById(String companyId, int breakdownItemNo){
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSHST_OVER_TIME_BRD_ATEN (KshstOverTimeBrdAten SQL)
		CriteriaQuery<KshstOverTimeBrdAten> cq = criteriaBuilder.createQuery(KshstOverTimeBrdAten.class);

		// root data
		Root<KshstOverTimeBrdAten> root = cq.from(KshstOverTimeBrdAten.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KshstOverTimeBrdAten_.kshstOverTimeBrdAtenPK).get(KshstOverTimeBrdAtenPK_.cid),
				companyId));

		// equal breakdown item no
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KshstOverTimeBrdAten_.kshstOverTimeBrdAtenPK)
						.get(KshstOverTimeBrdAtenPK_.brdItemNo), breakdownItemNo));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by id asc
		cq.orderBy(criteriaBuilder.asc(root.get(KshstOverTimeBrdAten_.kshstOverTimeBrdAtenPK)
				.get(KshstOverTimeBrdAtenPK_.attendanceItemId)));

		// create query
		TypedQuery<KshstOverTimeBrdAten> query = em.createQuery(cq);
		
		// exclude select
		return query.getResultList();

	}
	
}
