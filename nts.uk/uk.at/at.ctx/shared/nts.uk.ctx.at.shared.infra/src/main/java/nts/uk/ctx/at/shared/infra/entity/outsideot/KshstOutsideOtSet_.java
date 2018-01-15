/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshstOutsideOtSet_.
 */
@StaticMetamodel(KshstOutsideOtSet.class)
public class KshstOutsideOtSet_ {

	/** The cid. */
	public static volatile SingularAttribute<KshstOutsideOtSet, String> cid;
	
	/** The note. */
	public static volatile SingularAttribute<KshstOutsideOtSet, String> note;
	
	/** The calculation method. */
	public static volatile SingularAttribute<KshstOutsideOtSet, Integer> calculationMethod;

}
