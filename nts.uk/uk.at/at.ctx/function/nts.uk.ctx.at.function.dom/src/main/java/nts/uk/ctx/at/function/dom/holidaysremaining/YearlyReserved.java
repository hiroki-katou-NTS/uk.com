package nts.uk.ctx.at.function.dom.holidaysremaining;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author thanh.tq 出力する積立年休の項目
 */
@Getter
@Setter
@NoArgsConstructor
public class YearlyReserved {
	/**
	 * 積立年休の項目を出力する
	 */
	private boolean yearlyReserved;

	public YearlyReserved(boolean yearlyReserved) {
		super();
		this.yearlyReserved = yearlyReserved;
	}
	

}
