/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.employment.history;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class BsymtAffEmpHistItem_.
 */
@StaticMetamodel(BsymtAffEmpHistItem.class)
public class BsymtAffEmpHistItem_ {

	/** The his id. */
	public static volatile SingularAttribute<BsymtAffEmpHistItem, String> hisId;

	/** The sid. */
	public static volatile SingularAttribute<BsymtAffEmpHistItem, String> sid;

	/** The emp code. */
	public static volatile SingularAttribute<BsymtAffEmpHistItem, String> empCode;

	/** The salary segment. */
	public static volatile SingularAttribute<BsymtAffEmpHistItem, Integer> salarySegment;

	/** The bsymt employment hist. */
	public static volatile SingularAttribute<BsymtAffEmpHistItem, BsymtAffEmpHist> bsymtAffEmpHist;
}
