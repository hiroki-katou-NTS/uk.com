/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.work.executionlog;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class ReCreatedInfo.
 */
// 再作成情報
@Getter
public class ReCreatedInfo extends DomainObject{

	/** The re create content. */
	// 再作成内容
	private ReCreateContent reCreateContent;
	
	/** The re create atr. */
	// 再作成区分
	private ReCreateAtr reCreateAtr;
}
