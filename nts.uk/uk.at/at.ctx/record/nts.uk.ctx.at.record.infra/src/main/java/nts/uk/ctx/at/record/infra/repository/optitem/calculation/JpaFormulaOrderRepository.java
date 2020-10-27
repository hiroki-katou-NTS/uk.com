/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem.calculation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.disporder.KrcmtAnyfSort;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.disporder.KrcmtAnyfSortPK_;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.disporder.KrcmtAnyfSort_;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.disporder.FormulaDispOrder;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.disporder.FormulaDispOrderRepository;

/**
 * The Class JpaFormulaOrderRepository.
 */
@Stateless
public class JpaFormulaOrderRepository extends JpaRepository implements FormulaDispOrderRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.disporder.
	 * FormulaDispOrderRepository#create(java.util.List)
	 */
	@Override
	public void create(List<FormulaDispOrder> listFormula) {
		listFormula.stream().forEach(item -> {
			KrcmtAnyfSort entity = new KrcmtAnyfSort();

			item.saveToMemento(new JpaFormulaDispOrderSetMemento(entity));

			this.commandProxy().insert(entity);
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.disporder.
	 * FormulaDispOrderRepository#remove(java.lang.String, java.lang.String)
	 */
	@Override
	public void remove(String comId, Integer optItemNo) {
		List<KrcmtAnyfSort> entities = this.findByItemNo(comId, optItemNo);

		if (CollectionUtil.isEmpty(entities)) {
			return;
		}

		this.commandProxy().removeAll(entities);
		this.getEntityManager().flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.disporder.
	 * FormulaDispOrderRepository#findByOptItemNo(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<FormulaDispOrder> findByOptItemNo(String companyId, Integer optItemNo) {
		List<KrcmtAnyfSort> entities = this.findByItemNo(companyId, optItemNo);

		if (CollectionUtil.isEmpty(entities)) {
			return Collections.emptyList();
		}

		return entities.stream()
				.map(item -> new FormulaDispOrder(new JpaFormulaDispOrderGetMemento(item)))
				.collect(Collectors.toList());
	}
	
	/**
	 * Find all.
	 * @param companyId the company id
	 * @return the list
	 */
	@Override
	public List<FormulaDispOrder> findAll(String companyId) {
		List<KrcmtAnyfSort> entities = this._findAll(companyId);

		if (CollectionUtil.isEmpty(entities)) {
			return Collections.emptyList();
		}

		return entities.stream()
				.map(item -> new FormulaDispOrder(new JpaFormulaDispOrderGetMemento(item)))
				.collect(Collectors.toList());
	}

	/**
	 * Find by item no.
	 *
	 * @param comId
	 *            the com id
	 * @param optItemNo
	 *            the opt item no
	 * @return the list
	 */
	private List<KrcmtAnyfSort> findByItemNo(String comId, Integer optItemNo) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		// Create builder
		CriteriaBuilder builder = em.getCriteriaBuilder();

		// Create query
		CriteriaQuery<KrcmtAnyfSort> cq = builder.createQuery(KrcmtAnyfSort.class);

		// From table
		Root<KrcmtAnyfSort> root = cq.from(KrcmtAnyfSort.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Add where condition
		predicateList.add(builder.equal(root.get(KrcmtAnyfSort_.krcmtAnyfSortPK)
				.get(KrcmtAnyfSortPK_.cid), comId));
		predicateList.add(builder.equal(root.get(KrcmtAnyfSort_.krcmtAnyfSortPK)
				.get(KrcmtAnyfSortPK_.optionalItemNo), optItemNo));
		cq.where(predicateList.toArray(new Predicate[] {}));

		// Get results
		return em.createQuery(cq).getResultList();
	}

	/**
	 * Find all.
	 * @param comId the company id
	 * @return the list
	 */
	private List<KrcmtAnyfSort> _findAll(String comId) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		// Create builder
		CriteriaBuilder builder = em.getCriteriaBuilder();

		// Create query
		CriteriaQuery<KrcmtAnyfSort> cq = builder.createQuery(KrcmtAnyfSort.class);

		// From table
		Root<KrcmtAnyfSort> root = cq.from(KrcmtAnyfSort.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Add where condition
		predicateList.add(builder.equal(root.get(KrcmtAnyfSort_.krcmtAnyfSortPK)
				.get(KrcmtAnyfSortPK_.cid), comId));
		cq.where(predicateList.toArray(new Predicate[] {}));

		// Get results
		return em.createQuery(cq).getResultList();
	}
}
