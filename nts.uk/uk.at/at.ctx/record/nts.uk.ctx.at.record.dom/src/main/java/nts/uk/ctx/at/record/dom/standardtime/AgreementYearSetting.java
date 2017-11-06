package nts.uk.ctx.at.record.dom.standardtime;

import lombok.Getter;
import nts.arc.error.BusinessException;
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

	private int yearValue;
	
	private ErrorOneYear errorOneYear;
	
	private AlarmOneYear alarmOneYear;

	public AgreementYearSetting(String employeeId, int yearValue, ErrorOneYear errorOneYear,
			AlarmOneYear alarmOneYear) {
		super();
		this.employeeId = employeeId;
		this.yearValue = yearValue;
		this.errorOneYear = errorOneYear;
		this.alarmOneYear = alarmOneYear;
	}
	
	public static AgreementYearSetting createFromJavaType(String employeeId, int yearvalue, int errorOneYear, int alarmOneYear){
		return new AgreementYearSetting(employeeId, yearvalue, new ErrorOneYear(errorOneYear), new AlarmOneYear(alarmOneYear));
	}
	
	public void validate(){
		if(alarmOneYear.v().compareTo(errorOneYear.v()) > 0){
			throw new BusinessException("Msg_59");
		}
	}
	
}
