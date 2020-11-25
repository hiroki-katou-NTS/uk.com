package nts.uk.ctx.at.record.pub.appreflect;

import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;

public interface CreateEditStatusHistAppReasonPub {

	public void process(String employeeId, GeneralDate date, String appId, ScheduleRecordClassifi classification,
			Map<Integer, String> mapValue);
}
