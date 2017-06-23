/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.workplace;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KwpmtWplHist_.
 */
@StaticMetamodel(KwpmtWplHist.class)
public class KwpmtWplHist_ {

	/** The kwpmt work hist PK. */
	public static volatile SingularAttribute<KwpmtWplHist, KwpmtWplHistPK> kwpmtWplHistPK;
	
	/** The str D. */
	public static volatile SingularAttribute<KwpmtWplHist, GeneralDate> strD;
	
	/** The end D. */
	public static volatile SingularAttribute<KwpmtWplHist, GeneralDate> endD;

}