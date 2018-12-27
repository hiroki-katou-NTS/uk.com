/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.shortworktime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemRepository;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistoryItem;
import nts.uk.ctx.at.shared.infra.entity.shortworktime.BshmtWorktimeHistItem;
import nts.uk.ctx.at.shared.infra.entity.shortworktime.BshmtWorktimeHistItemPK;
import nts.uk.ctx.at.shared.infra.entity.shortworktime.BshmtWorktimeHistItemPK_;
import nts.uk.ctx.at.shared.infra.entity.shortworktime.BshmtWorktimeHistItem_;

/**
 * The Class JpaSWorkTimeHistItemRepository.
 */
@RequestScoped
public class JpaSWorkTimeHistItemRepository extends JpaRepository implements SWorkTimeHistItemRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemRepository#add(
	 * nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistoryItem)
	 */
	@Override
	@Transactional
	public void add(ShortWorkTimeHistoryItem domain) {
		this.commandProxy().insert(this.toEntity(domain));
		this.getEntityManager().flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemRepository#update
	 * (nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistoryItem)
	 */
	@Override
	@Transactional
	public void update(ShortWorkTimeHistoryItem domain) {
//		this.delete(domain.getEmployeeId(), domain.getHistoryId());
//		
//		this.add(domain);
		this.commandProxy().update(toEntity(domain));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemRepository#
	 * findByKey(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public Optional<ShortWorkTimeHistoryItem> findByKey(String empId, String histId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<BshmtWorktimeHistItem> query = builder.createQuery(BshmtWorktimeHistItem.class);
        Root<BshmtWorktimeHistItem> root = query.from(BshmtWorktimeHistItem.class);
        
        List<Predicate> predicateList = new ArrayList<>();
        
        predicateList.add(builder.equal(root.get(BshmtWorktimeHistItem_.bshmtWorktimeHistItemPK)
        		.get(BshmtWorktimeHistItemPK_.sid), empId));
        predicateList.add(builder.equal(root.get(BshmtWorktimeHistItem_.bshmtWorktimeHistItemPK)
        		.get(BshmtWorktimeHistItemPK_.histId), histId));
        
        query.where(predicateList.toArray(new Predicate[]{}));
        
		return em.createQuery(query).getResultList().stream()
				.map(entity -> new ShortWorkTimeHistoryItem(new JpaSWorkTimeHistItemGetMemento(entity)))
				.findFirst();
	}
	
	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the bshmt worktime hist item
	 */
	private BshmtWorktimeHistItem toEntity(ShortWorkTimeHistoryItem domain) {
		BshmtWorktimeHistItem entity = this.queryProxy()
				.find(new BshmtWorktimeHistItemPK(domain.getEmployeeId(), domain.getHistoryId()),
						BshmtWorktimeHistItem.class)
				.orElse(new BshmtWorktimeHistItem(new BshmtWorktimeHistItemPK()));
		JpaSWorkTimeHistItemSetMemento memento = new JpaSWorkTimeHistItemSetMemento(entity);
		domain.saveToMemento(memento);
		return entity;
	}

	@Override
	@Transactional
	public void delete(String sid, String hist) {
		BshmtWorktimeHistItemPK key = new BshmtWorktimeHistItemPK(sid, hist);
		this.commandProxy().remove(BshmtWorktimeHistItem.class,key);
		this.getEntityManager().flush();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemRepository#findByHistIds(java.util.List)
	 */
	@Override
	public List<ShortWorkTimeHistoryItem> findByHistIds(List<String> histIds) {
		// Check
		if(CollectionUtil.isEmpty(histIds)) {
			return Collections.emptyList();
		}
		
		EntityManager em = this.getEntityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<BshmtWorktimeHistItem> query = builder
				.createQuery(BshmtWorktimeHistItem.class);
		Root<BshmtWorktimeHistItem> root = query.from(BshmtWorktimeHistItem.class);
		
		List<BshmtWorktimeHistItem> result = new ArrayList<>();
		
		CollectionUtil.split(histIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			// Predicate where clause
			List<Predicate> predicateList = new ArrayList<>();
			predicateList.add(root.get(BshmtWorktimeHistItem_.bshmtWorktimeHistItemPK)
					.get(BshmtWorktimeHistItemPK_.histId).in(splitData));
			query.where(predicateList.toArray(new Predicate[] {}));
			
			result.addAll(em.createQuery(query).getResultList());
		});
		
		return result.stream().map(
				entity -> new ShortWorkTimeHistoryItem(new JpaSWorkTimeHistItemGetMemento(entity)))
				.collect(Collectors.toList());
	}

}
