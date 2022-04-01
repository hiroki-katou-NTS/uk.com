/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.monthlyattditem;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KrcmtMonAttendanceItem_.
 */
@StaticMetamodel(KrcmtMonAttendanceItem.class)
public class KrcmtMonAttendanceItem_ {

	/** The krcmt mon attendance item PK. */
	public static volatile SingularAttribute<KrcmtMonAttendanceItem, KrcmtMonAttendanceItemPK> krcmtMonAttendanceItemPK;

	/** The m atd item name. */
	public static volatile SingularAttribute<KrcmtMonAttendanceItem, String> mAtdItemName;

	/** The m atd item atr. */
	public static volatile SingularAttribute<KrcmtMonAttendanceItem, Integer> mAtdItemAtr;

	/** The disp no. */
	public static volatile SingularAttribute<KrcmtMonAttendanceItem, Integer> dispNo;

	/** The is allow change. */
	public static volatile SingularAttribute<KrcmtMonAttendanceItem, Integer> isAllowChange;

	/** The line break pos name. */
	public static volatile SingularAttribute<KrcmtMonAttendanceItem, Integer> lineBreakPosName;


	public static volatile SingularAttribute<KrcmtMonAttendanceItem, Boolean> useByMon;

	public static volatile SingularAttribute<KrcmtMonAttendanceItem, Boolean> useByAnp;

}
