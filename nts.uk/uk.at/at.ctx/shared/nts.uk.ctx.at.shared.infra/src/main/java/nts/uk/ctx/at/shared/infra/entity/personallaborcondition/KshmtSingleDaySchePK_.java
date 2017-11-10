/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.personallaborcondition;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KshmtSingleDaySchePK_.
 */
@StaticMetamodel(KshmtSingleDaySchePK.class)
public class KshmtSingleDaySchePK_ {

	/** The sid. */
	public static volatile SingularAttribute<KshmtSingleDaySchePK, String> sid;
	
	/** The pers work atr. */
	public static volatile SingularAttribute<KshmtSingleDaySchePK, Integer> persWorkAtr;
	
	/** The start ymd. */
	public static volatile SingularAttribute<KshmtSingleDaySchePK, GeneralDate> startYmd;
	
	/** The end ymd. */
	public static volatile SingularAttribute<KshmtSingleDaySchePK, GeneralDate> endYmd;
}
