package nts.uk.ctx.at.function.app.find.alarm.checkcondition;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author HungTT
 *
 */

@Data
@AllArgsConstructor
public class DailyErrorAlarmCheckDto {
	private String code;
	private String name;
	private int classification;
	private String message;
}
