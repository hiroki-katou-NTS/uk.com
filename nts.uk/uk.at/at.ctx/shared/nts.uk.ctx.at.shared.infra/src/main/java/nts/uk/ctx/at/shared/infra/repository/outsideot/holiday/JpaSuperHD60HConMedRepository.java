/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.outsideot.holiday;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.PremiumExtra60HRate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHD60HConMed;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHD60HConMedRepository;
import nts.uk.ctx.at.shared.infra.entity.outsideot.holiday.KshmtHd60hConMed;
import nts.uk.ctx.at.shared.infra.entity.outsideot.premium.KshmtHd60hPremiumRate;
import nts.uk.ctx.at.shared.infra.entity.outsideot.premium.KshmtHd60hPremiumRatePK_;
import nts.uk.ctx.at.shared.infra.entity.outsideot.premium.KshmtHd60hPremiumRate_;
import nts.uk.ctx.at.shared.infra.repository.outsideot.premium.JpaPremiumExtra60HRateGetMemento;
import nts.uk.ctx.at.shared.infra.repository.outsideot.premium.JpaPremiumExtra60HRateSetMemento;

/**
 * The Class JpaSuperHD60HConMedRepository.
 */
@Stateless
public class JpaSuperHD60HConMedRepository extends JpaRepository
		implements SuperHD60HConMedRepository {
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.holiday.SuperHD60HConMedRepository#
	 * findById(java.lang.String)
	 */
	@Override
	public Optional<SuperHD60HConMed> findById(String companyId) {
		
		// find by id to entity
		Optional<KshmtHd60hConMed> opEntity = this.queryProxy().find(companyId, KshmtHd60hConMed.class);
		
		// find by company id to domain
		List<PremiumExtra60HRate> premiumExtra60HRates = this.findAllPremiumRate(companyId);
		
		// check exist data
		if (opEntity.isPresent()) {
			return Optional.ofNullable(this.toDomain(opEntity.get(),
					this.toEntityPremiumExtraRate(companyId, premiumExtra60HRates)));
		}
		// default data
		return Optional.empty();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.holiday.SuperHD60HConMedRepository#save
	 * (nts.uk.ctx.at.shared.dom.overtime.holiday.SuperHD60HConMed)
	 */
	@Override
	public void save(SuperHD60HConMed domain) {
		Optional<SuperHD60HConMed> opEntity = this.findById(domain.getCompanyId().v());
		KshmtHd60hConMed entity = new KshmtHd60hConMed();
		if (opEntity.isPresent()) {
			entity = this.toEntity(opEntity.get());
			domain.saveToMemento(new JpaSuperHD60HConMedSetMemento(entity));
			this.commandProxy().update(entity);
		}
		else {
			domain.saveToMemento(new JpaSuperHD60HConMedSetMemento(entity));
			this.commandProxy().insert(entity);
		}
		
		// save all premium
		this.saveAllPremiumRate(domain.getPremiumExtra60HRates(), domain.getCompanyId().v());
		
	}
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the super HD 60 H con med
	 */
	private SuperHD60HConMed toDomain(KshmtHd60hConMed entity,
			List<KshmtHd60hPremiumRate> entityPremiumExtra60HRates) {
		return new SuperHD60HConMed(new JpaSuperHD60HConMedGetMemento(entity,entityPremiumExtra60HRates));
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshst super hd con med
	 */
	private KshmtHd60hConMed toEntity(SuperHD60HConMed domain){
		KshmtHd60hConMed entity = new KshmtHd60hConMed();
		domain.saveToMemento(new JpaSuperHD60HConMedSetMemento(entity));
		return entity;
	}
	
	/**
	 * To entity premium extra rate.
	 *
	 * @param domains the domains
	 * @return the list
	 */
	private List<KshmtHd60hPremiumRate> toEntityPremiumExtraRate(String companyId,
			List<PremiumExtra60HRate> domains) {
		return domains.stream().map(domain -> {
			KshmtHd60hPremiumRate entity = new KshmtHd60hPremiumRate();
			domain.saveToMemento(new JpaPremiumExtra60HRateSetMemento(entity, companyId));
			return entity;
		}).collect(Collectors.toList());
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.premium.extra.
	 * PremiumExtra60HRateRepository#findAll(java.lang.String)
	 */
	@Override
	public List<PremiumExtra60HRate> findAllPremiumRate(String companyId) {
		
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSHMT_HD60H_PREMIUM_RATE (KshmtHd60hPremiumRate SQL)
		CriteriaQuery<KshmtHd60hPremiumRate> cq = criteriaBuilder
				.createQuery(KshmtHd60hPremiumRate.class);

		// root data
		Root<KshmtHd60hPremiumRate> root = cq.from(KshmtHd60hPremiumRate.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(
				criteriaBuilder.equal(root.get(KshmtHd60hPremiumRate_.kshmtHd60hPremiumRatePK)
						.get(KshmtHd60hPremiumRatePK_.cid), companyId));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by breakdown item no asc and overtime no asc
		cq.orderBy(
				criteriaBuilder.asc(root.get(KshmtHd60hPremiumRate_.kshmtHd60hPremiumRatePK)
						.get(KshmtHd60hPremiumRatePK_.brdItemNo)),
				criteriaBuilder.asc(root.get(KshmtHd60hPremiumRate_.kshmtHd60hPremiumRatePK)
						.get(KshmtHd60hPremiumRatePK_.overTimeNo)));

		// create query
		TypedQuery<KshmtHd60hPremiumRate> query = em.createQuery(cq);

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
	public void saveAllPremiumRate(List<PremiumExtra60HRate> premiumExtras, String companyId) {
		
		// get all premium extra rate of data base
		List<PremiumExtra60HRate> lstPremiumExtraRates = this.findAllPremiumRate(companyId);
		
		// entity add all
		List<KshmtHd60hPremiumRate> entityAddAll = new ArrayList<>();

		// entity update all
		List<KshmtHd60hPremiumRate> entityUpdateAll = new ArrayList<>();

		// for each data premium extra rate
		premiumExtras.forEach(premiumExtra -> {
			if (this.findKeyOfPremiumExtraRate(premiumExtra, lstPremiumExtraRates)) {
				entityAddAll.add(this.toEntity(premiumExtra, companyId));
			} else {
				entityUpdateAll.add(this.toEntity(premiumExtra, companyId));
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
	private PremiumExtra60HRate toDomain(KshmtHd60hPremiumRate entity){
		return new PremiumExtra60HRate(new JpaPremiumExtra60HRateGetMemento(entity));
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @param companyId the company id
	 * @return the kshst premium ext 60 h rate
	 */
	private KshmtHd60hPremiumRate toEntity(PremiumExtra60HRate domain, String companyId){
		KshmtHd60hPremiumRate entity = new KshmtHd60hPremiumRate();
		domain.saveToMemento(new JpaPremiumExtra60HRateSetMemento(entity, companyId));
		return entity;
	}


}
