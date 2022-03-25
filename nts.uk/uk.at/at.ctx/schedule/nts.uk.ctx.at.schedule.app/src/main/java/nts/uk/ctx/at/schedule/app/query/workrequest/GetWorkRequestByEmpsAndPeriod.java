package nts.uk.ctx.at.schedule.app.query.workrequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityDisplayInfoOfOneDay;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityOfOneDay;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityOfOneDayRepository;
import nts.uk.ctx.at.shared.app.workrule.workinghours.TimeSpanForCalcSharedDto;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterRepository;
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
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * «Query» 対象期間に対応する社員リストの勤務希望を取得する
 *         対象期間に対応する勤務希望を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.勤務希望.App.対象期間に対応する社員リストの勤務希望を取得する
 * 
 * @author tutk
 *
 */
@Stateless
public class GetWorkRequestByEmpsAndPeriod {
	@Inject
	private WorkAvailabilityOfOneDayRepository availabilityOfOneDayRepository;

	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;

	@Inject
	private ShiftMasterRepository shiftMasterRepo;

	@Inject
	private WorkTypeRepository workTypeRepo;

	@Inject
	private BasicScheduleService basicScheduleService;

	@Inject
	private FixedWorkSettingRepository fixedWorkSettingRepository;

	@Inject
	private FlowWorkSettingRepository flowWorkSettingRepository;

	@Inject
	private FlexWorkSettingRepository flexWorkSettingRepository;

	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSettingRepository;

	public List<WorkAvailabilityDisplayInfoOfOneDayDto> get(List<String> listEmp, DatePeriod datePeriod) {
		List<WorkAvailabilityOfOneDay> availabilityOfOneDays = new ArrayList<WorkAvailabilityOfOneDay>();

		//1:
		for (int i = 0; i < listEmp.size(); i++) {
			List<WorkAvailabilityOfOneDay> availabilityOfOneDayTmps = availabilityOfOneDayRepository
					.getList(listEmp.get(i), datePeriod);//
			availabilityOfOneDays = Stream.of(availabilityOfOneDays, availabilityOfOneDayTmps).flatMap(x -> x.stream())
					.collect(Collectors.toList());
		}
		//2:
		if (availabilityOfOneDays.isEmpty()) {
			return new ArrayList<>();
		}
		WorkAvailabilityOfOneDay.Require requireWorkAvailabilityOfOneDay = new RequireWorkAvailabilityOfOneDayImpl(
				workTypeRepo, workTimeSettingRepository, basicScheduleService, shiftMasterRepo,
				fixedWorkSettingRepository, flowWorkSettingRepository, flexWorkSettingRepository,
				predetemineTimeSettingRepository);
		List<WorkAvailabilityDisplayInfoOfOneDay> listWorkAvailabilityDisplayInfoOfOneDay = new ArrayList<>();
		//3:
		for (WorkAvailabilityOfOneDay workOneDay : availabilityOfOneDays) {
			listWorkAvailabilityDisplayInfoOfOneDay
					.add(workOneDay.getDisplayInformation(requireWorkAvailabilityOfOneDay));
		}

		return listWorkAvailabilityDisplayInfoOfOneDay.stream().map(c -> convertToDomain(c))
				.collect(Collectors.toList());
	}

	private WorkAvailabilityDisplayInfoOfOneDayDto convertToDomain(WorkAvailabilityDisplayInfoOfOneDay domain) {
		List<String> shiftList = domain.getDisplayInfo().getShiftList().keySet().stream().map(c->c.v()).collect(Collectors.toList());
		return new WorkAvailabilityDisplayInfoOfOneDayDto(domain.getEmployeeId(),
				domain.getAvailabilityDate(), domain.getMemo().v(),
				new WorkAvailabilityDisplayInfoScheduleDto(domain.getDisplayInfo().getMethod().value,
						shiftList,
						domain.getDisplayInfo().getTimeZoneList().stream()
								.map(c -> new TimeSpanForCalcSharedDto(c.getStart().v(), c.getEnd().v()))
								.collect(Collectors.toList())));
	}

	@AllArgsConstructor
	private static class RequireWorkAvailabilityOfOneDayImpl implements WorkAvailabilityOfOneDay.Require {

		private final String companyId = AppContexts.user().companyId();

		@Inject
		private WorkTypeRepository workTypeRepo;

		@Inject
		private WorkTimeSettingRepository workTimeSettingRepository;

		@Inject
		private BasicScheduleService basicScheduleService;

		@Inject
		private ShiftMasterRepository shiftMasterRepo;

		@Inject
		private FixedWorkSettingRepository fixedWorkSettingRepository;

		@Inject
		private FlowWorkSettingRepository flowWorkSettingRepository;

		@Inject
		private FlexWorkSettingRepository flexWorkSettingRepository;

		@Inject
		private PredetemineTimeSettingRepository predetemineTimeSettingRepository;

		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			return basicScheduleService.checkNeededOfWorkTimeSetting(workTypeCode);
		}

		@Override
		public Optional<ShiftMaster> getShiftMasterByWorkInformation(WorkTypeCode workTypeCode,
				WorkTimeCode workTimeCode) {
			Optional<ShiftMaster> data = shiftMasterRepo.getByWorkTypeAndWorkTime(companyId, workTypeCode.v(),
					workTimeCode.v());
			return data;
		}

		@Override
		public List<ShiftMaster> getShiftMaster(List<ShiftMasterCode> shiftMasterCodeList) {
			List<ShiftMaster> data = shiftMasterRepo.getByListShiftMaterCd2(companyId,
					shiftMasterCodeList.stream().map(c -> c.v()).collect(Collectors.toList()));
			return data;
		}

		@Override
		public Optional<WorkType> workType(String companyId, WorkTypeCode workTypeCode) {
			return workTypeRepo.findByPK(companyId, workTypeCode.v());
		}

		@Override
		public Optional<WorkTimeSetting> workTimeSetting(String companyId, WorkTimeCode workTimeCode) {
			return workTimeSettingRepository.findByCode(companyId, workTimeCode.v());
		}

		@Override
		public Optional<FixedWorkSetting> fixedWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return fixedWorkSettingRepository.findByKey(companyId, workTimeCode.v());
		}
		
		@Override
		public Optional<FlowWorkSetting> flowWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return flowWorkSettingRepository.find(companyId, workTimeCode.v());
		}
		
		@Override
		public Optional<FlexWorkSetting> flexWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return flexWorkSettingRepository.find(companyId, workTimeCode.v());
		}
		
		@Override
		public Optional<PredetemineTimeSetting> predetemineTimeSetting(String companyId, WorkTimeCode workTimeCode) {
			return predetemineTimeSettingRepository.findByWorkTimeCode(companyId, workTimeCode.v());
		}

		@Override
		public boolean shiftMasterIsExist(ShiftMasterCode shiftMasterCode) {
			// TODO Auto-generated method stub
			return false;
		}
	}
}
