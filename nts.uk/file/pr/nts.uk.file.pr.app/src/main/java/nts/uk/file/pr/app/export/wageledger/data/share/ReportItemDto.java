/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.wageledger.data.share;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

/**
 * The Class ReportItemDto.
 */
@Builder
public class ReportItemDto {

	/** The name. */
	public String name;

	/** The monthly datas. */
	public List<MonthlyData> monthlyDatas;
	
	/** The total. */
	@Getter
	private long total;
	
	/**
	 * Calculate total.
	 */
	public void calculateTotal() {
		this.total = this.monthlyDatas.stream().mapToLong(data -> data.amount).sum();
	}
}
