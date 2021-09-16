package nts.uk.ctx.at.shared.dom.scherec.workinfo;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInfoAndTimeZone;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

@Stateless
public class GetWorkInfoTimeZoneFromProcess {

	@Inject
	private WorkTypeRepository workTypeRepo;

	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;

	@Inject
	private BasicScheduleService basicScheduleService;

	@Inject
	private FlexWorkSettingRepository flexWorkSettingRepository;

	@Inject
	private FixedWorkSettingRepository fixedWorkSettingRepository;

	@Inject
	private FlowWorkSettingRepository flowWorkSettingRepository;

	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSettingRepository;

	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;

	@Inject
	private GetWorkInfo getWorkInfo;

	@Inject
	private GetWorkInfoSchedule getWorkInfoSchedule;

	public Optional<WorkInfoAndTimeZone> getInfo(String cid, String employeeId, GeneralDate baseDate,
			Optional<WorkingConditionItem> workItem) {
		RequireImpl impl = new RequireImpl(cid, workTypeRepo, workTimeSettingRepository, basicScheduleService,
				flexWorkSettingRepository, fixedWorkSettingRepository, flowWorkSettingRepository,
				predetemineTimeSettingRepository, workingConditionItemRepository, getWorkInfo, getWorkInfoSchedule);
		return GetWorkInfoTimeZoneFromRcSc.getInfo(impl, cid, employeeId, baseDate, workItem);
	}

	@AllArgsConstructor
	public class RequireImpl implements GetWorkInfoTimeZoneFromRcSc.Require {

		private final String companyId;

		private final WorkTypeRepository workTypeRepo;

		private final WorkTimeSettingRepository workTimeSettingRepository;

		private final BasicScheduleService basicScheduleService;

		private final FlexWorkSettingRepository flexWorkSettingRepository;

		private final FixedWorkSettingRepository fixedWorkSettingRepository;

		private final FlowWorkSettingRepository flowWorkSettingRepository;

		private final PredetemineTimeSettingRepository predetemineTimeSettingRepository;

		private final WorkingConditionItemRepository workingConditionItemRepository;

		private final GetWorkInfo getWorkInfo;

		private final GetWorkInfoSchedule getWorkInfoSchedule;

		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			return basicScheduleService.checkNeededOfWorkTimeSetting(workTypeCode);
		}

		@Override
		public Optional<WorkType> getWorkType(String workTypeCd) {
			return workTypeRepo.findByPK(companyId, workTypeCd);
		}

		@Override
		public Optional<WorkTimeSetting> getWorkTime(String workTimeCode) {
			return workTimeSettingRepository.findByCode(companyId, workTimeCode);
		}

		@Override
		public FixedWorkSetting getWorkSettingForFixedWork(WorkTimeCode code) {
			return fixedWorkSettingRepository.findByKey(companyId, code.v()).get();
		}

		@Override
		public FlowWorkSetting getWorkSettingForFlowWork(WorkTimeCode code) {
			return flowWorkSettingRepository.find(companyId, code.v()).get();
		}

		@Override
		public FlexWorkSetting getWorkSettingForFlexWork(WorkTimeCode code) {
			return flexWorkSettingRepository.find(companyId, code.v()).get();
		}

		@Override
		public PredetemineTimeSetting getPredetermineTimeSetting(WorkTimeCode wktmCd) {
			return predetemineTimeSettingRepository.findByWorkTimeCode(companyId, wktmCd.v()).get();
		}

		@Override
		public Optional<WorkingConditionItem> getWorkingConditionItem(String employeeId, GeneralDate baseDate) {
			return workingConditionItemRepository.getBySidAndStandardDate(employeeId, baseDate);
		}

		@Override
		public Optional<WorkInformation> getWorkInfoSc(String employeeId, GeneralDate baseDate) {
			return getWorkInfoSchedule.getWorkInfoSc(employeeId, baseDate);
		}

		@Override
		public Optional<WorkInformation> getWorkInfoRc(String employeeId, GeneralDate baseDate) {
			return getWorkInfo.getRecord(new CacheCarrier(), employeeId, baseDate);
		}

	}
}
