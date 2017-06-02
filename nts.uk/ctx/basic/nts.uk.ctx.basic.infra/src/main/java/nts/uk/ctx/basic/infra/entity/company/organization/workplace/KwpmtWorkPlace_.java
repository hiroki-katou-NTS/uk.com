/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.workplace;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KwpmtWorkHistPK_.
 */
@StaticMetamodel(KwpmtWorkPlace.class)
public class KwpmtWorkPlace_ {

	/** The ccid. */
	public static volatile SingularAttribute<KwpmtWorkPlace, KwpmtWorkPlacePK> kwpmtWorkPlacePK;
	
	/** The str D. */
	public static volatile SingularAttribute<KwpmtWorkPlace, GeneralDate> strD;
	
	/** The end D. */
	public static volatile SingularAttribute<KwpmtWorkPlace, GeneralDate> endD;
	
	/** The wkpname. */
	public static volatile SingularAttribute<KwpmtWorkPlace, String> wkpname;
	

}