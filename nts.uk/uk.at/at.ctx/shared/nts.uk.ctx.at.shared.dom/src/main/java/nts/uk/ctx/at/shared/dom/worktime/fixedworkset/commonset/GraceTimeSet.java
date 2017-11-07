/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedworkset.commonset;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

//猶予時間設定
@Getter
public class GraceTimeSet extends DomainObject {

	//就業時間に含める
	private boolean includeEmTime;
	
	//TODO wait QA 猶予時間
//	private LateEarlyGraceTime graceTime;
}
