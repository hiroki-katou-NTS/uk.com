/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.insurance.social.healthrate;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class QismtHealthInsuAvgearnPK_.
 */
@StaticMetamodel(QismtHealthInsuRatePK.class)
public class QismtHealthInsuRatePK_ {

	/** The ccd. */
	public static volatile SingularAttribute<QismtHealthInsuRatePK, String> ccd;

	/** The si office cd. */
	public static volatile SingularAttribute<QismtHealthInsuRatePK, String> siOfficeCd;

	/** The hist id. */
	public static volatile SingularAttribute<QismtHealthInsuRatePK, String> histId;
}