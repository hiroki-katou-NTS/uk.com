/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.employeeinfo;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.PersonalWorkScheduleCreSet;
import nts.uk.ctx.at.schedule.dom.employeeinfo.PersonalWorkScheduleCreSetRepository;
import nts.uk.ctx.at.schedule.infra.entity.employeeinfo.KscstScheCreSet;

/**
 * The Class JpaPersonalWorkScheduleCreSetRepository.
 */
@Stateless
public class JpaPersonalWorkScheduleCreSetRepository extends JpaRepository
		implements PersonalWorkScheduleCreSetRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.employeeinfo.
	 * PersonalWorkScheduleCreSetRepository#findById(java.lang.String)
	 */
	@Override
	public Optional<PersonalWorkScheduleCreSet> findById(String employeeId) {
		return this.queryProxy().find(employeeId, KscstScheCreSet.class)
				.map(entity -> this.toDomain(entity));
	}

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the personal work schedule cre set
	 */
	private PersonalWorkScheduleCreSet toDomain(KscstScheCreSet entity){
		return new PersonalWorkScheduleCreSet(new JpaPersonalWorkScheduleCreSetGetMemento(entity));
	}

}
