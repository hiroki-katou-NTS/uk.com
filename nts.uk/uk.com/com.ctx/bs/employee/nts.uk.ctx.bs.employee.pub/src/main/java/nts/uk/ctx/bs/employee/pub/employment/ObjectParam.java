package nts.uk.ctx.bs.employee.pub.employment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ObjectParam {
	String employmentCode;
	DatePeriod birthdayPeriod;
}
