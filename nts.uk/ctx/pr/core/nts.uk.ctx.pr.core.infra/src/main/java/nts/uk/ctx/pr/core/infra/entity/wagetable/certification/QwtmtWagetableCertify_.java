/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.wagetable.certification;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class QwtmtWagetableCertify_.
 */
@StaticMetamodel(QwtmtWagetableCertify.class)
public class QwtmtWagetableCertify_ {

	/** The qwtmt wagetable certify PK. */
	public static volatile SingularAttribute<QwtmtWagetableCertify, QwtmtWagetableCertifyPK> qwtmtWagetableCertifyPK;

	/** The ins date. */
	public static volatile SingularAttribute<QwtmtWagetableCertify, Date> insDate;

	/** The ins ccd. */
	public static volatile SingularAttribute<QwtmtWagetableCertify, String> insCcd;

	/** The ins scd. */
	public static volatile SingularAttribute<QwtmtWagetableCertify, String> insScd;

	/** The ins pg. */
	public static volatile SingularAttribute<QwtmtWagetableCertify, String> insPg;

	/** The upd date. */
	public static volatile SingularAttribute<QwtmtWagetableCertify, Date> updDate;

	/** The upd ccd. */
	public static volatile SingularAttribute<QwtmtWagetableCertify, String> updCcd;

	/** The upd scd. */
	public static volatile SingularAttribute<QwtmtWagetableCertify, String> updScd;

	/** The upd pg. */
	public static volatile SingularAttribute<QwtmtWagetableCertify, String> updPg;

	/** The exclus ver. */
	public static volatile SingularAttribute<QwtmtWagetableCertify, Long> exclusVer;

}