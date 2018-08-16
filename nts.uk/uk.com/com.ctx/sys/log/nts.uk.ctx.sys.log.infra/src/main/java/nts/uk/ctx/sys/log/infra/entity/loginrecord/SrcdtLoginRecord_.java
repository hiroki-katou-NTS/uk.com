/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.log.infra.entity.loginrecord;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class SgwmtLogoutDataPK_.
 */
@StaticMetamodel(SrcdtLoginRecord.class)
public class SrcdtLoginRecord_ {

	/** The operation id. */
	public static volatile SingularAttribute<SrcdtLoginRecord, SrcdtLoginRecordPK> srcdtLoginRecordPK;

	/** The lock status. */
	public static volatile SingularAttribute<SrcdtLoginRecord, Integer> lockStatus;
	
    /** The login method. */
    public static volatile SingularAttribute<SrcdtLoginRecord, Integer> loginMethod;
    
	/** The login status. */
	public static volatile SingularAttribute<SrcdtLoginRecord, Integer> loginStatus;
	
    /** The remarks. */
    public static volatile SingularAttribute<SrcdtLoginRecord, String> remarks;
    
    /** The url. */
    public static volatile SingularAttribute<SrcdtLoginRecord, String> url;
}