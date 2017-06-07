/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.closure;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class Closure.
 */
// 締め
@Getter
public class Closure extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The closure id. */
	// 締めＩＤ
	private Integer closureId;

	/** The use classification. */
	// 使用区分
	private UseClassification useClassification;

	/** The month. */
	// 当月
	private ClosureMonth month;

	/** The closure histories. */
	// 締め変更履歴
	private List<ClosureHistory> closureHistories;
}
