package nts.uk.ctx.at.function.dom.adapter.periodofspecialleave;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Setter
@Getter
public class SpecialVacationImported {
	/**
	 * 付与日数
	 */
	private GeneralDate grantDate;
	/**
	 * 月初残日数
	 */
	private Double firstMonthRemNumDays;
	/**
	 * 使用数日数
	 */
	private Double usedDate;
	/**
	 * 残数日数
	 */
	private Double remainDate;
	
	/**
	 * 付与時間
	 */
	private Double grantHours;
	/**
	 * 月初残時間
	 */
	private Double firstMonthRemNumHours;
	/**
	 * 使用数時間
	 */
	private Double usedHours;
	/**
	 * 残数時間
	 */
	private Double remainHours;

	public SpecialVacationImported(GeneralDate grantDate, Double firstMonthRemNumDays, Double usedDate,
			Double remainDate, Double grantHours, Double firstMonthRemNumHours, Double usedHours, Double remainHours) {
		this.grantDate = grantDate;
		this.firstMonthRemNumDays = firstMonthRemNumDays;
		this.usedDate = usedDate;
		this.remainDate = remainDate;
		this.grantHours = grantHours;
		this.firstMonthRemNumHours = firstMonthRemNumHours;
		this.usedHours = usedHours;
		this.remainHours = remainHours;
	}

}
