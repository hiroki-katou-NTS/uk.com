/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.personallaborcondition;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KshmtPerLaborCondPK_.
 */
@StaticMetamodel(KshmtPerLaborCondPK.class)
public class KshmtPerLaborCondPK_ {

	/** The sid. */
	public static volatile SingularAttribute<KshmtPerLaborCondPK, String> sid;
	
	/** The start ymd. */
	public static volatile SingularAttribute<KshmtPerLaborCondPK, GeneralDate> startYmd;
	
	/** The end ymd. */
	public static volatile SingularAttribute<KshmtPerLaborCondPK, GeneralDate> endYmd;
	
}
