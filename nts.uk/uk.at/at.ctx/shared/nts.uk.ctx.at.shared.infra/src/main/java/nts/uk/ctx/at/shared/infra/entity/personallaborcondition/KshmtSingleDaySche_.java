/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.personallaborcondition;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshmtSingleDaySche_.
 */
@StaticMetamodel(KshmtSingleDaySche.class)
public class KshmtSingleDaySche_ {

	/** The kshmt single day sche PK. */
	public static volatile SingularAttribute<KshmtSingleDaySche, KshmtSingleDaySchePK> kshmtSingleDaySchePK;
	
	/** The worktype cd. */
	public static volatile SingularAttribute<KshmtSingleDaySche, String> worktypeCd;
	
	/** The worktime cd. */
	public static volatile SingularAttribute<KshmtSingleDaySche, String> worktimeCd;
	
	/** The use atr 1. */
	public static volatile SingularAttribute<KshmtSingleDaySche, Integer> useAtr1;
	
	/** The use atr 2. */
	public static volatile SingularAttribute<KshmtSingleDaySche, Integer> useAtr2;
	
	/** The cnt 1. */
	public static volatile SingularAttribute<KshmtSingleDaySche, Integer> cnt1;
	
	/** The cnt 2. */
	public static volatile SingularAttribute<KshmtSingleDaySche, Integer> cnt2;
	
	/** The end 1. */
	public static volatile SingularAttribute<KshmtSingleDaySche, Integer> end1;
	
	/** The end 2. */
	public static volatile SingularAttribute<KshmtSingleDaySche, Integer> end2;
	
	/** The start 1. */
	public static volatile SingularAttribute<KshmtSingleDaySche, Integer> start1;
	
	/** The start 2. */
	public static volatile SingularAttribute<KshmtSingleDaySche, Integer> start2;
}