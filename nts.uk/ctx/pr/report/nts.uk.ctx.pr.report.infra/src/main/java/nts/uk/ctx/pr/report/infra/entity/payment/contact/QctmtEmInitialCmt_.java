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
@StaticMetamodel(QctmtEmInitialCmt.class)
public class QctmtEmInitialCmt_ {

	/** The qcmt comment month cp PK. */
	public static volatile SingularAttribute<QctmtEmInitialCmt, QctmtEmInitialCmtPK> qctmtEmInitialCmtPK;

	/** The comment. */
	public static volatile SingularAttribute<QctmtEmInitialCmt, String> comment;

}