/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.shortworktime;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class BshmtSchildCareFramePK_.
 */
@StaticMetamodel(BshmtSchildCareFramePK.class)
public class BshmtSchildCareFramePK_ {

	/** The sid. */
	public static SingularAttribute<BshmtSchildCareFramePK, String> sid;
	
	/** The hist id. */
	public static SingularAttribute<BshmtSchildCareFramePK, String> histId;
	
	/** The str clock. */
	public static SingularAttribute<BshmtSchildCareFramePK, Integer> strClock;
}
