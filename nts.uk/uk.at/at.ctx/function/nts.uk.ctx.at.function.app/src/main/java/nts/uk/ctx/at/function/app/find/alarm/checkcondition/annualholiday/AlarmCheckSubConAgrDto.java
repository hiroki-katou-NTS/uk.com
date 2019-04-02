package nts.uk.ctx.at.function.app.find.alarm.checkcondition.annualholiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlarmCheckSubConAgrDto {

	private boolean narrowUntilNext;

	private boolean narrowLastDay;

	private Integer numberDayAward;

	private Integer periodUntilNext;
}
