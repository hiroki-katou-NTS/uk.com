/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.employee;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


/**
 * The Class CemptEmployee_.
 */
@StaticMetamodel(CemptEmployee.class)
public class CemptEmployee_ {

	/** The cempt employee PK. */
	public static volatile SingularAttribute<CemptEmployee, CemptEmployeePK> cemptEmployeePK;

	/** The join date. */
	public static volatile SingularAttribute<CemptEmployee, String> joinDate;

}