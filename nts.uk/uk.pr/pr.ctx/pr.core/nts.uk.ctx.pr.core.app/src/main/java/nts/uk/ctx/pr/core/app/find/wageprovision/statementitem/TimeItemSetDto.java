package nts.uk.ctx.pr.core.app.find.wageprovision.statementitem;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset.TimeItemSet;

/**
 * 
 * @author thanh.tq 勤怠項目設定
 *
 */
@Value
public class TimeItemSetDto {

	/**
	 * 平均賃金区分
	 */
	private Integer averageWageAtr;

	/**
	 * 年間所定労働日数区分
	 */
	private Integer workingDaysPerYear;

	/**
	 * 時間回数区分
	 */
	private int timeCountAtr;

	/**
	 * 備考
	 */
	private String note;

	public static TimeItemSetDto fromDomain(TimeItemSet domain) {
		return new TimeItemSetDto(domain.getAverageWageAtr().map(i -> i.value).orElse(null),
				domain.getWorkingDaysPerYear().map(i -> i.value).orElse(null), domain.getTimeCountAtr().value,
				domain.getNote().map(i -> i.v()).orElse(null));
	}
}
