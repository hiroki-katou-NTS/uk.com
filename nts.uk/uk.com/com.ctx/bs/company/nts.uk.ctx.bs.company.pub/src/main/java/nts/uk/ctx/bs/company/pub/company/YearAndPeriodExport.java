package nts.uk.ctx.bs.company.pub.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.Year;

/**
* @author sakuratani
*
*			年期間Export
*
*/
@Getter
@AllArgsConstructor
public class YearAndPeriodExport {

	// 年度
	private Year year;

	// 開始日
	private GeneralDate startDate;

	// 終了日
	private GeneralDate endDate;

}
