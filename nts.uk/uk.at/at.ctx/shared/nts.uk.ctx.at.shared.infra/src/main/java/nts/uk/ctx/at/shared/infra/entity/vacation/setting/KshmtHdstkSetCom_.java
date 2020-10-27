/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshmtHdstkSetCom_.
 */
@StaticMetamodel(KshmtHdstkSetCom.class)
public class KshmtHdstkSetCom_ {

	/** The year amount. */
	public static volatile SingularAttribute<KshmtHdstkSetCom, Short> yearAmount;
	
	/** The max days retention. */
	public static volatile SingularAttribute<KshmtHdstkSetCom, Short> maxDaysRetention;
	
	/** The cid. */
	public static volatile SingularAttribute<KshmtHdstkSetCom, String> cid;
	
	/** The leave as work days. */
	public static volatile SingularAttribute<KshmtHdstkSetCom, Short> leaveAsWorkDays;

}