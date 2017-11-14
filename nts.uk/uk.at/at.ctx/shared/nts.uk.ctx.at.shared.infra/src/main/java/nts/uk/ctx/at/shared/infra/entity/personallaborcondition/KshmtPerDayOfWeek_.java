/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.personallaborcondition;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshmtPerDayOfWeek_.
 */
@StaticMetamodel(KshmtPerDayOfWeek.class)
public class KshmtPerDayOfWeek_ {

	/** The kshmt per day of week PK. */
	public static volatile SingularAttribute<KshmtPerDayOfWeek, KshmtPerDayOfWeekPK> kshmtPerDayOfWeekPK;
	
	/** The worktype cd. */
	public static volatile SingularAttribute<KshmtPerDayOfWeek, String> worktypeCd;
	
	/** The worktime cd. */
	public static volatile SingularAttribute<KshmtPerDayOfWeek, String> worktimeCd;
	
	/** The use atr 1. */
	public static volatile SingularAttribute<KshmtPerDayOfWeek, Integer> useAtr1;
	
	/** The use atr 2. */
	public static volatile SingularAttribute<KshmtPerDayOfWeek, Integer> useAtr2;
	
	/** The cnt 1. */
	public static volatile SingularAttribute<KshmtPerDayOfWeek, Integer> cnt1;
	
	/** The cnt 2. */
	public static volatile SingularAttribute<KshmtPerDayOfWeek, Integer> cnt2;
	
	/** The end 1. */
	public static volatile SingularAttribute<KshmtPerDayOfWeek, Integer> end1;
	
	/** The end 2. */
	public static volatile SingularAttribute<KshmtPerDayOfWeek, Integer> end2;
	
	/** The start 1. */
	public static volatile SingularAttribute<KshmtPerDayOfWeek, Integer> start1;
	
	/** The start 2. */
	public static volatile SingularAttribute<KshmtPerDayOfWeek, Integer> start2;
}