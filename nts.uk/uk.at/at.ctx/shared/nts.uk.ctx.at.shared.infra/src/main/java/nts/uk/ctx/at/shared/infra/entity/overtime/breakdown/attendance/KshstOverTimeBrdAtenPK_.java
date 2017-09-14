/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.overtime.breakdown.attendance;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshstOverTimeBrdAtenPK_.
 */
@StaticMetamodel(KshstOverTimeBrdAtenPK.class)
public class KshstOverTimeBrdAtenPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KshstOverTimeBrdAtenPK, String> cid;
	
	/** The brd item no. */
	public static volatile SingularAttribute<KshstOverTimeBrdAtenPK, Integer> brdItemNo;
	
	/** The attendance item id. */
	public static volatile SingularAttribute<KshstOverTimeBrdAtenPK, Integer> attendanceItemId;
	

}
