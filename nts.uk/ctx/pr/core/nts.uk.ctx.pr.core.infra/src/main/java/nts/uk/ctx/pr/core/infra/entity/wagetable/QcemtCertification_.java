/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.wagetable;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class QcemtCertification_.
 */
@StaticMetamodel(QcemtCertification.class)
public class QcemtCertification_ {

	/** The qcemt certification PK. */
	public static volatile SingularAttribute<QcemtCertification, QcemtCertificationPK> qcemtCertificationPK;

	/** The ins date. */
	public static volatile SingularAttribute<QcemtCertification, Date> insDate;

	/** The ins ccd. */
	public static volatile SingularAttribute<QcemtCertification, String> insCcd;

	/** The ins scd. */
	public static volatile SingularAttribute<QcemtCertification, String> insScd;

	/** The ins pg. */
	public static volatile SingularAttribute<QcemtCertification, String> insPg;

	/** The upd date. */
	public static volatile SingularAttribute<QcemtCertification, Date> updDate;

	/** The upd ccd. */
	public static volatile SingularAttribute<QcemtCertification, String> updCcd;

	/** The upd scd. */
	public static volatile SingularAttribute<QcemtCertification, String> updScd;

	/** The upd pg. */
	public static volatile SingularAttribute<QcemtCertification, String> updPg;

	/** The exclus ver. */
	public static volatile SingularAttribute<QcemtCertification, Long> exclusVer;

	/** The name. */
	public static volatile SingularAttribute<QcemtCertification, String> name;
}