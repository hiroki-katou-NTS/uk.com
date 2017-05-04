package nts.uk.ctx.pr.report.infra.entity.payment.contact;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(QcmtCommentMonthCp.class)
public class QctmtCommentMonthEm_ {

	/** The qcmt comment month cp PK. */
	public static volatile SingularAttribute<QctmtCommentMonthEm, QctmtCommentMonthEmPK> qctmtCommentMonthEmPK;

	/** The comment. */
	public static volatile SingularAttribute<QctmtCommentMonthEm, String> comment;
}
