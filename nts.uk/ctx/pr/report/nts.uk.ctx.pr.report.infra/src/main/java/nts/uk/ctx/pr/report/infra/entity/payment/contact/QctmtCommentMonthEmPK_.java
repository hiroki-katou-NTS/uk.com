/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.entity.payment.contact;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class QctmtCommentMonthEmPK_.
 */
@StaticMetamodel(QctmtCommentMonthEmPK.class)
public class QctmtCommentMonthEmPK_ {

	/** The ccd. */
	public static volatile SingularAttribute<QctmtCommentMonthEmPK, String> ccd;

	/** The emp cd. */
	public static volatile SingularAttribute<QctmtCommentMonthEmPK, String> empCd;

	/** The pay bonus atr. */
	public static volatile SingularAttribute<QctmtCommentMonthEmPK, Integer> payBonusAtr;

	/** The spare pay atr. */
	public static volatile SingularAttribute<QctmtCommentMonthEmPK, Integer> sparePayAtr;

	/** The processing ym. */
	public static volatile SingularAttribute<QctmtCommentMonthEmPK, Integer> processingYm;

	/** The processing no. */
	public static volatile SingularAttribute<QctmtCommentMonthEmPK, Integer> processingNo;

}
