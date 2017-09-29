/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.work.executionlog;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;

/**
 * The Class ExecutionContent.
 */
// 実行内容
@Getter
public class ExecutionContent extends DomainObject{

	/** The content. */
	// 実施内容
	private Content content;
	
	/** The copy start date. */
	// コピー開始日
	private GeneralDate copyStartDate;
	
	// 作成方法区分
	private CreateMethodAtr createMethodAtr;
}
