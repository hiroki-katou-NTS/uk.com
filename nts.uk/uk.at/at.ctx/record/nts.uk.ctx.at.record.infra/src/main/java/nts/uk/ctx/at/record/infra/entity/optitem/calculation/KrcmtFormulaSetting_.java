/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem.calculation;

import java.math.BigDecimal;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KrcmtFormulaSetting_.
 */
@StaticMetamodel(KrcmtFormulaSetting.class)
public class KrcmtFormulaSetting_ {

	/** The krcmt formula setting PK. */
	public static volatile SingularAttribute<KrcmtFormulaSetting, KrcmtFormulaSettingPK> krcmtFormulaSettingPK;

	/** The calc atr. */
	public static volatile SingularAttribute<KrcmtFormulaSetting, Short> calcAtr;

	/** The minus segment. */
	public static volatile SingularAttribute<KrcmtFormulaSetting, Short> minusSegment;

	/** The operator. */
	public static volatile SingularAttribute<KrcmtFormulaSetting, Short> operator;

	/** The left set method. */
	public static volatile SingularAttribute<KrcmtFormulaSetting, Short> leftSetMethod;

	/** The left input val. */
	public static volatile SingularAttribute<KrcmtFormulaSetting, BigDecimal> leftInputVal;

	/** The left formula item id. */
	public static volatile SingularAttribute<KrcmtFormulaSetting, String> leftFormulaItemId;

	/** The right set method. */
	public static volatile SingularAttribute<KrcmtFormulaSetting, Short> rightSetMethod;

	/** The right input val. */
	public static volatile SingularAttribute<KrcmtFormulaSetting, BigDecimal> rightInputVal;

	/** The right formula item id. */
	public static volatile SingularAttribute<KrcmtFormulaSetting, String> rightFormulaItemId;

}
