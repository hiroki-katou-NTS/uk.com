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
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours.KshmtHd60hCom;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours.KshmtHd60hCom_;

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
		Optional<KshmtHd60hCom> optEntity = this.queryProxy().find(setting.getCompanyId(),
				KshmtHd60hCom.class);

		KshmtHd60hCom entity = optEntity.get();

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
		CriteriaQuery<KshmtHd60hCom> cq = builder.createQuery(KshmtHd60hCom.class);
		Root<KshmtHd60hCom> root = cq.from(KshmtHd60hCom.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		predicateList.add(builder.equal(root.get(KshmtHd60hCom_.cid), companyId));

		cq.where(predicateList.toArray(new Predicate[] {}));

		List<KshmtHd60hCom> results = em.createQuery(cq).getResultList();

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
		KshmtHd60hCom entity = new KshmtHd60hCom();

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
