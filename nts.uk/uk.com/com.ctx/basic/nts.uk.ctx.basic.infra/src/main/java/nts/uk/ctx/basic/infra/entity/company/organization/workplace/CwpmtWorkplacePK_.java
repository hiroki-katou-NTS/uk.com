/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.workplace;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class CwpmtWorkplacePK_.
 */
@StaticMetamodel(CwpmtWorkplacePK.class)
public class CwpmtWorkplacePK_ {

	/** The cid. */
	public static volatile SingularAttribute<CwpmtWorkplacePK, String> cid;
	
	/** The wkpid. */
	public static volatile SingularAttribute<CwpmtWorkplacePK, String> wkpid;
	
	/** The str D. */
	public static volatile SingularAttribute<CwpmtWorkplacePK, GeneralDate> strD;
	
	/** The end D. */
	public static volatile SingularAttribute<CwpmtWorkplacePK, GeneralDate> endD;
}