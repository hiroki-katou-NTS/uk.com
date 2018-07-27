package nts.uk.ctx.at.function.dom.holidaysremaining;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author thanh.tq 出力する年休の項目
 */
@Getter
@Setter
@NoArgsConstructor
public class YearlyItemsOutput {
	/**
	 * 年休の項目出力する
	 */
	private boolean yearlyHoliday;

	/**
	 * 内時間年休残数を出力する
	 */
	private boolean insideHours;

	/**
	 * ★内半日年休を出力する
	 */
	private boolean insideHalfDay;

	public YearlyItemsOutput(boolean yearlyHoliday, boolean insideHours, boolean insideHalfDay) {
		super();
		this.yearlyHoliday = yearlyHoliday;
		this.insideHours = insideHours;
		this.insideHalfDay = insideHalfDay;
	}
	
	
}
