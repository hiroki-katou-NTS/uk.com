/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.predset;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetRepository;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtPredTimeSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtPredTimeSetPK_;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtPredTimeSet_;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtWorkTimeSheetSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtWorkTimeSheetSetPK_;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtWorkTimeSheetSet_;

/**
 * The Class JpaPredetemineTimeSetRepository.
 */
@Stateless
public class JpaPredetemineTimeSetRepository extends JpaRepository implements PredetemineTimeSetRepository {

	@Override
	public PredetemineTimeSet findByWorkTimeCode(String companyId, String workTimeCode) {
		// get entity manager
		EntityManager em1 = this.getEntityManager();
		CriteriaBuilder criteriaBuilder1 = em1.getCriteriaBuilder();

		EntityManager em2 = this.getEntityManager();
		CriteriaBuilder criteriaBuilder2 = em2.getCriteriaBuilder();

		CriteriaQuery<KshmtPredTimeSet> cq = criteriaBuilder1.createQuery(KshmtPredTimeSet.class);
		Root<KshmtPredTimeSet> root = cq.from(KshmtPredTimeSet.class);

		CriteriaQuery<KshmtWorkTimeSheetSet> cq2 = criteriaBuilder2.createQuery(KshmtWorkTimeSheetSet.class);
		Root<KshmtWorkTimeSheetSet> root2 = cq2.from(KshmtWorkTimeSheetSet.class);

		cq.select(root);
		cq2.select(root2);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere.add(criteriaBuilder1
				.equal(root.get(KshmtPredTimeSet_.kshmtPredTimeSetPK).get(KshmtPredTimeSetPK_.cid), companyId));

		lstpredicateWhere.add(criteriaBuilder1.equal(
				root.get(KshmtPredTimeSet_.kshmtPredTimeSetPK).get(KshmtPredTimeSetPK_.worktimeCd), workTimeCode));

		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		KshmtPredTimeSet kwtstWorkTimeSet = em1.createQuery(cq).getSingleResult();

		// +++++++++++++++++++++++++++++++++++
		List<Predicate> lstpredicateWhere2 = new ArrayList<>();
		lstpredicateWhere2.add(criteriaBuilder2.equal(
				root2.get(KshmtWorkTimeSheetSet_.kshmtWorkTimeSheetSetPK).get(KshmtWorkTimeSheetSetPK_.cid),
				companyId));

		lstpredicateWhere2.add(criteriaBuilder2.equal(
				root2.get(KshmtWorkTimeSheetSet_.kshmtWorkTimeSheetSetPK).get(KshmtWorkTimeSheetSetPK_.worktimeCd),
				workTimeCode));

		cq2.where(lstpredicateWhere2.toArray(new Predicate[] {}));

		List<KshmtWorkTimeSheetSet> lstKshmtWorkTimeSheetSet = em2.createQuery(cq2).getResultList();

		return new PredetemineTimeSet(new JpaPredetemineTimeGetMemento(kwtstWorkTimeSet, lstKshmtWorkTimeSheetSet));
	}

}
