/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshstOutsideOtBrd_.
 */
@StaticMetamodel(KshstOutsideOtBrd.class)
public class KshstOutsideOtBrd_ {

	/** The kshst outside ot brd PK. */
	public static volatile SingularAttribute<KshstOutsideOtBrd, KshstOutsideOtBrdPK> kshstOutsideOtBrdPK;
	
	/** The name. */
	public static volatile SingularAttribute<KshstOutsideOtBrd, String> name;
	
	/** The use atr. */
	public static volatile SingularAttribute<KshstOutsideOtBrd, Integer> useAtr;
	
	/** The product number. */
	public static volatile SingularAttribute<KshstOutsideOtBrd, Integer> productNumber;

}
