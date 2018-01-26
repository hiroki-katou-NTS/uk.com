/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.classification.affiliate_ver1;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class BsymtAffClassHistory_Ver1_.
 */
@StaticMetamodel(BsymtAffClassHistory_Ver1.class)
public class BsymtAffClassHistory_Ver1_ {

	/** The history id. */
	public static volatile SingularAttribute<BsymtAffClassHistory_Ver1, String> historyId;

	/** The cid. */
	public static volatile SingularAttribute<BsymtAffClassHistory_Ver1, String> cid;

	/** The sid. */
	public static volatile SingularAttribute<BsymtAffClassHistory_Ver1, String> sid;

	/** The start date. */
	public static volatile SingularAttribute<BsymtAffClassHistory_Ver1, GeneralDate> startDate;

	/** The end date. */
	public static volatile SingularAttribute<BsymtAffClassHistory_Ver1, GeneralDate> endDate;
	
	/** The bsymt aff class hist item. */
	public static volatile SingularAttribute<BsymtAffClassHistory_Ver1,  BsymtAffClassHistItem_Ver1> bsymtAffClassHistItem;

}
