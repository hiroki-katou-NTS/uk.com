/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.plannedyearholiday.frame;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrame;
import nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameRepository;
import nts.uk.ctx.at.schedule.infra.entity.plannedyearholiday.frame.KscstPlanYearHdFrame;
import nts.uk.ctx.at.schedule.infra.entity.plannedyearholiday.frame.KscstPlanYearHdFramePK;
import nts.uk.ctx.at.schedule.infra.entity.plannedyearholiday.frame.KscstPlanYearHdFramePK_;
import nts.uk.ctx.at.schedule.infra.entity.plannedyearholiday.frame.KscstPlanYearHdFrame_;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaPlanYearHolidayFrameRepository.
 */
@Stateless
public class JpaPlanYearHolidayFrameRepository extends JpaRepository
	implements PlanYearHolidayFrameRepository {

	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameRepository#findPlanYearHolidayFrame(nts.uk.ctx.at.shared.dom.common.CompanyId, int)
	 */
	@Override
	public Optional<PlanYearHolidayFrame> findPlanYearHolidayFrame(CompanyId companyId, int planYearHdFrNo) {
		return this.queryProxy()
				.find(new KscstPlanYearHdFramePK(companyId.v(), (short) planYearHdFrNo), KscstPlanYearHdFrame.class)
				.map(e -> this.toDomain(e));
	}

	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameRepository#update(nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrame)
	 */
	@Override
	public void update(PlanYearHolidayFrame planYearHolidayFrame) {
		this.commandProxy().update(this.toEntity(planYearHolidayFrame));
	}
	

	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameRepository#getAllPlanYearHolidayFrame(java.lang.String)
	 */
	@Override
	public List<PlanYearHolidayFrame> getAllPlanYearHolidayFrame(String companyId) {
		
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KscstPlanYearHdFrame> cq = criteriaBuilder
			.createQuery(KscstPlanYearHdFrame.class);

		// root data
		Root<KscstPlanYearHdFrame> root = cq.from(KscstPlanYearHdFrame.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// eq company id
		lstpredicateWhere
			.add(criteriaBuilder.equal(root.get(KscstPlanYearHdFrame_.kscstPlanYearHdFramePK)
				.get(KscstPlanYearHdFramePK_.cid), companyId));
		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// creat query
		TypedQuery<KscstPlanYearHdFrame> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(category -> toDomain(category))
			.collect(Collectors.toList());
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kscst plan year hd frame
	 */
	private KscstPlanYearHdFrame toEntity(PlanYearHolidayFrame domain){
		KscstPlanYearHdFrame entity = new KscstPlanYearHdFrame();
		domain.saveToMemento(new JpaPlanYearHolidayFrameSetMemento(entity));
		return entity;
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the plan year holiday frame
	 */
	private PlanYearHolidayFrame toDomain(KscstPlanYearHdFrame entity){
		return new PlanYearHolidayFrame(new JpaPlanYearHolidayFrameGetMemento(entity));
	}
}
