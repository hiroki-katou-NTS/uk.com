/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem.calculation;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KrcmtAnyf_.
 */
@StaticMetamodel(KrcmtAnyf.class)
public class KrcmtAnyf_ {

	/** The krcmt opt item formula PK. */
	public static volatile SingularAttribute<KrcmtAnyf, KrcmtAnyfPK> krcmtAnyfPK;

	/** The formula name. */
	public static volatile SingularAttribute<KrcmtAnyf, String> formulaName;

	/** The formula atr. */
	public static volatile SingularAttribute<KrcmtAnyf, Integer> formulaAtr;

	/** The calc atr. */
	public static volatile SingularAttribute<KrcmtAnyf, Integer> calcAtr;

	/** The symbol. */
	public static volatile SingularAttribute<KrcmtAnyf, String> symbol;

}
