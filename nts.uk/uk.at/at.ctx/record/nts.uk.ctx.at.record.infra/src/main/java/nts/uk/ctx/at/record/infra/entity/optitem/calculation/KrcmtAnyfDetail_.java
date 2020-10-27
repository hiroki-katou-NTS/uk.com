/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem.calculation;

import java.math.BigDecimal;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KrcmtAnyfDetail_.
 */
@StaticMetamodel(KrcmtAnyfDetail.class)
public class KrcmtAnyfDetail_ {

	/** The krcmt formula setting PK. */
	public static volatile SingularAttribute<KrcmtAnyfDetail, KrcmtAnyfDetailPK> krcmtAnyfDetailPK;

	/** The calc atr. */
	public static volatile SingularAttribute<KrcmtAnyfDetail, Integer> calcAtr;

	/** The minus segment. */
	public static volatile SingularAttribute<KrcmtAnyfDetail, Integer> minusSegment;

	/** The operator. */
	public static volatile SingularAttribute<KrcmtAnyfDetail, Integer> operator;

	/** The left set method. */
	public static volatile SingularAttribute<KrcmtAnyfDetail, Integer> leftSetMethod;

	/** The left input val. */
	public static volatile SingularAttribute<KrcmtAnyfDetail, BigDecimal> leftInputVal;

	/** The left formula item id. */
	public static volatile SingularAttribute<KrcmtAnyfDetail, String> leftFormulaItemId;

	/** The right set method. */
	public static volatile SingularAttribute<KrcmtAnyfDetail, Integer> rightSetMethod;

	/** The right input val. */
	public static volatile SingularAttribute<KrcmtAnyfDetail, BigDecimal> rightInputVal;

	/** The right formula item id. */
	public static volatile SingularAttribute<KrcmtAnyfDetail, String> rightFormulaItemId;

}
