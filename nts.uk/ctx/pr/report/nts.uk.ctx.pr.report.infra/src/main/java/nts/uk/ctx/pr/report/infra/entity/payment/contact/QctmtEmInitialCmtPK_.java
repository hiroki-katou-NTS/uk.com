/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.entity.payment.contact;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class QcmtCommentMonthCp_.
 */
@StaticMetamodel(QctmtEmInitialCmtPK.class)
public class QctmtEmInitialCmtPK_ {

	/** The ccd. */
	public static volatile SingularAttribute<QctmtEmInitialCmtPK, String> ccd;

	/** The emp cd. */
	public static volatile SingularAttribute<QctmtEmInitialCmtPK, String> empCd;

	/** The pay bonus atr. */
	public static volatile SingularAttribute<QctmtEmInitialCmtPK, Integer> payBonusAtr;

	/** The spare pay atr. */
	public static volatile SingularAttribute<QctmtEmInitialCmtPK, Integer> sparePayAtr;

}