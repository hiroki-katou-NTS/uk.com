/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.workplace;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class BsymtWorkplaceInfo_.
 */
@StaticMetamodel(BsymtWorkplaceInfo.class)
public class BsymtWorkplaceInfo_ {

	/** The bsymt workplace info PK. */
	public static volatile SingularAttribute<BsymtWorkplaceInfo, BsymtWorkplaceInfoPK> bsymtWorkplaceInfoPK;
	
	/** The wkpcd. */
	public static volatile SingularAttribute<BsymtWorkplaceInfo, String> wkpcd;
	
	/** The bsymt workplace hist. */
	public static volatile SingularAttribute<BsymtWorkplaceInfo, BsymtWorkplaceHist> bsymtWorkplaceHist;
}
