/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.work.executionlog;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class ReCreateContent.
 */
// 再作成内容

@Getter
public class ReCreateContent extends DomainObject{
	
	/** The process execution atr. */
	// 処理実行区分
	private ProcessExecutionAtr processExecutionAtr;
	
	/** The reset atr. */
	// 再設定区分
	private ResetAtr resetAtr;

}
