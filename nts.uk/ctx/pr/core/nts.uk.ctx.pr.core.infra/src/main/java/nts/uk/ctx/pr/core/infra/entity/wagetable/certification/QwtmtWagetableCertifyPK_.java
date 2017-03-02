/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.wagetable.certification;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class QwtmtWagetableCertify_.
 */
@StaticMetamodel(QwtmtWagetableCertifyPK.class)
public class QwtmtWagetableCertifyPK_ {

	/** The ccd. */
	public static volatile SingularAttribute<QwtmtWagetableCertifyPK, String> ccd;

	/** The certify group cd. */
	public static volatile SingularAttribute<QwtmtWagetableCertifyPK, String> certifyGroupCd;

	/** The certify cd. */
	public static volatile SingularAttribute<QwtmtWagetableCertifyPK, String> certifyCd;

}