/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem.calculation;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KrcmtAnyfItemSelectPK_.
 */
@StaticMetamodel(KrcmtAnyfItemSelectPK.class)
public class KrcmtAnyfItemSelectPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KrcmtAnyfItemSelectPK, String> cid;

	/** The optional item no. */
	public static volatile SingularAttribute<KrcmtAnyfItemSelectPK, Integer> optionalItemNo;

	/** The formula id. */
	public static volatile SingularAttribute<KrcmtAnyfItemSelectPK, String> formulaId;

	/** The attendance item id. */
	public static volatile SingularAttribute<KrcmtAnyfItemSelectPK, String> attendanceItemId;

}
