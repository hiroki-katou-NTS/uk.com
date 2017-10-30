/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.infra.entity.grant;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class SacmtRoleIndiviGrantPK_.
 */
@StaticMetamodel(SacmtRoleIndiviGrantPK.class)
public class SacmtRoleIndiviGrantPK_ {
	
	/** The cid. */
	public static volatile SingularAttribute<SacmtRoleIndiviGrantPK, String> cid;
	
	/** The user id. */
	public static volatile SingularAttribute<SacmtRoleIndiviGrantPK, String> userId;
	
	/** The role type. */
	public static volatile SingularAttribute<SacmtRoleIndiviGrantPK, String> roleType;
}
