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
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours.KshstCom60hVacation;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours.KshstCom60hVacation_;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.subst.KsvstComSubstVacation;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.subst.KsvstComSubstVacation_;
import nts.uk.ctx.at.shared.infra.repository.vacation.setting.subst.JpaComSubstVacationGetMemento;
import nts.uk.ctx.at.shared.infra.repository.vacation.setting.subst.JpaComSubstVacationSetMemento;

/**
 * The Class JpaComSubstVacationRepo.
 */
@Stateless
public class JpaCom60HourVacationRepo extends JpaRepository implements Com60HourVacationRepository {

	@Override
	public void update(Com60HourVacation setting) {
		this.commandProxy().update(this.toEntity(setting));
	}

	@Override
	public Optional<Com60HourVacation> findById(String companyId) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<KshstCom60hVacation> cq = builder.createQuery(KshstCom60hVacation.class);
		Root<KshstCom60hVacation> root = cq.from(KshstCom60hVacation.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		predicateList.add(builder.equal(root.get(KshstCom60hVacation_.cid), companyId));

		cq.where(predicateList.toArray(new Predicate[] {}));

		List<KshstCom60hVacation> results = em.createQuery(cq).getResultList();

		if (CollectionUtil.isEmpty(results)) {
			return Optional.empty();
		}

		return Optional.of(new Com60HourVacation(new JpaCom60HourVacationGetMemento(results.get(0))));
	}
	private KshstCom60hVacation toEntity(Com60HourVacation setting) {
		KshstCom60hVacation entity = new KshstCom60hVacation();
		setting.saveToMemento(new JpaCom60HourVacationSetMemento(entity));
		return entity;
	}
}
