/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KmfmtRetentionYearly_.
 */
@StaticMetamodel(KmfmtRetentionYearly.class)
public class KmfmtRetentionYearly_ {

	/** The year amount. */
	public static volatile SingularAttribute<KmfmtRetentionYearly, Short> yearAmount;
	
	/** The max days retention. */
	public static volatile SingularAttribute<KmfmtRetentionYearly, Short> maxDaysRetention;
	
	/** The cid. */
	public static volatile SingularAttribute<KmfmtRetentionYearly, String> cid;
	
	/** The leave as work days. */
	public static volatile SingularAttribute<KmfmtRetentionYearly, Short> leaveAsWorkDays;

}