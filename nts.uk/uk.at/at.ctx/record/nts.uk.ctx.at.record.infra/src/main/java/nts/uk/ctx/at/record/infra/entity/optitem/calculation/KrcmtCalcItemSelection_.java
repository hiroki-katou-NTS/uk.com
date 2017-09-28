/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem.calculation;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KrcmtCalcItemSelection_.
 */
@StaticMetamodel(KrcmtCalcItemSelection.class)
public class KrcmtCalcItemSelection_ {

	/** The krcmt calc item selection PK. */
	public static volatile SingularAttribute<KrcmtCalcItemSelection, KrcmtCalcItemSelectionPK> krcmtCalcItemSelectionPK;

	/** The calc atr. */
	public static volatile SingularAttribute<KrcmtCalcItemSelection, Integer> calcAtr;

	/** The minus segment. */
	public static volatile SingularAttribute<KrcmtCalcItemSelection, Integer> minusSegment;

	/** The operator. */
	public static volatile SingularAttribute<KrcmtCalcItemSelection, Integer> operator;

}
