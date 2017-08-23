/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.employment;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class CemptEmployment_.
 */
@StaticMetamodel(CemptEmployment.class)
public class CemptEmployment_ {
	

	public static volatile SingularAttribute<CemptEmployment, CemptEmploymentPK> cemptEmploymentPK;
	
	/** The salary closure id. */
	public static volatile SingularAttribute<CemptEmployment, Integer> salaryClosureId;
	
	/** The work closure id. */
	public static volatile SingularAttribute<CemptEmployment, Integer> workClosureId;


	/** The name. */
	public static volatile SingularAttribute<CemptEmployment, String> name;
	
}
