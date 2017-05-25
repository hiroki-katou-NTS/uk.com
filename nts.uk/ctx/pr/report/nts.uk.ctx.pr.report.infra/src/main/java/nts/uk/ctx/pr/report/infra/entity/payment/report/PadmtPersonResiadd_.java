/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.entity.payment.report;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class PadmtPersonResiadd_.
 */
@StaticMetamodel(PadmtPersonResiadd.class)
public class PadmtPersonResiadd_ {

	/** The padmt person resiadd PK. */
	public static volatile SingularAttribute<PadmtPersonResiadd, PadmtPersonResiaddPK> padmtPersonResiaddPK;

	/** The ins date. */
	public static volatile SingularAttribute<PadmtPersonResiadd, Date> insDate;

	/** The ins ccd. */
	public static volatile SingularAttribute<PadmtPersonResiadd, String> insCcd;

	/** The ins scd. */
	public static volatile SingularAttribute<PadmtPersonResiadd, String> insScd;

	/** The ins pg. */
	public static volatile SingularAttribute<PadmtPersonResiadd, String> insPg;

	/** The upd ccd. */
	public static volatile SingularAttribute<PadmtPersonResiadd, String> updCcd;

	/** The upd scd. */
	public static volatile SingularAttribute<PadmtPersonResiadd, String> updScd;

	/** The upd pg. */
	public static volatile SingularAttribute<PadmtPersonResiadd, String> updPg;

	/** The exclus ver. */
	public static volatile SingularAttribute<PadmtPersonResiadd, Integer> exclusVer;

	/** The inv scd. */
	public static volatile SingularAttribute<PadmtPersonResiadd, String> invScd;
	
	/** The str D. */
	public static volatile SingularAttribute<PadmtPersonResiadd, Date> strD;
	
	/** The end D. */
	public static volatile SingularAttribute<PadmtPersonResiadd, Date> endD;
	
	/** The exp D. */
	public static volatile SingularAttribute<PadmtPersonResiadd, Date> expD;
	
	/** The postal. */
	public static volatile SingularAttribute<PadmtPersonResiadd, String> postal;
	
	/** The nationality. */
	public static volatile SingularAttribute<PadmtPersonResiadd, String> nationality;
	
	/** The address 1. */
	public static volatile SingularAttribute<PadmtPersonResiadd, String> address1;
	
	/** The address 2. */
	public static volatile SingularAttribute<PadmtPersonResiadd, String> address2;
	
	/** The kn address 1. */
	public static volatile SingularAttribute<PadmtPersonResiadd, String> knAddress1;
	
	/** The kn address 2. */
	public static volatile SingularAttribute<PadmtPersonResiadd, String> knAddress2;
	
	/** The tel no. */
	public static volatile SingularAttribute<PadmtPersonResiadd, String> telNo;
	
	/** The house condition. */
	public static volatile SingularAttribute<PadmtPersonResiadd, String> houseCondition;
	
	/** The rent mny. */
	public static volatile SingularAttribute<PadmtPersonResiadd, Integer> rentMny;
	
	/** The map file. */
	public static volatile SingularAttribute<PadmtPersonResiadd, String> mapFile;

}