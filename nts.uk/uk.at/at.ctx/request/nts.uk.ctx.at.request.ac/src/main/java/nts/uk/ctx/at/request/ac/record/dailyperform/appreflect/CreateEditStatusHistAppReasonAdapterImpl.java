package nts.uk.ctx.at.request.ac.record.dailyperform.appreflect;

import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.appreflect.CreateEditStatusHistAppReasonPub;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.reflect.CreateEditStatusHistAppReasonAdapter;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;

@Stateless
public class CreateEditStatusHistAppReasonAdapterImpl implements CreateEditStatusHistAppReasonAdapter {

	@Inject
	private CreateEditStatusHistAppReasonPub pub;

	@Override
	public void process(String employeeId, GeneralDate date, String appId, ScheduleRecordClassifi classification,
			Map<Integer, String> mapValue) {
		pub.process(employeeId, date, appId, classification, mapValue);
	}

}
