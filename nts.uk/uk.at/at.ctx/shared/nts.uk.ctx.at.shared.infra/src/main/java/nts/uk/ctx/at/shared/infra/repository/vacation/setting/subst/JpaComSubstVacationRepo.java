/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.subst;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.subst.KsvstComSubstVacation;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.subst.KsvstComSubstVacation_;

/**
 * The Class JpaComSubstVacationRepo.
 */
@Stateless
public class JpaComSubstVacationRepo extends JpaRepository implements ComSubstVacationRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.subst.
	 * ComSubstVacationRepository#insert(nts.uk.ctx.at.shared.dom.vacation.
	 * setting.subst.ComSubstVacation)
	 */
	@Override
	public void insert(ComSubstVacation setting) {
		KsvstComSubstVacation entity = new KsvstComSubstVacation();

		setting.saveToMemento(new JpaComSubstVacationSetMemento(entity));

		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.subst.
	 * ComSubstVacationRepository#update(nts.uk.ctx.at.shared.dom.vacation.
	 * setting.subst.ComSubstVacation)
	 */
	@Override
	public void update(ComSubstVacation setting) {
		Optional<KsvstComSubstVacation> optEntity = this.queryProxy().find(setting.getCompanyId(),
				KsvstComSubstVacation.class);

		KsvstComSubstVacation entity = optEntity.get();

		setting.saveToMemento(new JpaComSubstVacationSetMemento(entity));

		this.commandProxy().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.subst.
	 * ComSubstVacationRepository#findById(java.lang.String)
	 */
	@Override
	public Optional<ComSubstVacation> findById(String companyId) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<KsvstComSubstVacation> cq = builder.createQuery(KsvstComSubstVacation.class);
		Root<KsvstComSubstVacation> root = cq.from(KsvstComSubstVacation.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		predicateList.add(builder.equal(root.get(KsvstComSubstVacation_.cid), companyId));

		cq.where(predicateList.toArray(new Predicate[] {}));

		List<KsvstComSubstVacation> results = em.createQuery(cq).getResultList();

		if (CollectionUtil.isEmpty(results)) {
			return Optional.empty();
		}

		return Optional.of(new ComSubstVacation(new JpaComSubstVacationGetMemento(results.get(0))));
	}

	@Override
	public void copyMasterData(String sourceCid, String targetCid, boolean isReplace) {
		// TODO Auto-generated method stub
		
	}

}
