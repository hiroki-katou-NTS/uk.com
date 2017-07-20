/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KcbmtCompanyWorkSet_.
 */
@StaticMetamodel(KcbmtCompanyWorkSet.class)
public class KcbmtCompanyWorkSet_ {

	/** The cid. */
	public static volatile SingularAttribute<KcbmtCompanyWorkSet, String> cid;

	/** The wd work type code. */
	public static volatile SingularAttribute<KcbmtCompanyWorkSet, String> wdWorkTypeCode;

	/** The wd working code. */
	public static volatile SingularAttribute<KcbmtCompanyWorkSet, String> wdWorkingCode;

	/** The nwd law work type code. */
	public static volatile SingularAttribute<KcbmtCompanyWorkSet, String> nwdLawWorkTypeCode;

	/** The nwd law working code. */
	public static volatile SingularAttribute<KcbmtCompanyWorkSet, String> nwdLawWorkingCode;

	/** The nwd ext work type code. */
	public static volatile SingularAttribute<KcbmtCompanyWorkSet, String> nwdExtWorkTypeCode;

	/** The nwd ext working code. */
	public static volatile SingularAttribute<KcbmtCompanyWorkSet, String> nwdExtWorkingCode;

}
