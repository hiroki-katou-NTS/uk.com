/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KrcmtAnyv_.
 */
@StaticMetamodel(KrcmtAnyv.class)
public class KrcmtAnyv_ {

	/** The krcst optional item PK. */
	public static volatile SingularAttribute<KrcmtAnyv, KrcmtAnyvPK> krcmtAnyvPK;

	/** The optional item name. */
	public static volatile SingularAttribute<KrcmtAnyv, String> optionalItemName;

	/** The optional item atr. */
	public static volatile SingularAttribute<KrcmtAnyv, Integer> optionalItemAtr;

	/** The usage atr. */
	public static volatile SingularAttribute<KrcmtAnyv, Integer> usageAtr;

	/** The performance atr. */
	public static volatile SingularAttribute<KrcmtAnyv, Integer> performanceAtr;

	/** The emp condition atr. */
	public static volatile SingularAttribute<KrcmtAnyv, Integer> empConditionAtr;
	
	/** The krcst calc result range. */
	public static volatile SingularAttribute<KrcmtAnyv, KrcmtAnyfResultRange> krcmtAnyfResultRange;


}
