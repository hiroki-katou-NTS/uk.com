/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.personallaborcondition;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshmtPerLaborCond_.
 */
@StaticMetamodel(KshmtPerLaborCond.class)
public class KshmtPerLaborCond_ {

	/** The kshmt per labor cond PK. */
	public static volatile SingularAttribute<KshmtPerLaborCond, KshmtPerLaborCondPK> kshmtPerLaborCondPK;
	
	/** The is 60 h super hd. */
	public static volatile SingularAttribute<KshmtPerLaborCond, Integer> schedMgmtAtr;
	
	/** The hd add one day. */
	public static volatile SingularAttribute<KshmtPerLaborCond, Integer> hdAddOneDay;
	
	/** The hd add morning. */
	public static volatile SingularAttribute<KshmtPerLaborCond, Integer> hdAddMorning;
	
	/** The hd add afternoon. */
	public static volatile SingularAttribute<KshmtPerLaborCond, Integer> hdAddAfternoon;
	
	/** The auto emboss set atr. */
	public static volatile SingularAttribute<KshmtPerLaborCond, Integer> autoEmbossSetAtr;

}
