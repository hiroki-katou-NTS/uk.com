/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot.overtime;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshmtOutsidePK_.
 */
@StaticMetamodel(KshmtOutsidePK.class)
public class KshmtOutsidePK_ {

	/** The cid. */
	public static volatile SingularAttribute<KshmtOutsidePK, String> cid;
	
	/** The over time no. */
	public static volatile SingularAttribute<KshmtOutsidePK, Integer> overTimeNo;
	

}
