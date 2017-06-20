package nts.uk.ctx.at.record.dom.standardtime;

import java.time.Year;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmOneYear;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorOneYear;

/**
 * 
 * @author nampt
 *
 */
@Getter
public class AgreementYearSetting extends AggregateRoot{

	private String employeeId;

	private Year yearValue;
	
	private ErrorOneYear errorOneYear;
	
	private AlarmOneYear alarmOneYear;

	public AgreementYearSetting(String employeeId, Year yearValue, ErrorOneYear errorOneYear,
			AlarmOneYear alarmOneYear) {
		super();
		this.employeeId = employeeId;
		this.yearValue = yearValue;
		this.errorOneYear = errorOneYear;
		this.alarmOneYear = alarmOneYear;
	}
	
	
	
}
