/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.workplace;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class JcwtstWorkplaceWtSetPK_.
 */
@StaticMetamodel(JwwstWorkplaceWtSetPK.class)
public class JwwstWorkplaceWtSetPK_ {

	/** The cid. */
	public static volatile SingularAttribute<JwwstWorkplaceWtSetPK, String> cid;

	/** The y K. */
	public static volatile SingularAttribute<JwwstWorkplaceWtSetPK, Integer> yK;

	/** The ctg. */
	public static volatile SingularAttribute<JwwstWorkplaceWtSetPK, Integer> ctg;

	/** The type. */
	public static volatile SingularAttribute<JwwstWorkplaceWtSetPK, Integer> type;

	/** The wkp id. */
	public static volatile SingularAttribute<JwwstWorkplaceWtSetPK, String> wkpId;

}
