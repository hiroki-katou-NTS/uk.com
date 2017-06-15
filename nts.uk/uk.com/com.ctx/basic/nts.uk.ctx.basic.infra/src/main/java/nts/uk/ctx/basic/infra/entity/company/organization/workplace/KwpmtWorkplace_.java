/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.workplace;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KwpmtWorkPlace_.
 */
@StaticMetamodel(KwpmtWorkplace.class)
public class KwpmtWorkplace_ {

	/** The kwpmt workplace PK. */
	public static volatile SingularAttribute<KwpmtWorkplace, KwpmtWorkplacePK> kwpmtWorkplacePK;
	
	/** The str D. */
	public static volatile SingularAttribute<KwpmtWorkplace, GeneralDate> strD;
	
	/** The end D. */
	public static volatile SingularAttribute<KwpmtWorkplace, GeneralDate> endD;
	
	/** The wkpcd. */
	public static volatile SingularAttribute<KwpmtWorkplace, String> wkpcd;
	
	/** The wkpname. */
	public static volatile SingularAttribute<KwpmtWorkplace, String> wkpname;
	

}