/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.overtime.holiday;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.overtime.holiday.SuperHD60HConMed;
import nts.uk.ctx.at.shared.dom.overtime.holiday.SuperHD60HConMedRepository;
import nts.uk.ctx.at.shared.infra.entity.overtime.holiday.KshstSuperHdConMed;

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
		return this.queryProxy().find(companyId, KshstSuperHdConMed.class)
				.map(entity -> this.toDomain(entity));
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

}
