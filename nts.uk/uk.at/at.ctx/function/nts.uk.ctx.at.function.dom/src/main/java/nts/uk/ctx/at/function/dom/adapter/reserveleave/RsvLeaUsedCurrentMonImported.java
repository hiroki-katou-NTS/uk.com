package nts.uk.ctx.at.function.dom.adapter.reserveleave;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;

@Getter
@Setter
public class RsvLeaUsedCurrentMonImported {

	/** 年月 */
	private YearMonth yearMonth;
	/** 月度使用数 */
	private Double usedNumber;
	/** 月度残日数 */
	private Double remainNumber;

	public RsvLeaUsedCurrentMonImported(YearMonth yearMonth, Double usedNumber, Double remainNumber) {
		this.yearMonth = yearMonth;
		this.usedNumber = usedNumber;
		this.remainNumber = remainNumber;
	}

}
