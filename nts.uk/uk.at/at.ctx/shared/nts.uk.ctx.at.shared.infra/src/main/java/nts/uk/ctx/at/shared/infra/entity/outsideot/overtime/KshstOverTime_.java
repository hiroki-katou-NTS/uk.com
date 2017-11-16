/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot.overtime;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshstOverTime_.
 */
@StaticMetamodel(KshstOverTime.class)
public class KshstOverTime_ {

	/** The kshst over time PK. */
	public static volatile SingularAttribute<KshstOverTime, KshstOverTimePK> kshstOverTimePK;
	
	/** The is 60 h super hd. */
	public static volatile SingularAttribute<KshstOverTime, Integer> is60hSuperHd;
	
	/** The use atr. */
	public static volatile SingularAttribute<KshstOverTime, Integer> useAtr;
	
	/** The name. */
	public static volatile SingularAttribute<KshstOverTime, String> name;
	
	/** The over time. */
	public static volatile SingularAttribute<KshstOverTime, Integer> overTime;

}
