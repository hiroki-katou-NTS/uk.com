/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.attendance;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshstOutsideOtBrdAtenPK_.
 */
@StaticMetamodel(KshstOutsideOtBrdAtenPK.class)
public class KshstOutsideOtBrdAtenPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KshstOutsideOtBrdAtenPK, String> cid;
	
	/** The brd item no. */
	public static volatile SingularAttribute<KshstOutsideOtBrdAtenPK, Integer> brdItemNo;
	
	/** The attendance item id. */
	public static volatile SingularAttribute<KshstOutsideOtBrdAtenPK, Integer> attendanceItemId;
	

}
