/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.sixtyhour;

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
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Emp60HourVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Emp60HourVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours.KshstEmp60hVacation;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours.KshstEmp60hVacationPK_;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours.KshstEmp60hVacation_;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.subst.KsvstEmpSubstVacation;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.subst.KsvstEmpSubstVacationPK_;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.subst.KsvstEmpSubstVacation_;
import nts.uk.ctx.at.shared.infra.repository.vacation.setting.subst.JpaEmpSubstVacationGetMemento;
import nts.uk.ctx.at.shared.infra.repository.vacation.setting.subst.JpaEmpSubstVacationSetMemento;

/**
 * The Class JpaEmpSubstVacationRepo.
 */
@Stateless
public class JpaEmp60HourVacationRepo extends JpaRepository implements Emp60HourVacationRepository {

	@Override
	public void update(Emp60HourVacation setting) {
		this.commandProxy().update(this.toEntity(setting));
		
	}

	@Override
	public Optional<Emp60HourVacation> findById(String companyId, String contractTypeCode) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<KshstEmp60hVacation> cq = builder.createQuery(KshstEmp60hVacation.class);
		Root<KshstEmp60hVacation> root = cq.from(KshstEmp60hVacation.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		predicateList.add(builder.equal(root.get(KshstEmp60hVacation_.kshstEmp60hVacationPK)
				.get(KshstEmp60hVacationPK_.cid), companyId));
		predicateList.add(builder.equal(root.get(KshstEmp60hVacation_.kshstEmp60hVacationPK)
				.get(KshstEmp60hVacationPK_.contractTypeCd), contractTypeCode));

		cq.where(predicateList.toArray(new Predicate[] {}));

		List<KshstEmp60hVacation> results = em.createQuery(cq).getResultList();

		if (CollectionUtil.isEmpty(results)) {
			return Optional.empty();
		}

		return Optional.of(new Emp60HourVacation(new JpaEmp60HourVacationGetMemento(results.get(0))));
	}
	private KshstEmp60hVacation toEntity(Emp60HourVacation setting) {
		KshstEmp60hVacation entity = new KshstEmp60hVacation();
		setting.saveToMemento(new JpaEmp60HourVacationSetMemento(entity));
		return entity;
	}
}
