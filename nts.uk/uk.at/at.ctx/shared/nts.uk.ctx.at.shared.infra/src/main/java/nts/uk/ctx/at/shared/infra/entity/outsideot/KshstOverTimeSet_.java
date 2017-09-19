/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshstOverTimeSet_.
 */
@StaticMetamodel(KshstOverTimeSet.class)
public class KshstOverTimeSet_ {

	/** The cid. */
	public static volatile SingularAttribute<KshstOverTimeSet, String> cid;
	
	/** The note. */
	public static volatile SingularAttribute<KshstOverTimeSet, String> note;
	
	/** The calculation method. */
	public static volatile SingularAttribute<KshstOverTimeSet, Integer> calculationMethod;

}
