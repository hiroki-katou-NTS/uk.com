/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.outsideot.holiday;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.outsideot.holiday.SuperHD60HConMed;
import nts.uk.ctx.at.shared.dom.outsideot.holiday.SuperHD60HConMedRepository;
import nts.uk.ctx.at.shared.dom.outsideot.premium.extra.PremiumExtra60HRate;
import nts.uk.ctx.at.shared.dom.outsideot.premium.extra.PremiumExtra60HRateRepository;
import nts.uk.ctx.at.shared.infra.entity.outsideot.holiday.KshstSuperHdConMed;
import nts.uk.ctx.at.shared.infra.entity.outsideot.premium.KshstPremiumExt60hRate;
import nts.uk.ctx.at.shared.infra.repository.outsideot.premium.JpaPremiumExtra60HRateSetMemento;

/**
 * The Class JpaSuperHD60HConMedRepository.
 */
@Stateless
public class JpaSuperHD60HConMedRepository extends JpaRepository
		implements SuperHD60HConMedRepository {
	
	/** The repository. */
	@Inject
	private PremiumExtra60HRateRepository repository;
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
		Optional<KshstSuperHdConMed> opEntity = this.queryProxy().find(companyId, KshstSuperHdConMed.class);
		
		// find by company id to domain
		List<PremiumExtra60HRate> premiumExtra60HRates = this.repository.findAll(companyId);
		
		// check exist data
		if (opEntity.isPresent()) {
			return Optional.ofNullable(this.toDomain(opEntity.get(),
					this.toEntityPremiumExtraRate(companyId, premiumExtra60HRates)));
		}
		// default data
		return Optional.ofNullable(this.toDomain(new KshstSuperHdConMed(),
				this.toEntityPremiumExtraRate(companyId, premiumExtra60HRates)));
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
		KshstSuperHdConMed entity = new KshstSuperHdConMed();
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
		this.repository.saveAll(domain.getPremiumExtra60HRates(), domain.getCompanyId().v());
		
	}
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the super HD 60 H con med
	 */
	private SuperHD60HConMed toDomain(KshstSuperHdConMed entity,
			List<KshstPremiumExt60hRate> entityPremiumExtra60HRates) {
		return new SuperHD60HConMed(new JpaSuperHD60HConMedGetMemento(entity,entityPremiumExtra60HRates));
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshst super hd con med
	 */
	private KshstSuperHdConMed toEntity(SuperHD60HConMed domain){
		KshstSuperHdConMed entity = new KshstSuperHdConMed();
		domain.saveToMemento(new JpaSuperHD60HConMedSetMemento(entity));
		return entity;
	}
	
	/**
	 * To entity premium extra rate.
	 *
	 * @param domains the domains
	 * @return the list
	 */
	private List<KshstPremiumExt60hRate> toEntityPremiumExtraRate(String companyId,
			List<PremiumExtra60HRate> domains) {
		return domains.stream().map(domain -> {
			KshstPremiumExt60hRate entity = new KshstPremiumExt60hRate();
			domain.saveToMemento(new JpaPremiumExtra60HRateSetMemento(entity, companyId));
			return entity;
		}).collect(Collectors.toList());
	}
	

}
