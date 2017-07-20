/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KcbmtClassifyWorkSet_.
 */
@StaticMetamodel(KcbmtClassifyWorkSet.class)
public class KcbmtClassifyWorkSet_ {
	
	/** The kcbmt classify work set PK. */
	public static volatile SingularAttribute<KcbmtClassifyWorkSet, KcbmtClassifyWorkSetPK> kcbmtClassifyWorkSetPK;

	/** The wd work type code. */
	public static volatile SingularAttribute<KcbmtClassifyWorkSet, String> wdWorkTypeCode;

	/** The wd working code. */
	public static volatile SingularAttribute<KcbmtClassifyWorkSet, String> wdWorkingCode;

	/** The nwd law work type code. */
	public static volatile SingularAttribute<KcbmtClassifyWorkSet, String> nwdLawWorkTypeCode;

	/** The nwd law working code. */
	public static volatile SingularAttribute<KcbmtClassifyWorkSet, String> nwdLawWorkingCode;

	/** The nwd ext work type code. */
	public static volatile SingularAttribute<KcbmtClassifyWorkSet, String> nwdExtWorkTypeCode;

	/** The nwd ext working code. */
	public static volatile SingularAttribute<KcbmtClassifyWorkSet, String> nwdExtWorkingCode;
}
