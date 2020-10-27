/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot.premium;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshmtHd60hPremiumRate_.
 */
@StaticMetamodel(KshmtHd60hPremiumRate.class)
public class KshmtHd60hPremiumRate_ {

	/** The kshst premium ext 60 h rate PK. */
	public static volatile SingularAttribute<KshmtHd60hPremiumRate, KshmtHd60hPremiumRatePK> kshmtHd60hPremiumRatePK;
	
	/** The premium rate. */
	public static volatile SingularAttribute<KshmtHd60hPremiumRate, Integer> premiumRate;
	
}
