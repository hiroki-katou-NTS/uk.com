/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.workrule.closure;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KclmtClosureHist_.
 */
@StaticMetamodel(KclmtClosureHist.class)
public class KclmtClosureHist_ {

	/** The kclmt closure hist PK. */
	public static volatile SingularAttribute<KclmtClosureHist, KclmtClosureHistPK> kclmtClosureHistPK;
	
	/** The name. */
	public static volatile SingularAttribute<KclmtClosureHist, String> name;
	
	/** The end D. */
	public static volatile SingularAttribute<KclmtClosureHist, Integer> endYM;
	
	/** The close day. */
	public static volatile SingularAttribute<KclmtClosureHist, Integer> closeDay;
	
	/** The is last day. */
	public static volatile SingularAttribute<KclmtClosureHist, Integer> isLastDay;
}
