package nts.uk.ctx.at.function.app.command.alarm.checkcondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.app.find.alarm.checkcondition.annualholiday.AlarmCheckConAgrDto;
import nts.uk.ctx.at.function.app.find.alarm.checkcondition.annualholiday.AlarmCheckSubConAgrDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnualHolidayAlarmConditionCommand {
   private AlarmCheckConAgrDto alarmCheckConAgr;
   private AlarmCheckSubConAgrDto alarmCheckSubConAgr;
}
