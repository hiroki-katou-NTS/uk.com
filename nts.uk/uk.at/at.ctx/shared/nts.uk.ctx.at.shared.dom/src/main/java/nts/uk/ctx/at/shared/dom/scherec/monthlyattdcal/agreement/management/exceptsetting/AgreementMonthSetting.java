package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;

/**
 * ３６協定年月設定
 * @author nampt
 */
@Getter
public class AgreementMonthSetting extends AggregateRoot {

	/** 社員ID　*/
	private String employeeId;

	/** 年月 */
	private YearMonth yearMonthValue;

	/** １ヶ月時間 */
	private OneMonthErrorAlarmTime oneMonthTime;


	public AgreementMonthSetting(String employeeId, YearMonth yearMonthValue,
			OneMonthErrorAlarmTime oneMonthTime) {
		super();
		this.employeeId = employeeId;
		this.yearMonthValue = yearMonthValue;
		this.oneMonthTime = oneMonthTime;
	}

	public static AgreementMonthSetting createFromJavaType(String employeeId, BigDecimal yearMonthValue,
			int errorOneMonth, int alarmOneMonth) {
		return new AgreementMonthSetting(employeeId,
										new YearMonth(yearMonthValue.intValue()),
										OneMonthErrorAlarmTime.from(
												new AgreementOneMonthTime(errorOneMonth),
												new AgreementOneMonthTime(alarmOneMonth)));
	}

	public void validate(){
		if(oneMonthTime.getAlarm().v().compareTo(oneMonthTime.getError().v()) > 0){
			throw new BusinessException("Msg_59", "KMK008_43", "KMK008_42");
		}
	}

}
