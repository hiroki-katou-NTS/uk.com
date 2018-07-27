/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.totaltimes;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class SummaryList.
 */
@Getter
@Setter
public class SummaryList {

	/** The work type codes. */
	// 勤務種類一覧
	private List<String> workTypeCodes;

	/** The work time codes. */
	// 就業時間帯一覧
	private List<String> workTimeCodes;
}
