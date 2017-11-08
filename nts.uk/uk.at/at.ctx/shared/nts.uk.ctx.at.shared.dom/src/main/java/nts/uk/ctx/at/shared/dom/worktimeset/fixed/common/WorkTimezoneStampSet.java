/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.fixed.common;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

//就業時間帯の打刻設定
@Getter
public class WorkTimezoneStampSet extends DomainObject{
	
	//丸め設定
	private List<RoundingSet> roundingSet;
}
