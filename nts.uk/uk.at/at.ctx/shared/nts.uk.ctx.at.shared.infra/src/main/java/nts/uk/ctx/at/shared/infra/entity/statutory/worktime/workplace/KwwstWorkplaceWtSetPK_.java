/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime.workplace;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class JcwtstWorkplaceWtSetPK_.
 */
@StaticMetamodel(KwwstWorkplaceWtSetPK.class)
public class KwwstWorkplaceWtSetPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KwwstWorkplaceWtSetPK, String> cid;

	/** The y K. */
	public static volatile SingularAttribute<KwwstWorkplaceWtSetPK, Integer> yK;

	/** The ctg. */
	public static volatile SingularAttribute<KwwstWorkplaceWtSetPK, Integer> ctg;

	/** The type. */
	public static volatile SingularAttribute<KwwstWorkplaceWtSetPK, Integer> type;

	/** The wkp id. */
	public static volatile SingularAttribute<KwwstWorkplaceWtSetPK, String> wkpId;

}
