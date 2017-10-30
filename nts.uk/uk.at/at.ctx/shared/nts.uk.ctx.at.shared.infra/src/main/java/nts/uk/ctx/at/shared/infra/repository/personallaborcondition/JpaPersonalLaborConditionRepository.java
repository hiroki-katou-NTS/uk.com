/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.personallaborcondition;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborConditionRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class JpaPersonalLaborConditionRepository.
 */
@Stateless
public class JpaPersonalLaborConditionRepository extends JpaRepository implements PersonalLaborConditionRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalLaborConditionRepository#findById(java.lang.String,
	 * nts.uk.shr.com.time.calendar.period.DatePeriod)
	 */
	@Override
	public Optional<PersonalLaborCondition> findById(String employeeId, DatePeriod period) {
		// TODO Auto-generated method stub
		return null;
	}

}
