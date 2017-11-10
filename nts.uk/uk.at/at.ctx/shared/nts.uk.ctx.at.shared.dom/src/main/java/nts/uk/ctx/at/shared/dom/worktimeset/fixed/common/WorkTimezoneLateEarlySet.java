/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.fixed.common;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

//就業時間帯の遅刻・早退設定
@Getter
public class WorkTimezoneLateEarlySet extends DomainObject{

	//共通設定
	private EmTimezoneLateEarlyCommonSet commonSet;
	
	//区分別設定
	private List<OtherEmTimezoneLateEarlySet> otherClassSet;
}
