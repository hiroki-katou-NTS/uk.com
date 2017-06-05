/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.employee;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class CemptEmployeePK_.
 */
@StaticMetamodel(CemptEmployeePK.class)
public class CemptEmployeePK_ {

	/** The ccid. */
	public static volatile SingularAttribute<CemptEmployeePK, String> ccid;

	/** The pid. */
	public static volatile SingularAttribute<CemptEmployeePK, String> pid;
	
	/** The empid. */
	public static volatile SingularAttribute<CemptEmployeePK, String> empid;
	
	/** The empcd. */
	public static volatile SingularAttribute<CemptEmployeePK, String> empcd;

}