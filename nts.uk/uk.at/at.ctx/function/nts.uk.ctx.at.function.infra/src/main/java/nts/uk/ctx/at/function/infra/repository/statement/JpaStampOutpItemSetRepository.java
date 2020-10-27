/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.repository.statement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import nts.arc.error.BusinessException;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.statement.StampingOutputItemSet;
import nts.uk.ctx.at.function.dom.statement.StampingOutputItemSetRepository;
import nts.uk.ctx.at.function.infra.entity.statement.KfnmtStampOutpItem;
import nts.uk.ctx.at.function.infra.entity.statement.KfnmtStampOutpItemPK;
import nts.uk.ctx.at.function.infra.entity.statement.KfnmtStampOutpItemPK_;
import nts.uk.ctx.at.function.infra.entity.statement.KfnmtStampOutpItem_;

/**
 * The Class JpaKfnmtStampOutpItemRepository.
 */
@Stateless
public class JpaStampOutpItemSetRepository extends JpaRepository implements StampingOutputItemSetRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.statement.StampingOutputItemSetRepository#getByCidAndCode(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<StampingOutputItemSet> getByCidAndCode(String companyId, String code) {
		KfnmtStampOutpItemPK primaryKey = new KfnmtStampOutpItemPK(companyId, code);
		return this.queryProxy().find(primaryKey, KfnmtStampOutpItem.class).map(entity -> this.toDomain(entity));
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.statement.StampingOutputItemSetRepository#getByCid(java.lang.String)
	 */
	@Override
	public List<StampingOutputItemSet> getByCid(String companyId) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		// Create builder
		CriteriaBuilder builder = em.getCriteriaBuilder();

		// Create query
		CriteriaQuery<KfnmtStampOutpItem> cq = builder.createQuery(KfnmtStampOutpItem.class);

		// From table
		Root<KfnmtStampOutpItem> root = cq.from(KfnmtStampOutpItem.class);

		// Add where condition
		cq.where(builder.equal(root.get(KfnmtStampOutpItem_.id).get(KfnmtStampOutpItemPK_.cid),companyId));
		cq.orderBy(builder.asc(root.get(KfnmtStampOutpItem_.id).get(KfnmtStampOutpItemPK_.cid)));
		// Get results
		List<KfnmtStampOutpItem> results = em.createQuery(cq).getResultList();

		// Check empty
		if (CollectionUtil.isEmpty(results)) {
			return Collections.emptyList();
		}
		
		// Return
		return results.stream().map(entity -> new StampingOutputItemSet(new JpaStampOutpItemSetGetMemento(entity)))
								.collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.statement.StampingOutputItemSetRepository#add(nts.uk.ctx.at.function.dom.statement.StampingOutputItemSet)
	 */
	@Override
	public void add(StampingOutputItemSet domain) {
		Optional<StampingOutputItemSet> duplicateDomain = getByCidAndCode(domain.getCompanyID().v(),
				domain.getStampOutputSetCode().v());
		if (duplicateDomain.isPresent()){
			throw new BusinessException("Msg_3");
		}
		this.commandProxy().insert(this.toEntity(domain));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.statement.StampingOutputItemSetRepository#update(nts.uk.ctx.at.function.dom.statement.StampingOutputItemSet)
	 */
	@Override
	public void update(StampingOutputItemSet domain) {
		this.commandProxy().update(this.toEntity(domain));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.statement.StampingOutputItemSetRepository#removeByCidAndCode(java.lang.String, java.lang.String)
	 */
	@Override
	public void removeByCidAndCode(String companyId, String code) {
		KfnmtStampOutpItemPK primaryKey = new KfnmtStampOutpItemPK(companyId, code);
		this.commandProxy().remove(KfnmtStampOutpItem.class, primaryKey);
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the stamping output item set
	 */
	private StampingOutputItemSet toDomain(KfnmtStampOutpItem entity) {
		return new StampingOutputItemSet(new JpaStampOutpItemSetGetMemento(entity));
		
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kfnmt stamp outp item set
	 */
	private KfnmtStampOutpItem toEntity(StampingOutputItemSet domain) {
		KfnmtStampOutpItem entity = new KfnmtStampOutpItem();
		domain.saveToMemento(new JpaStampOutpItemSetSetMemento(entity));
		return entity;
	}
}
