/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.wageledger.data.share;

import java.util.List;

import lombok.Builder;

/**
 * The Class ReportItemDto.
 */
@Builder
public class ReportItemDto {

	/** The name. */
	public String name;

	/** The monthly datas. */
	public List<MonthlyData> monthlyDatas;
}
