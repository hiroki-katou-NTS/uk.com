/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.shortworktime;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class BshmtWorktimeHistItemPK_.
 */
@StaticMetamodel(BshmtWorktimeHistItemPK.class)
public class BshmtWorktimeHistItemPK_ {

	/** The sid. */
	public static SingularAttribute<BshmtWorktimeHistItemPK, String> sid;
	
	/** The hist id. */
	public static SingularAttribute<BshmtWorktimeHistItemPK, String> histId;
}
