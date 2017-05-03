/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.insurance.social.pensionavgearn;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.uk.ctx.pr.core.infra.entity.insurance.social.pensionrate.QismtPensionRate;

/**
 * The Class QismtPensionAvgearn_.
 */
@StaticMetamodel(QismtPensionAvgearn.class)
public class QismtPensionAvgearn_ {

	/** The qismt pension avgearn PK. */
	public static volatile SingularAttribute<QismtPensionAvgearn, QismtPensionAvgearnPK> qismtPensionAvgearnPK;

	/** The ins date. */
	public static volatile SingularAttribute<QismtPensionAvgearn, Date> insDate;

	/** The ins ccd. */
	public static volatile SingularAttribute<QismtPensionAvgearn, String> insCcd;

	/** The ins scd. */
	public static volatile SingularAttribute<QismtPensionAvgearn, String> insScd;

	/** The ins pg. */
	public static volatile SingularAttribute<QismtPensionAvgearn, String> insPg;

	/** The upd date. */
	public static volatile SingularAttribute<QismtPensionAvgearn, Date> updDate;

	/** The upd ccd. */
	public static volatile SingularAttribute<QismtPensionAvgearn, String> updCcd;

	/** The upd scd. */
	public static volatile SingularAttribute<QismtPensionAvgearn, String> updScd;

	/** The upd pg. */
	public static volatile SingularAttribute<QismtPensionAvgearn, String> updPg;

	/** The exclus ver. */
	public static volatile SingularAttribute<QismtPensionAvgearn, Long> exclusVer;

	/** The pension avg earn. */
	public static volatile SingularAttribute<QismtPensionAvgearn, BigDecimal> pensionAvgEarn;

	/** The pension upper limit. */
	public static volatile SingularAttribute<QismtPensionAvgearn, BigDecimal> pensionUpperLimit;

	/** The p pension male mny. */
	public static volatile SingularAttribute<QismtPensionAvgearn, BigDecimal> pPensionMaleMny;

	/** The p pension fem mny. */
	public static volatile SingularAttribute<QismtPensionAvgearn, BigDecimal> pPensionFemMny;

	/** The p pension miner mny. */
	public static volatile SingularAttribute<QismtPensionAvgearn, BigDecimal> pPensionMinerMny;

	/** The c pension male mny. */
	public static volatile SingularAttribute<QismtPensionAvgearn, Long> cPensionMaleMny;

	/** The c pension fem mny. */
	public static volatile SingularAttribute<QismtPensionAvgearn, BigDecimal> cPensionFemMny;

	/** The c pension miner mny. */
	public static volatile SingularAttribute<QismtPensionAvgearn, BigDecimal> cPensionMinerMny;

	/** The p fund male mny. */
	public static volatile SingularAttribute<QismtPensionAvgearn, BigDecimal> pFundMaleMny;

	/** The p fund fem mny. */
	public static volatile SingularAttribute<QismtPensionAvgearn, BigDecimal> pFundFemMny;

	/** The p fund miner mny. */
	public static volatile SingularAttribute<QismtPensionAvgearn, BigDecimal> pFundMinerMny;

	/** The c fund male mny. */
	public static volatile SingularAttribute<QismtPensionAvgearn, BigDecimal> cFundMaleMny;

	/** The c fund fem mny. */
	public static volatile SingularAttribute<QismtPensionAvgearn, BigDecimal> cFundFemMny;

	/** The c fund miner mny. */
	public static volatile SingularAttribute<QismtPensionAvgearn, BigDecimal> cFundMinerMny;

	/** The p fund exempt male mny. */
	public static volatile SingularAttribute<QismtPensionAvgearn, BigDecimal> pFundExemptMaleMny;

	/** The p fund exempt fem mny. */
	public static volatile SingularAttribute<QismtPensionAvgearn, BigDecimal> pFundExemptFemMny;

	/** The p fund exempt miner mny. */
	public static volatile SingularAttribute<QismtPensionAvgearn, BigDecimal> pFundExemptMinerMny;

	/** The c fund exempt male mny. */
	public static volatile SingularAttribute<QismtPensionAvgearn, BigDecimal> cFundExemptMaleMny;

	/** The c fund exempt fem mny. */
	public static volatile SingularAttribute<QismtPensionAvgearn, BigDecimal> cFundExemptFemMny;

	/** The c fund exempt miner mny. */
	public static volatile SingularAttribute<QismtPensionAvgearn, BigDecimal> cFundExemptMinerMny;

	/** The child contribution mny. */
	public static volatile SingularAttribute<QismtPensionAvgearn, BigDecimal> childContributionMny;

	/** The qismt pension rate. */
	public static volatile SingularAttribute<QismtPensionAvgearn, QismtPensionRate> qismtPensionRate;
}