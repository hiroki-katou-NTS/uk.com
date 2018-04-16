package nts.uk.ctx.at.function.dom.holidaysremaining;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author thanh.tq 出力する特別休暇
 */
@AllArgsConstructor
@Getter
public class SpecialHolidayOutput {

	/**
	 * コード
	 */
	private String code;

	private String holidayCode;

	private String companyID;
	
}
