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
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours.KshstCom60hVacation;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours.KshstCom60hVacation_;

/**
 * The Class JpaComSubstVacationRepo.
 */
@Stateless
public class JpaCom60HourVacationRepo extends JpaRepository implements Com60HourVacationRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacationRepository#update(nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacation)
	 */
	@Override
	public void update(Com60HourVacation setting) {
		Optional<KshstCom60hVacation> optEntity = this.queryProxy().find(setting.getCompanyId(),
				KshstCom60hVacation.class);

		KshstCom60hVacation entity = optEntity.get();

		setting.saveToMemento(new JpaCom60HourVacationSetMemento(entity));

		this.commandProxy().update(entity);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacationRepository#findById(java.lang.String)
	 */
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

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacationRepository#insert(nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacation)
	 */
	@Override
	public void insert(Com60HourVacation setting) {
		KshstCom60hVacation entity = new KshstCom60hVacation();

		setting.saveToMemento(new JpaCom60HourVacationSetMemento(entity));

		this.commandProxy().insert(entity);		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacationRepository#copyMasterData(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public void copyMasterData(String sourceCid, String targetCid, boolean isReplace) {
		// TODO Auto-generated method stub
		
	}
}
