package nts.uk.ctx.at.record.pubimp.appreflect;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.applicationcancel.CreateEditStatusHistAppReason;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.editstate.repository.EditStateOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.pub.appreflect.CreateEditStatusHistAppReasonPub;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.cancellation.ApplicationReflectHistory;

@Stateless
public class CreateEditStatusHistAppReasonPubImpl implements CreateEditStatusHistAppReasonPub {

	@Inject
	private EditStateOfDailyPerformanceRepository editStateOfDailyRepo;

	@Override
	public void process(String employeeId, GeneralDate date, String appId, ScheduleRecordClassifi classification,
			Map<Integer, String> mapValue) {
		RequireImpl impl = new RequireImpl(editStateOfDailyRepo);
		CreateEditStatusHistAppReason.process(impl, employeeId, date, appId, classification, mapValue);

	}

	@AllArgsConstructor
	public class RequireImpl implements CreateEditStatusHistAppReason.Require {

		private final EditStateOfDailyPerformanceRepository editStateOfDailyRepo;

		@Override
		public List<EditStateOfDailyPerformance> findByKey(String employeeId, GeneralDate ymd) {
			return editStateOfDailyRepo.findByKey(employeeId, ymd);
		}

		@Override
		public void addAndUpdate(List<EditStateOfDailyPerformance> editStates) {
			editStateOfDailyRepo.addAndUpdate(editStates);

		}

		@Override
		public void insertAppReflectHist(ApplicationReflectHistory hist) {
			// TODO Auto-generated method stub

		}

	}
}
