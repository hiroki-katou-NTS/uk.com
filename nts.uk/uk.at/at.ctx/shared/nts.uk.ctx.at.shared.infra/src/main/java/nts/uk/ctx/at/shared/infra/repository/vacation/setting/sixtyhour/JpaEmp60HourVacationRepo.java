/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.sixtyhour;

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
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Emp60HourVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Emp60HourVacationRepository;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours.KshstEmp60hVacation;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours.KshstEmp60hVacationPK;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours.KshstEmp60hVacationPK_;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours.KshstEmp60hVacation_;

/**
 * The Class JpaEmpSubstVacationRepo.
 */
@Stateless
public class JpaEmp60HourVacationRepo extends JpaRepository implements Emp60HourVacationRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.
	 * Emp60HourVacationRepository#update(nts.uk.ctx.at.shared.dom.vacation.
	 * setting.sixtyhours.Emp60HourVacation)
	 */
	@Override
	public void update(Emp60HourVacation setting) {
		Optional<KshstEmp60hVacation> optional = this.queryProxy().find(
				new KshstEmp60hVacationPK(setting.getCompanyId(), setting.getEmpContractTypeCode()),
				KshstEmp60hVacation.class);

		KshstEmp60hVacation entity = optional.get();
		setting.saveToMemento(new JpaEmp60HourVacationSetMemento(entity));
		this.commandProxy().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.
	 * Emp60HourVacationRepository#findById(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<Emp60HourVacation> findById(String companyId, String contractTypeCode) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<KshstEmp60hVacation> cq = builder.createQuery(KshstEmp60hVacation.class);
		Root<KshstEmp60hVacation> root = cq.from(KshstEmp60hVacation.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		predicateList.add(builder.equal(
				root.get(KshstEmp60hVacation_.kshstEmp60hVacationPK).get(KshstEmp60hVacationPK_.cid), companyId));
		predicateList.add(builder.equal(
				root.get(KshstEmp60hVacation_.kshstEmp60hVacationPK).get(KshstEmp60hVacationPK_.contractTypeCd),
				contractTypeCode));

		cq.where(predicateList.toArray(new Predicate[] {}));

		List<KshstEmp60hVacation> results = em.createQuery(cq).getResultList();

		if (CollectionUtil.isEmpty(results)) {
			return Optional.empty();
		}

		return Optional.of(new Emp60HourVacation(new JpaEmp60HourVacationGetMemento(results.get(0))));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.
	 * Emp60HourVacationRepository#findAll(java.lang.String)
	 */
	@Override
	public List<Emp60HourVacation> findAll(String companyId) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<KshstEmp60hVacation> cq = builder.createQuery(KshstEmp60hVacation.class);
		Root<KshstEmp60hVacation> root = cq.from(KshstEmp60hVacation.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		predicateList.add(builder.equal(
				root.get(KshstEmp60hVacation_.kshstEmp60hVacationPK).get(KshstEmp60hVacationPK_.cid), companyId));
		cq.where(predicateList.toArray(new Predicate[] {}));
		return em.createQuery(cq).getResultList().stream()
				.map(entity -> new Emp60HourVacation(new JpaEmp60HourVacationGetMemento(entity)))
				.collect(Collectors.toList());
	}

	@Override
	public void insert(Emp60HourVacation setting) {
		KshstEmp60hVacation entity = new KshstEmp60hVacation();

		setting.saveToMemento(new JpaEmp60HourVacationSetMemento(entity));

		this.commandProxy().insert(entity);
	}

}
