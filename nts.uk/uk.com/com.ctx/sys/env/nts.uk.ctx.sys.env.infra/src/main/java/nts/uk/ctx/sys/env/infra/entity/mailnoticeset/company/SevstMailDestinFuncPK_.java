/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class SevstMailDestinFuncPK_.
 */
@StaticMetamodel(SevstMailDestinFuncPK.class)
public class SevstMailDestinFuncPK_ {

	/** The cid. */
	public static volatile SingularAttribute<SevstMailDestinFuncPK, String> cid;

	/** The setting item. */
	public static volatile SingularAttribute<SevstMailDestinFuncPK, Integer> settingItem;

	/** The function id. */
	public static volatile SingularAttribute<SevstMailDestinFuncPK, Integer> functionId;

}
