package nts.uk.ctx.hr.shared.dom.employment;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.calendar.period.DatePeriod;

@AllArgsConstructor
@Data
public class ObjectParam {
	String employmentCode; // 雇用コード
	DatePeriod birthdayPeriod; // 誕生日期間
}
