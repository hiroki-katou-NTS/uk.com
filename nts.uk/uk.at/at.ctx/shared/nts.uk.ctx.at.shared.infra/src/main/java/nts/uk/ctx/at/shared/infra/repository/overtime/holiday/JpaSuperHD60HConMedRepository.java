/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.overtime.holiday;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.overtime.holiday.SuperHD60HConMed;
import nts.uk.ctx.at.shared.dom.overtime.holiday.SuperHD60HConMedRepository;
import nts.uk.ctx.at.shared.dom.overtime.premium.extra.PremiumExtra60HRateRepository;
import nts.uk.ctx.at.shared.infra.entity.overtime.holiday.KshstSuperHdConMed;

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
		return this.queryProxy().find(companyId, KshstSuperHdConMed.class)
				.map(entity -> this.toDomain(entity));
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
	private SuperHD60HConMed toDomain(KshstSuperHdConMed entity){
		return new SuperHD60HConMed(new JpaSuperHD60HConMedGetMemento(entity));
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
	

}
