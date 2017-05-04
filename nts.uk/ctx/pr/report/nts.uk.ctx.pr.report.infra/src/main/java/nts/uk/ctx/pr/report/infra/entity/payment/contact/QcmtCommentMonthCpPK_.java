/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.entity.payment.contact;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class QcmtCommentMonthCpPK_.
 */
@StaticMetamodel(QcmtCommentMonthCpPK.class)
public class QcmtCommentMonthCpPK_ {

	/** The ccd. */
	public static volatile SingularAttribute<QcmtCommentMonthCpPK, String> ccd;

	/** The processing no. */
	public static volatile SingularAttribute<QcmtCommentMonthCpPK, Integer> processingNo;

	/** The pay bonus atr. */
	public static volatile SingularAttribute<QcmtCommentMonthCpPK, Integer> payBonusAtr;

	/** The processing ym. */
	public static volatile SingularAttribute<QcmtCommentMonthCpPK, Integer> processingYm;

	/** The spare pay atr. */
	public static volatile SingularAttribute<QcmtCommentMonthCpPK, Integer> sparePayAtr;

}