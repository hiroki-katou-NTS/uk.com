/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.insurance.social.healthavgearn;

import java.math.BigDecimal;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class QismtHealthInsuAvgearnPK_.
 */
@StaticMetamodel(QismtHealthInsuAvgearnPK.class)
public class QismtHealthInsuAvgearnPK_ {

	/** The ccd. */
	public static volatile SingularAttribute<QismtHealthInsuAvgearnPK, String> ccd;

	/** The si office cd. */
	public static volatile SingularAttribute<QismtHealthInsuAvgearnPK, String> siOfficeCd;

	/** The hist id. */
	public static volatile SingularAttribute<QismtHealthInsuAvgearnPK, String> histId;

	/** The health insu grade. */
	public static volatile SingularAttribute<QismtHealthInsuAvgearnPK, BigDecimal> healthInsuGrade;
}