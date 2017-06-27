/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.workplace;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KwpmtWorkPlace_.
 */
@StaticMetamodel(KwpmtWorkplace.class)
public class KwpmtWorkplace_ {

	/** The kwpmt workplace PK. */
	public static volatile SingularAttribute<KwpmtWorkplace, KwpmtWorkplacePK> kwpmtWorkplacePK;
	
	/** The wplcd. */
	public static volatile SingularAttribute<KwpmtWorkplace, String> wplcd;
		
	/** The wplname. */
	public static volatile SingularAttribute<KwpmtWorkplace, String> wplname;
	

}