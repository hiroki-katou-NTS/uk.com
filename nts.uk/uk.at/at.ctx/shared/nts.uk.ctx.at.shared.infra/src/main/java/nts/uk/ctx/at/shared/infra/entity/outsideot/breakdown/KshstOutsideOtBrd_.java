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
@StaticMetamodel(KshmtOutsideDetail.class)
public class KshstOutsideOtBrd_ {

	/** The kshst outside ot brd PK. */
	public static volatile SingularAttribute<KshmtOutsideDetail, KshstOutsideOtBrdPK> kshstOutsideOtBrdPK;
	
	/** The name. */
	public static volatile SingularAttribute<KshmtOutsideDetail, String> name;
	
	/** The use atr. */
	public static volatile SingularAttribute<KshmtOutsideDetail, Integer> useAtr;
	
	/** The product number. */
	public static volatile SingularAttribute<KshmtOutsideDetail, Integer> productNumber;

}
