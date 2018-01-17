/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.classification.affiliate_ver1;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class BsymtAffClassHistItem_Ver1_.
 */
@StaticMetamodel(BsymtAffClassHistItem_Ver1.class)
public class BsymtAffClassHistItem_Ver1_ {

	/** The history id. */
	public static volatile SingularAttribute<BsymtAffClassHistItem_Ver1, String> historyId;

	/** The sid. */
	public static volatile SingularAttribute<BsymtAffClassHistItem_Ver1, String> sid;

	/** The classification code. */
	public static volatile SingularAttribute<BsymtAffClassHistItem_Ver1, String> classificationCode;
	
	/** The bsymt aff class history. */
	public static volatile SingularAttribute<BsymtAffClassHistItem_Ver1, BsymtAffClassHistory_Ver1> bsymtAffClassHistory;

}
