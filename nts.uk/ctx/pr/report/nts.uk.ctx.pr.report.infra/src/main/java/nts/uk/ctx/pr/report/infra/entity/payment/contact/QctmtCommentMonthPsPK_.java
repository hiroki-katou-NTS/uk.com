/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.entity.payment.contact;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(QctmtCommentMonthPsPK.class)
public class QctmtCommentMonthPsPK_ {
	public static volatile SingularAttribute<QctmtCommentMonthPsPK, String> ccd;
	public static volatile SingularAttribute<QctmtCommentMonthPsPK, String> pId;
	public static volatile SingularAttribute<QctmtCommentMonthPsPK, Integer> processingNo;
	public static volatile SingularAttribute<QctmtCommentMonthPsPK, Integer> payBonusAtr;
	public static volatile SingularAttribute<QctmtCommentMonthPsPK, Integer> sparePayAtr;
	public static volatile SingularAttribute<QctmtCommentMonthPsPK, String> processingYm;
}
