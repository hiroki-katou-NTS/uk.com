/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.ot.frame;

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

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository;
import nts.uk.ctx.at.shared.infra.entity.ot.frame.KshstOvertimeFrame;
import nts.uk.ctx.at.shared.infra.entity.ot.frame.KshstOvertimeFramePK;
import nts.uk.ctx.at.shared.infra.entity.ot.frame.KshstOvertimeFramePK_;
import nts.uk.ctx.at.shared.infra.entity.ot.frame.KshstOvertimeFrame_;
import nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes.KshstTotalConditionPK_;

/**
 * The Class JpaOvertimeWorkFrameRepository.
 */
@Stateless
public class JpaOvertimeWorkFrameRepository extends JpaRepository
	implements OvertimeWorkFrameRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.classification.ClassificationRepository#findClassification(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<OvertimeWorkFrame> findOvertimeWorkFrame(CompanyId companyId, int planYearHdFrNo) {
		return this.queryProxy()
				.find(new KshstOvertimeFramePK(companyId.v(), (short) planYearHdFrNo), KshstOvertimeFrame.class)
				.map(e -> this.toDomain(e));
	}
	
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameRepository#add(nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrame)
	 */
	@Override
	public void add(OvertimeWorkFrame planYearHolidayFrame) {
		this.commandProxy().insert(this.toEntity(planYearHolidayFrame));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.category.
	 * ManagementCategoryRepository#update(nts.uk.ctx.basic.dom.company.
	 * organization.category.ManagementCategory)
	 */
	@Override
	public void update(OvertimeWorkFrame planYearHolidayFrame) {
		this.commandProxy().update(this.toEntity(planYearHolidayFrame));
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository#getAllOvertimeWorkFrame(java.lang.String)
	 */
	@Override
	public List<OvertimeWorkFrame> getAllOvertimeWorkFrame(String companyId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshstOvertimeFrame> cq = criteriaBuilder
			.createQuery(KshstOvertimeFrame.class);

		val x = KshstTotalConditionPK_.cid;
		
		// root data
		Root<KshstOvertimeFrame> root = cq.from(KshstOvertimeFrame.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// eq company id
		lstpredicateWhere
			.add(criteriaBuilder.equal(root.get(KshstOvertimeFrame_.kshstOvertimeFramePK)
				.get(KshstOvertimeFramePK_.cid), companyId));
		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// creat query
		TypedQuery<KshstOvertimeFrame> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(category -> toDomain(category))
			.collect(Collectors.toList());
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshst overtime frame
	 */
	private KshstOvertimeFrame toEntity(OvertimeWorkFrame domain){
		KshstOvertimeFrame entity = new KshstOvertimeFrame();
		domain.saveToMemento(new JpaOvertimeWorkFrameSetMemento(entity));
		return entity;
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the overtime work frame
	 */
	private OvertimeWorkFrame toDomain(KshstOvertimeFrame entity){
		return new OvertimeWorkFrame(new JpaOvertimeWorkFrameGetMemento(entity));
	}
}
