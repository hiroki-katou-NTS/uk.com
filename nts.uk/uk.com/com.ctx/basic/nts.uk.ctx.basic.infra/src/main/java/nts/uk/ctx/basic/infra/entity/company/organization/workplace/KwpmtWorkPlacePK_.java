/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.workplace;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KwpmtWorkHistPK_.
 */
@StaticMetamodel(KwpmtWorkPlacePK.class)
public class KwpmtWorkPlacePK_ {

	/** The ccid. */
	public static volatile SingularAttribute<KwpmtWorkPlacePK, String> ccid;
	
	/** The wkpid. */
	public static volatile SingularAttribute<KwpmtWorkPlacePK, String> wkpid;
	
	/** The wkpcd. */
	public static volatile SingularAttribute<KwpmtWorkPlacePK, String> wkpcd;
	
	

}