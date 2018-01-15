/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.personallaborcondition;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshmtPerWorkCategory_.
 */
@StaticMetamodel(KshmtPerWorkCategory.class)
public class KshmtPerWorkCategory_ {

	/** The kshmt per work category PK. */
	public static volatile SingularAttribute<KshmtPerWorkCategory, KshmtPerWorkCategoryPK> kshmtPerWorkCategoryPK;
	
	/** The worktype cd. */
	public static volatile SingularAttribute<KshmtPerWorkCategory, String> worktypeCd;
	
	/** The worktime cd. */
	public static volatile SingularAttribute<KshmtPerWorkCategory, String> worktimeCd;
	
	/** The use atr 1. */
	public static volatile SingularAttribute<KshmtPerWorkCategory, Integer> useAtr1;
	
	/** The use atr 2. */
	public static volatile SingularAttribute<KshmtPerWorkCategory, Integer> useAtr2;
	
	/** The cnt 1. */
	public static volatile SingularAttribute<KshmtPerWorkCategory, Integer> cnt1;
	
	/** The cnt 2. */
	public static volatile SingularAttribute<KshmtPerWorkCategory, Integer> cnt2;
	
	/** The end 1. */
	public static volatile SingularAttribute<KshmtPerWorkCategory, Integer> end1;
	
	/** The end 2. */
	public static volatile SingularAttribute<KshmtPerWorkCategory, Integer> end2;
	
	/** The start 1. */
	public static volatile SingularAttribute<KshmtPerWorkCategory, Integer> start1;
	
	/** The start 2. */
	public static volatile SingularAttribute<KshmtPerWorkCategory, Integer> start2;
}