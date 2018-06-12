package nts.uk.ctx.at.function.dom.adapter.reserveleave;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReserveHolidayImported {

	/** 月初残 */
	private Double startMonthRemain;
	/** 付与数 */
	private Double grantNumber;
	/** 使用数 */
	private Double usedNumber;
	/** 残数 */
	private Double remainNumber;
	/** 未消化数 */
	private Double undigestNumber;
	
	public ReserveHolidayImported(Double startMonthRemain, Double grantNumber, Double usedNumber,
			Double remainNumber, Double undigestNumber) {
		this.startMonthRemain = startMonthRemain;
		this.grantNumber = grantNumber;
		this.usedNumber = usedNumber;
		this.remainNumber = remainNumber;
		this.undigestNumber = undigestNumber;
	}
	
	
}


