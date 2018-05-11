/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.infra.repository.mailnoticeset;

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
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.env.dom.mailnoticeset.MailFunction;
import nts.uk.ctx.sys.env.dom.mailnoticeset.MailFunctionRepository;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.SevstMailFunction;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.SevstMailFunction_;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company.SevstMailDestinFuncPK_;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company.SevstMailDestinFunc_;

/**
 * The Class JpaMailFunctionRepository.
 */
@Stateless
public class JpaMailFunctionRepository extends JpaRepository implements MailFunctionRepository {

	private static final Integer TRUE_VAL = 1;
	private static final Integer FALSE_VAL = 0;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.env.dom.mailnoticeset.MailFunctionRepository#findAll(java.
	 * lang.Boolean)
	 */
	@Override
	public List<MailFunction> findAll(Boolean proprietySendMailSettingAtr) {
		List<MailFunction> lstMailFunction = new ArrayList<>();
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<SevstMailFunction> cq = criteriaBuilder.createQuery(SevstMailFunction.class);
		Root<SevstMailFunction> root = cq.from(SevstMailFunction.class);

		// Build query
		cq.select(root);

		// Add where conditions
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere.add(criteriaBuilder.equal(root.get(SevstMailFunction_.sendMailSetAtr),
				proprietySendMailSettingAtr ? TRUE_VAL : FALSE_VAL));
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
		cq.orderBy(criteriaBuilder.desc(root.get(SevstMailFunction_.sortOrder)));

		List<SevstMailFunction> listSevstMailFunction = em.createQuery(cq).getResultList();

		// Check exist
		if (!CollectionUtil.isEmpty(listSevstMailFunction)) {
			lstMailFunction = listSevstMailFunction.stream().map(item -> {
				return this.toDomain(item);
			}).collect(Collectors.toList());
		}
		// Return
		return lstMailFunction;
	}

	private MailFunction toDomain(SevstMailFunction entity) {
		return new MailFunction(new JpaMailFunctionGetMemento(entity));
	}

}
