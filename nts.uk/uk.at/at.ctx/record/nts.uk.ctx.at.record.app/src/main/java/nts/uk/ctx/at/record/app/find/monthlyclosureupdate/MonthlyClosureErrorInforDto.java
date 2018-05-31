package nts.uk.ctx.at.record.app.find.monthlyclosureupdate;

import lombok.Value;

/**
 * 
 * @author HungTT
 *
 */

@Value
public class MonthlyClosureErrorInforDto {

	private String employeeCode;
	private String employeeName;
	private String errorMessage;
	private int atr;

}
