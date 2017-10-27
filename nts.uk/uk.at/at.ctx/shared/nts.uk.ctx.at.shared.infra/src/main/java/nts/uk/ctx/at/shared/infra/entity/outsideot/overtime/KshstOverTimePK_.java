/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot.overtime;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshstOverTimePK_.
 */
@StaticMetamodel(KshstOverTimePK.class)
public class KshstOverTimePK_ {

	/** The cid. */
	public static volatile SingularAttribute<KshstOverTimePK, String> cid;
	
	/** The over time no. */
	public static volatile SingularAttribute<KshstOverTimePK, Integer> overTimeNo;
	

}
