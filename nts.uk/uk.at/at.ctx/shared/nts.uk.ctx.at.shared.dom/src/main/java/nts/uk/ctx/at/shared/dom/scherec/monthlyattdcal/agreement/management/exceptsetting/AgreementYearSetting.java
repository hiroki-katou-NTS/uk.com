package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;

/**
 * ３６協定年度設定
 * @author nampt
 */
@Getter
public class AgreementYearSetting extends AggregateRoot{

	/** 社員ID */
	private String employeeId;

	/** 年度 */
	private Year yearValue;

	/** １年間時間 */
	private OneYearErrorAlarmTime oneYearTime;

	public AgreementYearSetting(String employeeId, int yearValue, OneYearErrorAlarmTime oneYearTime) {
		super();
		this.employeeId = employeeId;
		this.yearValue = new Year(yearValue);
		this.oneYearTime = oneYearTime;
	}

	public static AgreementYearSetting createFromJavaType(String employeeId, int yearvalue, int errorOneYear, int alarmOneYear){
		return new AgreementYearSetting(employeeId, yearvalue,
										OneYearErrorAlarmTime.from(
												new AgreementOneYearTime(errorOneYear),
												new AgreementOneYearTime(alarmOneYear)));
	}

	public void validate(){
		if(oneYearTime.getAlarm().v().compareTo(oneYearTime.getError().v()) > 0){
			throw new BusinessException("Msg_59", "KMK008_43", "KMK008_42");
		}
	}

}
