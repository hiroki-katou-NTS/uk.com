/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.personallaborcondition;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshmtWorkcondCtgegory_.
 */
@StaticMetamodel(KshmtWorkcondCtgegory.class)
public class KshmtWorkcondCtgegory_ {

	/** The kshmt per work category PK. */
	public static volatile SingularAttribute<KshmtWorkcondCtgegory, KshmtWorkcondCtgegoryPK> kshmtWorkcondCtgegoryPK;
	
	/** The worktype cd. */
	public static volatile SingularAttribute<KshmtWorkcondCtgegory, String> worktypeCd;
	
	/** The worktime cd. */
	public static volatile SingularAttribute<KshmtWorkcondCtgegory, String> worktimeCd;
	
	/** The use atr 1. */
	public static volatile SingularAttribute<KshmtWorkcondCtgegory, Integer> useAtr1;
	
	/** The use atr 2. */
	public static volatile SingularAttribute<KshmtWorkcondCtgegory, Integer> useAtr2;
	
	/** The cnt 1. */
	public static volatile SingularAttribute<KshmtWorkcondCtgegory, Integer> cnt1;
	
	/** The cnt 2. */
	public static volatile SingularAttribute<KshmtWorkcondCtgegory, Integer> cnt2;
	
	/** The end 1. */
	public static volatile SingularAttribute<KshmtWorkcondCtgegory, Integer> end1;
	
	/** The end 2. */
	public static volatile SingularAttribute<KshmtWorkcondCtgegory, Integer> end2;
	
	/** The start 1. */
	public static volatile SingularAttribute<KshmtWorkcondCtgegory, Integer> start1;
	
	/** The start 2. */
	public static volatile SingularAttribute<KshmtWorkcondCtgegory, Integer> start2;
}