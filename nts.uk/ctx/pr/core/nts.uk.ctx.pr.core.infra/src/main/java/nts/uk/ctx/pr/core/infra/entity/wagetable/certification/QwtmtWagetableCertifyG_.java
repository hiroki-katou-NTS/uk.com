/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.wagetable.certification;

import java.util.Date;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class QwtmtWagetableCertifyG_.
 */
@StaticMetamodel(QwtmtWagetableCertifyG.class)
public class QwtmtWagetableCertifyG_ {

	/** The qwtmt wagetable certify GPK. */
	public static volatile SingularAttribute<QwtmtWagetableCertifyG, QwtmtWagetableCertifyGPK> qwtmtWagetableCertifyGPK;

	/** The ins date. */
	public static volatile SingularAttribute<QwtmtWagetableCertifyG, Date> insDate;

	/** The ins ccd. */
	public static volatile SingularAttribute<QwtmtWagetableCertifyG, String> insCcd;

	/** The ins scd. */
	public static volatile SingularAttribute<QwtmtWagetableCertifyG, String> insScd;

	/** The ins pg. */
	public static volatile SingularAttribute<QwtmtWagetableCertifyG, String> insPg;

	/** The upd date. */
	public static volatile SingularAttribute<QwtmtWagetableCertifyG, Date> updDate;

	/** The upd ccd. */
	public static volatile SingularAttribute<QwtmtWagetableCertifyG, String> updCcd;

	/** The upd scd. */
	public static volatile SingularAttribute<QwtmtWagetableCertifyG, String> updScd;

	/** The upd pg. */
	public static volatile SingularAttribute<QwtmtWagetableCertifyG, String> updPg;

	/** The exclus ver. */
	public static volatile SingularAttribute<QwtmtWagetableCertifyG, Long> exclusVer;

	/** The certify group name. */
	public static volatile SingularAttribute<QwtmtWagetableCertifyG, String> certifyGroupName;

	/** The multi apply set. */
	public static volatile SingularAttribute<QwtmtWagetableCertifyG, Integer> multiApplySet;

	/** The qwtmt wagetable certify list. */
	public static volatile ListAttribute<QwtmtWagetableCertifyG, QwtmtWagetableCertify> qwtmtWagetableCertifyList;
}