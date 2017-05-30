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
@StaticMetamodel(QcmtCommentMonthCp.class)
public class QcmtCommentMonthCp_ {

	/** The qcmt comment month cp PK. */
	public static volatile SingularAttribute<QcmtCommentMonthCp, QcmtCommentMonthCpPK> qcmtCommentMonthCpPK;

	/** The comment. */
	public static volatile SingularAttribute<QcmtCommentMonthCp, String> comment;

}