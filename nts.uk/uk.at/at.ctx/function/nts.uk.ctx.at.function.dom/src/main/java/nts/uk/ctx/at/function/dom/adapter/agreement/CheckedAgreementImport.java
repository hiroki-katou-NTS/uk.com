package nts.uk.ctx.at.function.dom.adapter.agreement;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.ErrorAlarm;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.MessageDisp;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.Period;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Builder
@Setter
@Getter
public class CheckedAgreementImport {
	private String employeeId;
	private DatePeriod datePeriod;
	private String alarmCheckId;
	private final boolean error = true;	
	private Period period;
	/** エラーアラーム */
	private ErrorAlarm errorAlarm;
	/** 表示するメッセージ */
	private MessageDisp messageDisp;
}
