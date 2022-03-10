package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflect;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.jobmanagement.usagesetting.ManHrInputUsageSetting;
import nts.uk.ctx.at.record.dom.jobmanagement.usagesetting.ManHrInputUsageSettingRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.JudCriteriaSameStampOfSupportRepo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.JudgmentCriteriaSameStampOfSupport;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.AppStampShare;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ICorrectSupportDataWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.IGetAppForCorrectionRuleRequire;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.SupportDataWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.OutputTimeReflectForWorkinfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.TimeReflectFromWorkinfo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsettings.TaskOperationSetting;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.operationsettings.TaskOperationSettingRepository;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSetting;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSettingRepository;

@Stateless
public class SupportWorkReflectionProcess implements ICorrectSupportDataWork {

	@Inject
	private StampCardRepository stampCardRepo;
	
	@Inject
	private StampDakokuRepository stampRepo;
	
	@Inject
	private TaskOperationSettingRepository taskOperationSettingRepository;
	
	@Inject
	private JudCriteriaSameStampOfSupportRepo judCriteriaSameStampOfSupportRepo;
	
	@Inject
	private ManHrInputUsageSettingRepository manHrInputUsageSettingRepository;
	
	@Inject
	private TimeReflectFromWorkinfo timeReflectFromWorkinfo;
	
	@Inject 
	private SupportOperationSettingRepository supportOperationSettingRepo;
	
	@Override
	public SupportDataWork correctSupportDataWork(IGetAppForCorrectionRuleRequire require,
			IntegrationOfDaily integrationOfDaily) {
		RequireImpl impl = new RequireImpl(require);
		return CorrectSupportDataWork.correctSupportDataWork(impl, integrationOfDaily);
	}
	
	@AllArgsConstructor
	public class RequireImpl implements CorrectSupportDataWork.Require {

		private IGetAppForCorrectionRuleRequire require;
		
		@Override
		public List<StampCard> stampCard(String contractCode, String sid) {
			return stampCardRepo.getLstStampCardBySidAndContractCd(contractCode, sid);
		}

		@Override
		public List<Stamp> stamp(List<String> listCard, String companyId, GeneralDateTime startDate,
				GeneralDateTime endDate) {
			return stampRepo.getByDateTimeperiod(listCard, startDate, endDate);
		}

		@Override
		public Optional<TaskOperationSetting> taskOperationSetting(String cid) {
			return taskOperationSettingRepository.getTasksOperationSetting(cid);
		}

		@Override
		public JudgmentCriteriaSameStampOfSupport getJudgmentSameStampOfSupport(String cid) {
			return judCriteriaSameStampOfSupportRepo.get(cid);
		}

		@Override
		public Optional<ManHrInputUsageSetting> getManHrInputUsageSetting(String cId) {
			return manHrInputUsageSettingRepository.get(cId);
		}

		@Override
		public Optional<AppStampShare> getInfoAppStamp() {
			return require.appStamp();
		}

		@Override
		public OutputTimeReflectForWorkinfo getTimeReflectFromWorkinfo(String companyId, String employeeId,
				GeneralDate ymd, WorkInfoOfDailyAttendance workInformation) {
			return timeReflectFromWorkinfo.get(companyId, employeeId, ymd, workInformation);
		}

		@Override
		public SupportOperationSetting supportOperationSettingRepo(String cid) {
			return supportOperationSettingRepo.get(cid);
		}

	}

}
