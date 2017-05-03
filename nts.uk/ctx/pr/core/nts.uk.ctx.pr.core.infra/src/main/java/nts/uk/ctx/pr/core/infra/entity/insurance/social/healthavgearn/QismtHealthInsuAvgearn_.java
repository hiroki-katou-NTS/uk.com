/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.insurance.social.healthavgearn;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.uk.ctx.pr.core.infra.entity.insurance.social.healthrate.QismtHealthInsuRate;

/**
 * The Class QismtHealthInsuAvgearn_.
 */
@StaticMetamodel(QismtHealthInsuAvgearn.class)
public class QismtHealthInsuAvgearn_ {

	/** The qismt health insu avgearn PK. */
	public static volatile SingularAttribute<QismtHealthInsuAvgearn, QismtHealthInsuAvgearnPK> qismtHealthInsuAvgearnPK;

	/** The ins date. */
	public static volatile SingularAttribute<QismtHealthInsuAvgearn, Date> insDate;

	/** The ins ccd. */
	public static volatile SingularAttribute<QismtHealthInsuAvgearn, String> insCcd;

	/** The ins scd. */
	public static volatile SingularAttribute<QismtHealthInsuAvgearn, String> insScd;

	/** The ins pg. */
	public static volatile SingularAttribute<QismtHealthInsuAvgearn, String> insPg;

	/** The upd date. */
	public static volatile SingularAttribute<QismtHealthInsuAvgearn, Date> updDate;

	/** The upd ccd. */
	public static volatile SingularAttribute<QismtHealthInsuAvgearn, String> updCcd;

	/** The upd scd. */
	public static volatile SingularAttribute<QismtHealthInsuAvgearn, String> updScd;

	/** The upd pg. */
	public static volatile SingularAttribute<QismtHealthInsuAvgearn, String> updPg;

	/** The exclus ver. */
	public static volatile SingularAttribute<QismtHealthInsuAvgearn, Long> exclusVer;

	/** The health insu avg earn. */
	public static volatile SingularAttribute<QismtHealthInsuAvgearn, BigDecimal> healthInsuAvgEarn;

	/** The health insu upper limit. */
	public static volatile SingularAttribute<QismtHealthInsuAvgearn, BigDecimal> healthInsuUpperLimit;

	/** The p health general mny. */
	public static volatile SingularAttribute<QismtHealthInsuAvgearn, BigDecimal> pHealthGeneralMny;

	/** The p health nursing mny. */
	public static volatile SingularAttribute<QismtHealthInsuAvgearn, BigDecimal> pHealthNursingMny;

	/** The p health specific mny. */
	public static volatile SingularAttribute<QismtHealthInsuAvgearn, BigDecimal> pHealthSpecificMny;

	/** The p health basic mny. */
	public static volatile SingularAttribute<QismtHealthInsuAvgearn, BigDecimal> pHealthBasicMny;

	/** The c health general mny. */
	public static volatile SingularAttribute<QismtHealthInsuAvgearn, BigDecimal> cHealthGeneralMny;

	/** The c health nursing mny. */
	public static volatile SingularAttribute<QismtHealthInsuAvgearn, BigDecimal> cHealthNursingMny;

	/** The c health specific mny. */
	public static volatile SingularAttribute<QismtHealthInsuAvgearn, BigDecimal> cHealthSpecificMny;

	/** The c health basic mny. */
	public static volatile SingularAttribute<QismtHealthInsuAvgearn, BigDecimal> cHealthBasicMny;

	/** The qismt health insu rate. */
	public static volatile SingularAttribute<QismtHealthInsuAvgearn, QismtHealthInsuRate> qismtHealthInsuRate;
}