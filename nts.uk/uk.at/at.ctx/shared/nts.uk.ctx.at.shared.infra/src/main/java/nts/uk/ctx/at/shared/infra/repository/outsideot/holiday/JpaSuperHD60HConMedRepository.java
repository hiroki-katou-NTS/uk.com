/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.outsideot.holiday;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHD60HConMed;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHD60HConMedRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHDOccUnit;
import nts.uk.ctx.at.shared.infra.entity.outsideot.holiday.KshmtHd60hConMed;

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
		
		// check exist data
		if (opEntity.isPresent()) {
			return Optional.ofNullable(this.toDomain(opEntity.get()));
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
		Optional<SuperHD60HConMed> opEntity = this.findById(domain.getCompanyId());
		KshmtHd60hConMed entity = new KshmtHd60hConMed();
		if (opEntity.isPresent()) {
			this.toEntity(entity, domain);
			this.commandProxy().update(entity);
		}
		else {
			this.toEntity(entity, domain);
			this.commandProxy().insert(entity);
		}
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the super HD 60 H con med
	 */
	private SuperHD60HConMed toDomain(KshmtHd60hConMed entity) {
		return new SuperHD60HConMed(entity.getCid(), 
									new TimeRoundingSetting(entity.getRoundTime(), entity.getRounding()), 
									new SuperHDOccUnit(entity.getSuperHdUnit()));
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshst super hd con med
	 */
	private KshmtHd60hConMed toEntity(KshmtHd60hConMed entity, SuperHD60HConMed domain) {
		entity.setCid(domain.getCompanyId());
		entity.setRounding(domain.getTimeRoundingSetting().getRounding().value);
		entity.setRoundTime(domain.getTimeRoundingSetting().getRoundingTime().value);
		entity.setSuperHdUnit(domain.getSuperHolidayOccurrenceUnit().valueAsMinutes());
		
		return entity;
	}
	
}
