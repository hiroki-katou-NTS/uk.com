package nts.uk.ctx.at.record.dom.standardtime;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmOneMonth;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorOneMonth;

/**
 * 
 * @author nampt
 *
 */
@Getter
public class AgreementMonthSetting extends AggregateRoot {

	private String employeeId;

	private YearMonth yearMonthValue;

	private ErrorOneMonth errorOneMonth;

	private AlarmOneMonth alarmOneMonth;

	public AgreementMonthSetting(String employeeId, YearMonth yearMonthValue, ErrorOneMonth errorOneMonth,
			AlarmOneMonth alarmOneMonth) {
		super();
		this.employeeId = employeeId;
		this.yearMonthValue = yearMonthValue;
		this.errorOneMonth = errorOneMonth;
		this.alarmOneMonth = alarmOneMonth;
	}

	public static AgreementMonthSetting createFromJavaType(String employeeId, BigDecimal yearMonthValue,
			int errorOneMonth, int alarmOneMonth) {
		return new AgreementMonthSetting(employeeId, new YearMonth(yearMonthValue.intValue()),
				new ErrorOneMonth(errorOneMonth), new AlarmOneMonth(alarmOneMonth));
	}
	
	public void validate(){
		if(alarmOneMonth.v().compareTo(errorOneMonth.v()) > 0){
			throw new BusinessException("Msg_59");
		}
	}

}
