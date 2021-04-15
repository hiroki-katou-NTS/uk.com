package nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.calendar.period.DatePeriod;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusOfEmployeeAdapterAl {
	private String employeeId;

	private List<DatePeriod> listPeriod;
}
