/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimeset;

import lombok.Builder;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;

/**
 * The Class WorkTimeDisplayName.
 */
//就業時間帯の表示名
@Getter
@Builder
public class WorkTimeDisplayName extends WorkTimeDomainObject {
	
	/** The work time name. */
	//名称
	private WorkTimeName workTimeName;
	
	/** The work time ab name. */
	//略名
	private WorkTimeAbName workTimeAbName;
	
	/** The work time symbol. */
	//記号
	private WorkTimeSymbol workTimeSymbol;
}
