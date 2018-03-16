/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class LegalAggrSetOfRegNew.
 */
@Getter
// 通常勤務の法定内集計設定
public class RegularWorkTimeAggrSet extends DomainObject {

	/** The aggregate time set. */
	// 集計時間設定
	private ExcessOutsideTimeSetReg aggregateTimeSet;

	/** The excess outside time set. */
	// 時間外超過設定
	private ExcessOutsideTimeSetReg excessOutsideTimeSet;

}
