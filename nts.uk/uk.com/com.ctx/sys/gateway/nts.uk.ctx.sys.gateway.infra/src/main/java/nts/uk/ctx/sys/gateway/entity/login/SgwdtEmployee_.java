/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.entity.login;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(SgwdtEmployee.class)
public class SgwdtEmployee_ {

	/** The sgwdt employee PK. */
	public static volatile SingularAttribute<SgwdtEmployee, SgwdtEmployeePK> sgwdtEmployeePK;
	
	/** The scd. */
	public static volatile SingularAttribute<SgwdtEmployee,String> scd;
}
