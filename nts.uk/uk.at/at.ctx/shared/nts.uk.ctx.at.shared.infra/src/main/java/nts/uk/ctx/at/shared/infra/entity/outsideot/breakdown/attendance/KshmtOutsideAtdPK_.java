/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.attendance;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshmtOutsideAtdPK_.
 */
@StaticMetamodel(KshmtOutsideAtdPK.class)
public class KshmtOutsideAtdPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KshmtOutsideAtdPK, String> cid;
	
	/** The brd item no. */
	public static volatile SingularAttribute<KshmtOutsideAtdPK, Integer> brdItemNo;
	
	/** The attendance item id. */
	public static volatile SingularAttribute<KshmtOutsideAtdPK, Integer> attendanceItemId;
	

}
