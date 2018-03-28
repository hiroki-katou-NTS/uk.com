/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.monthlyattditem;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KrcmtMonAttendanceItemPK_.
 */
@StaticMetamodel(KrcmtMonAttendanceItemPK.class)
public class KrcmtMonAttendanceItemPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KrcmtMonAttendanceItemPK, String> cid;

	/** The m atd item id. */
	public static volatile SingularAttribute<KrcmtMonAttendanceItemPK, Integer> mAtdItemId;

}
