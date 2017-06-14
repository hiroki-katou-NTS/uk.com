/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.jobtitle;

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
import nts.uk.ctx.basic.dom.company.organization.jobtitle.SequenceMaster;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.SequenceMasterRepository;
import nts.uk.ctx.basic.infra.entity.company.organization.jobtitle.CsqmtSequenceMaster;
import nts.uk.ctx.basic.infra.entity.company.organization.jobtitle.CsqmtSequenceMasterPK_;
import nts.uk.ctx.basic.infra.entity.company.organization.jobtitle.CsqmtSequenceMaster_;

/**
 * The Class JpaSequenceMasterRepository.
 */
@Stateless
public class JpaSequenceMasterRepository extends JpaRepository implements SequenceMasterRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.jobtitle.
	 * SequenceMasterRepository#findAll(java.lang.String, java.lang.String)
	 */
	@Override
	public List<SequenceMaster> findAll(String companyId, String sequenceCode) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CsqmtSequenceMaster> cq = cb.createQuery(CsqmtSequenceMaster.class);
		Root<CsqmtSequenceMaster> root = cq.from(CsqmtSequenceMaster.class);

		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(
				cb.equal(root.get(CsqmtSequenceMaster_.csqmtSequenceMasterPK).get(CsqmtSequenceMasterPK_.companyId),
						sequenceCode));
		predicateList.add(
				cb.equal(root.get(CsqmtSequenceMaster_.csqmtSequenceMasterPK).get(CsqmtSequenceMasterPK_.sequenceCode),
						companyId));

		cq.orderBy(
				cb.asc(root.get(CsqmtSequenceMaster_.csqmtSequenceMasterPK).get(CsqmtSequenceMasterPK_.sequenceCode)));
		// TODO: EA: sort by positionCode?

		cq.where(predicateList.toArray(new Predicate[] {}));

		return em.createQuery(cq).getResultList().stream().map(item -> this.toDomain(item))
				.collect(Collectors.toList());
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the csqmt sequence master
	 */
	private CsqmtSequenceMaster toEntity(SequenceMaster domain) {
		CsqmtSequenceMaster entity = new CsqmtSequenceMaster();
		domain.saveToMemento(new JpaSequenceMasterSetMemento(entity));
		return entity;
	}

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the sequence master
	 */
	private SequenceMaster toDomain(CsqmtSequenceMaster entity) {
		return new SequenceMaster(new JpaSequenceMasterGetMemento(entity));
	}

}
