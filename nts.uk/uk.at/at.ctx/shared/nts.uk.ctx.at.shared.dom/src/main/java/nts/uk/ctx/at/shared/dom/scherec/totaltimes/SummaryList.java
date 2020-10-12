/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.totaltimes;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 集計対象一覧
 */
@Getter
@Setter
public class SummaryList {

	/** 勤務種類一覧 */
	private List<String> workTypeCodes;

	/** 就業時間帯一覧 */
	private List<String> workTimeCodes;
	
}
