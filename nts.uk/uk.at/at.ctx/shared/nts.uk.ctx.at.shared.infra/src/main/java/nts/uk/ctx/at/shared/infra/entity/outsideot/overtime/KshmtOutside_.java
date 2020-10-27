/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot.overtime;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshmtOutside_.
 */
@StaticMetamodel(KshmtOutside.class)
public class KshmtOutside_ {

	/** The kshst over time PK. */
	public static volatile SingularAttribute<KshmtOutside, KshmtOutsidePK> kshmtOutsidePK;
	
	/** The is 60 h super hd. */
	public static volatile SingularAttribute<KshmtOutside, Integer> is60hSuperHd;
	
	/** The use atr. */
	public static volatile SingularAttribute<KshmtOutside, Integer> useAtr;
	
	/** The name. */
	public static volatile SingularAttribute<KshmtOutside, String> name;
	
	/** The over time. */
	public static volatile SingularAttribute<KshmtOutside, Integer> overTime;

}
