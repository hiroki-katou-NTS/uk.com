/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.overtime.breakdown;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshstOverTimeBrd_.
 */
@StaticMetamodel(KshstOverTimeBrd.class)
public class KshstOverTimeBrd_ {

	/** The kshst over time brd PK. */
	public static volatile SingularAttribute<KshstOverTimeBrd, KshstOverTimeBrdPK> kshstOverTimeBrdPK;
	
	/** The name. */
	public static volatile SingularAttribute<KshstOverTimeBrd, String> name;
	
	/** The use atr. */
	public static volatile SingularAttribute<KshstOverTimeBrd, Integer> useAtr;
	
	/** The name. */
	public static volatile SingularAttribute<KshstOverTimeBrd, Integer> productNumber;

}
