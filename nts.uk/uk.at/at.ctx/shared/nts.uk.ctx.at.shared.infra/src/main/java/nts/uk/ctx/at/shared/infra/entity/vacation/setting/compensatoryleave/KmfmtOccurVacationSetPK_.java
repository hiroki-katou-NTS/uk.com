/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(KmfmtOccurVacationSetPK.class)
public class KmfmtOccurVacationSetPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KmfmtOccurVacationSetPK, String> cid;

	/** The occurr division. */
	public static volatile SingularAttribute<KmfmtOccurVacationSetPK, Integer> occurrDivision;

}