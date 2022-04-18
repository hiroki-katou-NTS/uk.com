package nts.uk.ctx.at.record.dom.workrecord.erroralarm.anyperiod;

import java.util.Optional;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.AttendanceItemCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmMessage;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;

/**
 * 任意期間別実績のエラーアラーム
 */
@Value
public class ErrorAlarmAnyPeriod implements DomainAggregate{
	private String companyId;
	
	private ErrorAlarmWorkRecordCode code;
	
	private ErrorAlarmWorkRecordName name;
	
	private AttendanceItemCondition condition;
	
	private Optional<ErrorAlarmMessage> message;
}
