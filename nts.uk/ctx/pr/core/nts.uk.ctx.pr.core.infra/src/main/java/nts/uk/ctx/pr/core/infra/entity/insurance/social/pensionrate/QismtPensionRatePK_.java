/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.insurance.social.pensionrate;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class QismtHealthInsuAvgearnPK_.
 */
@StaticMetamodel(QismtPensionRatePK.class)
public class QismtPensionRatePK_ {

	/** The ccd. */
	public static volatile SingularAttribute<QismtPensionRatePK, String> ccd;

	/** The si office cd. */
	public static volatile SingularAttribute<QismtPensionRatePK, String> siOfficeCd;

	/** The hist id. */
	public static volatile SingularAttribute<QismtPensionRatePK, String> histId;
}