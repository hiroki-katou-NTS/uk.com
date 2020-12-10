package nts.uk.ctx.at.request.dom.application.common.adapter.record.reflect;

import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;

public interface CreateEditStatusHistAppReasonAdapter {

	public void process(String employeeId, GeneralDate date, String appId, ScheduleRecordClassifi classification,
			Map<Integer, String> mapValue);
}
