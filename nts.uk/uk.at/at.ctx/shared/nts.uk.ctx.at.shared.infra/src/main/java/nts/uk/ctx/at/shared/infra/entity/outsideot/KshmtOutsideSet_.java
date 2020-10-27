/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshmtOutsideSet_.
 */
@StaticMetamodel(KshmtOutsideSet.class)
public class KshmtOutsideSet_ {

	/** The cid. */
	public static volatile SingularAttribute<KshmtOutsideSet, String> cid;
	
	/** The note. */
	public static volatile SingularAttribute<KshmtOutsideSet, String> note;
	
	/** The calculation method. */
	public static volatile SingularAttribute<KshmtOutsideSet, Integer> calculationMethod;

}
