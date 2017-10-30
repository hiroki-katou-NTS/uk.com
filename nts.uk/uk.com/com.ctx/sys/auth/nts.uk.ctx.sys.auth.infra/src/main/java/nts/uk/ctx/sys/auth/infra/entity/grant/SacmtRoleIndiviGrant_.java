/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.infra.entity.grant;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class SacmtRoleIndiviGrant_.
 */
@StaticMetamodel(SacmtRoleIndiviGrant.class)
public class SacmtRoleIndiviGrant_ {
	
	/** The sacmt role indivi grant PK. */
	public static volatile SingularAttribute<SacmtRoleIndiviGrant, SacmtRoleIndiviGrantPK> sacmtRoleIndiviGrantPK;
	
	/** The str D. */
	public static volatile SingularAttribute<SacmtRoleIndiviGrant, GeneralDate> strD;
	
	/** The end D. */
	public static volatile SingularAttribute<SacmtRoleIndiviGrant, GeneralDate> endD;
}
