/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.overtime.premium;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.overtime.premium.extra.PremiumExtra60HRate;
import nts.uk.ctx.at.shared.dom.overtime.premium.extra.PremiumExtra60HRateRepository;
import nts.uk.ctx.at.shared.infra.entity.overtime.premium.KshstPremiumExt60hRate;
import nts.uk.ctx.at.shared.infra.entity.overtime.premium.KshstPremiumExt60hRatePK_;
import nts.uk.ctx.at.shared.infra.entity.overtime.premium.KshstPremiumExt60hRate_;

/**
 * The Class JpaPremiumExtra60HRateRepository.
 */
@Stateless
public class JpaPremiumExtra60HRateRepository extends JpaRepository
		implements PremiumExtra60HRateRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.premium.extra.
	 * PremiumExtra60HRateRepository#findAll(java.lang.String)
	 */
	@Override
	public List<PremiumExtra60HRate> findAll(String companyId) {
		
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSHST_PREMIUM_EXT60H_RATE (KshstPremiumExt60hRate SQL)
		CriteriaQuery<KshstPremiumExt60hRate> cq = criteriaBuilder
				.createQuery(KshstPremiumExt60hRate.class);

		// root data
		Root<KshstPremiumExt60hRate> root = cq.from(KshstPremiumExt60hRate.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(
				criteriaBuilder.equal(root.get(KshstPremiumExt60hRate_.kshstPremiumExt60hRatePK)
						.get(KshstPremiumExt60hRatePK_.cid), companyId));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by breakdown item no asc and overtime no asc
		cq.orderBy(
				criteriaBuilder.asc(root.get(KshstPremiumExt60hRate_.kshstPremiumExt60hRatePK)
						.get(KshstPremiumExt60hRatePK_.brdItemNo)),
				criteriaBuilder.asc(root.get(KshstPremiumExt60hRate_.kshstPremiumExt60hRatePK)
						.get(KshstPremiumExt60hRatePK_.overTimeNo)));

		// create query
		TypedQuery<KshstPremiumExt60hRate> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(entity -> this.toDomain(entity))
				.collect(Collectors.toList());
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.premium.extra.
	 * PremiumExtra60HRateRepository#saveAll(java.util.List)
	 */
	@Override
	public void saveAll(List<PremiumExtra60HRate> premiumExtras, String companyId) {
		
		// get all premium extra rate of data base
		List<PremiumExtra60HRate> lstPremiumExtraRates = this.findAll(companyId);
		
		// entity add all
		List<KshstPremiumExt60hRate> entityAddAll = new ArrayList<>();

		// entity update all
		List<KshstPremiumExt60hRate> entityUpdateAll = new ArrayList<>();

		// for each data premium extra rate
		premiumExtras.forEach(premiumExtra -> {
			if (this.findKeyOfPremiumExtraRate(premiumExtra, lstPremiumExtraRates)) {
				entityUpdateAll.add(this.toEntity(premiumExtra, companyId));
			} else {
				entityAddAll.add(this.toEntity(premiumExtra, companyId));
			}
		});

		// insert all
		this.commandProxy().insertAll(entityAddAll);

		// update all
		this.commandProxy().updateAll(entityUpdateAll);
	}
	
	/**
	 * Find key of premium extra rate.
	 *
	 * @param premiumExtra the premium extra
	 * @param premiumExtras the premium extras
	 * @return true, if successful
	 */
	private boolean findKeyOfPremiumExtraRate(PremiumExtra60HRate premiumExtra,
			List<PremiumExtra60HRate> premiumExtras) {
		for (PremiumExtra60HRate premiumExtraGet : premiumExtras) {
			if (premiumExtra.getBreakdownItemNo().value == premiumExtraGet
					.getBreakdownItemNo().value
					&& premiumExtra.getOvertimeNo().value == premiumExtraGet
							.getOvertimeNo().value) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the premium extra 60 H rate
	 */
	private PremiumExtra60HRate toDomain(KshstPremiumExt60hRate entity){
		return new PremiumExtra60HRate(new JpaPremiumExtra60HRateGetMemento(entity));
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @param companyId the company id
	 * @return the kshst premium ext 60 h rate
	 */
	private KshstPremiumExt60hRate toEntity(PremiumExtra60HRate domain, String companyId){
		KshstPremiumExt60hRate entity = new KshstPremiumExt60hRate();
		domain.saveToMemento(new JpaPremiumExtra60HRateSetMemento(entity, companyId));
		return entity;
	}

}
