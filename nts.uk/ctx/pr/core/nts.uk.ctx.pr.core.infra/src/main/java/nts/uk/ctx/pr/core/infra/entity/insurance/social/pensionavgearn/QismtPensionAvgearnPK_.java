/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.insurance.social.pensionavgearn;

import java.math.BigDecimal;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class QismtHealthInsuAvgearnPK_.
 */
@StaticMetamodel(QismtPensionAvgearnPK.class)
public class QismtPensionAvgearnPK_ {

	/** The ccd. */
	public static volatile SingularAttribute<QismtPensionAvgearnPK, String> ccd;

	/** The si office cd. */
	public static volatile SingularAttribute<QismtPensionAvgearnPK, String> siOfficeCd;

	/** The hist id. */
	public static volatile SingularAttribute<QismtPensionAvgearnPK, String> histId;

	/** The health insu grade. */
	public static volatile SingularAttribute<QismtPensionAvgearnPK, BigDecimal> pensionGrade;
}