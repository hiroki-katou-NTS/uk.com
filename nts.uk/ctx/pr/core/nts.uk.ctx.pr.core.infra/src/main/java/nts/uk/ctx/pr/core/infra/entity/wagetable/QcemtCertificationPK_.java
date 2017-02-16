/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.wagetable;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class QcemtCertification_.
 */
@StaticMetamodel(QcemtCertificationPK.class)
public class QcemtCertificationPK_ {

	/** The ccd. */
	public static volatile SingularAttribute<QcemtCertificationPK, String> ccd;

	/** The cert cd. */
	public static volatile SingularAttribute<QcemtCertificationPK, String> certCd;

}