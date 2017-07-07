/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.workplace;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class CwpmtWorkplace_.
 */
@StaticMetamodel(CwpmtWorkplace.class)
public class CwpmtWorkplace_ {

	/** The cwpmt workplace PK. */
	public static volatile SingularAttribute<CwpmtWorkplace, CwpmtWorkplacePK> cwpmtWorkplacePK;
	
	/** The wkpcd. */
	public static volatile SingularAttribute<CwpmtWorkplace, String> wkpcd;
		
	/** The wkpname. */
	public static volatile SingularAttribute<CwpmtWorkplace, String> wkpname;
	

}