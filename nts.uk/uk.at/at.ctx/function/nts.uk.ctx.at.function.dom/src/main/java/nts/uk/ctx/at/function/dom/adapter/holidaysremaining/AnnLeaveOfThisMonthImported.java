package nts.uk.ctx.at.function.dom.adapter.holidaysremaining;

import java.util.Optional;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class AnnLeaveOfThisMonthImported {
	/**
	 * 付与年月日
	 */
	private GeneralDate grantDate;
	
	/**
	 * 付与日数
	 */
	private Double grantDays;
	
	/**
	 * 月初残日数
	 */
	private double firstMonthRemNumDays;
	
	/**
	 * 月初残時間
	 */
	private int firstMonthRemNumMinutes;
	
	/**
	 * 使用日数
	 */
	private double usedDays;
	
	/**
	 * 使用時間
	 */
	private Optional<Integer> usedMinutes;
	
	/**
	 * 残日数
	 */
	private double remainDays;
	
	/**
	 * 残時間
	 */
	private Optional<Integer> remainMinutes;

	public AnnLeaveOfThisMonthImported(GeneralDate grantDate, Double grantDays, double firstMonthRemNumDays,
			int firstMonthRemNumMinutes, double usedDays, Optional<Integer> usedMinutes, double remainDays,
			Optional<Integer> remainMinutes) {
		super();
		this.grantDate = grantDate;
		this.grantDays = grantDays;
		this.firstMonthRemNumDays = firstMonthRemNumDays;
		this.firstMonthRemNumMinutes = firstMonthRemNumMinutes;
		this.usedDays = usedDays;
		this.usedMinutes = usedMinutes;
		this.remainDays = remainDays;
		this.remainMinutes = remainMinutes;
	}

}
