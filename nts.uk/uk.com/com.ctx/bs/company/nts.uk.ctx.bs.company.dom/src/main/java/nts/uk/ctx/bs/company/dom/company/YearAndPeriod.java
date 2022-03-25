package nts.uk.ctx.bs.company.dom.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.Year;

/**
* @author sakuratani
*
*			年期間
*         
*/
@Getter
@AllArgsConstructor
public class YearAndPeriod {

	// 年度
	private Year year;

	// 開始日
	private GeneralDate startDate;

	// 終了日
	private GeneralDate endDate;

	// メソッド
	// 終了日を計算して年期間を返す。
	public static YearAndPeriod createByStartDate(Year year, GeneralDate generalDate) {
		return new YearAndPeriod(year, generalDate, generalDate.addYears(1).addDays(-1));
	}

}
