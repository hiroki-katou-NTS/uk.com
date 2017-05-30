/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.subst;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
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
	 * ComSubstVacationRepository#add(nts.uk.ctx.at.shared.dom.vacation.setting.
	 * subst.ComSubstVacation)
	 */
	@Override
	public void add(ComSubstVacation setting) {
		this.commandProxy().insert(this.toEntity(setting));

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
		this.commandProxy().update(this.toEntity(setting));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.subst.
	 * ComSubstVacationRepository#findAll(java.lang.String)
	 */
	@Override
	public List<ComSubstVacation> findAll(String companyId) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<KsvstComSubstVacation> cq = builder.createQuery(KsvstComSubstVacation.class);
		Root<KsvstComSubstVacation> root = cq.from(KsvstComSubstVacation.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		predicateList.add(builder.equal(root.get(KsvstComSubstVacation_.cid), companyId));

		cq.where(predicateList.toArray(new Predicate[] {}));

		List<KsvstComSubstVacation> result = em.createQuery(cq).getResultList();

		return result.stream()
				.map(item -> new ComSubstVacation(new JpaComSubstVacationGetMemento(item)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.subst.
	 * ComSubstVacationRepository#findById(java.lang.String)
	 */
	@Override
	public Optional<ComSubstVacation> findById(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * To entity.
	 *
	 * @param setting
	 *            the setting
	 * @return the ksvst com subst vacation
	 */
	private KsvstComSubstVacation toEntity(ComSubstVacation setting) {
		KsvstComSubstVacation entity = new KsvstComSubstVacation();
		setting.saveToMemento(new JpaComSubstVacationSetMemento(entity));
		return entity;
	}

}
