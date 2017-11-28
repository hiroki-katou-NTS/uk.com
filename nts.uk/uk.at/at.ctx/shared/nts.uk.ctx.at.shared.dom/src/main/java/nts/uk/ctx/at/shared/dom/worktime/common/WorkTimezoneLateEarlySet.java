/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class WorkTimezoneLateEarlySet.
 */
//就業時間帯の遅刻・早退設定

/**
 * Gets the other class set.
 *
 * @return the other class set
 */
@Getter
public class WorkTimezoneLateEarlySet extends DomainObject{

	/** The common set. */
	//共通設定
	private EmTimezoneLateEarlyCommonSet commonSet;
	
	/** The other class set. */
	//区分別設定
	private List<OtherEmTimezoneLateEarlySet> otherClassSet;
}
