/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.personallaborcondition;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshmtPerLaborCondPK_.
 */
@StaticMetamodel(KshmtPerLaborCondPK.class)
public class KshmtPerLaborCondPK_ {

	/** The sid. */
	public static volatile SingularAttribute<KshmtPerLaborCondPK, String> sid;
	
	/** The start ymd. */
	public static volatile SingularAttribute<KshmtPerLaborCondPK, Date> startYmd;
	
	/** The end ymd. */
	public static volatile SingularAttribute<KshmtPerLaborCondPK, Date> endYmd;
	
}
