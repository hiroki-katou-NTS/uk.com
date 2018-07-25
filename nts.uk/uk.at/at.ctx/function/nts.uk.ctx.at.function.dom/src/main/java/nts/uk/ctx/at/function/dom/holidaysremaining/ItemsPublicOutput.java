package nts.uk.ctx.at.function.dom.holidaysremaining;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author thanh.tq 
 * 出力する公休の項目
 */
@Getter
@Setter
@NoArgsConstructor
public class ItemsPublicOutput {
	/**
	 * 公休繰越数を出力する
	 */
	private boolean outputHolidayForward;
	/**
	 * 公休月度残を出力する
	 */
	private boolean monthlyPublic;
	/**
	 * 公休の項目を出力する
	 */
	private boolean outputItemsHolidays;
	public ItemsPublicOutput(boolean outputholidayforward, boolean monthlyPublic, boolean outputitemsholidays) {
		super();
		this.outputHolidayForward = outputholidayforward;
		this.monthlyPublic = monthlyPublic;
		this.outputItemsHolidays = outputitemsholidays;
	}
	
	

}
