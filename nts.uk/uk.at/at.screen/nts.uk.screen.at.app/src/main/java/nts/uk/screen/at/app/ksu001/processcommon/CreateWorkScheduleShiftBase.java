/**
 *
 */
package nts.uk.screen.at.app.ksu001.processcommon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.employeeworkway.EmployeeWorkingStatus;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.GetWorkInforUsedDailyAttenRecordService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ColorCodeChar6;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.GetCombinationrAndWorkHolidayAtrService;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.Remarks;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterDisInfor;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterName;
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
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.screen.at.app.ksu001.displayinshift.ShiftMasterMapWithWorkStyle;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.ScheduleOfShiftDto;
import nts.uk.screen.at.app.ksu001.start.SupportCategory;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 * ScreenQuery: 勤務実績で勤務予定（シフト）dtoを作成する
 * Path: UKDesign.UniversalK.就業.KSU_スケジュール.KSU001_個人スケジュール修正(職場別).個人別と共通の処理.勤務実績で勤務予定（シフト）dtoを作成する
 *
 */
@Stateless
public class CreateWorkScheduleShiftBase {

	@Inject
	private WorkTypeRepository workTypeRepo;
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepo;
	@Inject
	private BasicScheduleService basicScheduleService;
	@Inject
	private WorkTimeSettingService workTimeSettingService;
	@Inject
	private ShiftMasterRepository shiftMasterRepo;
	@Inject
	private FixedWorkSettingRepository fixedWorkSet;
	@Inject
	private FlowWorkSettingRepository flowWorkSet;
	@Inject
	private FlexWorkSettingRepository flexWorkSet;
	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSet;

	public WorkScheduleShiftBaseResult getWorkScheduleShiftBase(
			Map<EmployeeWorkingStatus, Optional<IntegrationOfDaily>> mapDataDaily,
			List<ShiftMasterMapWithWorkStyle> listShiftMasterNotNeedGetNew) {

		List<WorkInfoOfDailyAttendance> workInfoOfDailyAttendances = new ArrayList<WorkInfoOfDailyAttendance>();
		mapDataDaily.forEach((k, v) -> {
			if (v.isPresent()) {
				WorkInfoOfDailyAttendance workInfo = v.get().getWorkInformation();
				if (workInfo != null) {
					if (workInfo.getRecordInfo().getWorkTypeCode() != null) {
						workInfoOfDailyAttendances.add(workInfo);
					}
				}
			}
		});

		// call 日別勤怠の実績で利用する勤務情報のリストを取得する
		List<WorkInformation> lstWorkInfo = GetWorkInforUsedDailyAttenRecordService.getListWorkInfo(workInfoOfDailyAttendances);

		// call シフトマスタと出勤休日区分の組み合わせを取得する
		GetCombinationrAndWorkHolidayAtrService.Require requireImpl2 = new RequireCombiAndWorkHolidayImpl(shiftMasterRepo, basicScheduleService, workTypeRepo,workTimeSettingRepo,workTimeSettingService, fixedWorkSet, flowWorkSet, flexWorkSet, predetemineTimeSet);
		Map<ShiftMaster,Optional<WorkStyle>> mapShiftMasterWithWorkStyle = GetCombinationrAndWorkHolidayAtrService.getbyWorkInfo(requireImpl2,AppContexts.user().companyId(), lstWorkInfo);

		List<ShiftMasterMapWithWorkStyle> listShiftMaster = listShiftMasterNotNeedGetNew;
		List<String> listShiftMasterCodeNotNeedGetNew = listShiftMasterNotNeedGetNew.stream().map(mapper -> mapper.getShiftMasterCode()).collect(Collectors.toList()); // ko cần get mới

		for (Map.Entry<ShiftMaster, Optional<WorkStyle>> entry : mapShiftMasterWithWorkStyle.entrySet()) {
			String shiftMasterCd = entry.getKey().getShiftMasterCode().toString();
			if(listShiftMasterCodeNotNeedGetNew.contains(shiftMasterCd)){
				// remove di, roi add lai
				ShiftMasterMapWithWorkStyle obj = listShiftMaster.stream().filter(shiftLocal -> shiftLocal.shiftMasterCode.equals(shiftMasterCd)).findFirst().get();
				listShiftMaster.remove(obj);
			}
			ShiftMasterMapWithWorkStyle shift = new ShiftMasterMapWithWorkStyle(entry.getKey(), entry.getValue().isPresent() ? entry.getValue().get().value + "" : null);
			listShiftMaster.add(shift);
		}

		// loop：日別実績 in 管理状態と勤務実績Map.values()
		List<ScheduleOfShiftDto> listWorkScheduleShift = new ArrayList<>();
		mapDataDaily.forEach((k, v) -> {
			EmployeeWorkingStatus key = k;
			Optional<IntegrationOfDaily> value = v;

			// step 4.1
			boolean needToWork = key.getWorkingStatus().needCreateWorkSchedule();
			// 4.2
			if (value.isPresent()) {
				IntegrationOfDaily daily = value.get();
				if(daily.getWorkInformation() != null){
					WorkInformation workInformation = daily.getWorkInformation().getRecordInfo();

					String workTypeCode = workInformation.getWorkTypeCode() == null ? null : workInformation.getWorkTypeCode().toString().toString();
					String workTimeCode = workInformation.getWorkTimeCode() == null ? null : workInformation.getWorkTimeCode().toString().toString();

					Optional<ShiftMasterMapWithWorkStyle> shiftMaster = listShiftMaster.stream().filter(x -> {
						boolean s = Objects.equals(x.workTypeCode, workTypeCode);
						boolean y = Objects.equals(x.workTimeCode, workTimeCode);
						return s&&y;
					}).findFirst();

					if(!shiftMaster.isPresent()){
						System.out.println("Daily : workType - workTime chua dc dang ky: " + workTypeCode + " - " + workTimeCode);
					}

					ScheduleOfShiftDto dto = ScheduleOfShiftDto.builder()
							.employeeId(key.getEmployeeID())
							.date(key.getDate())
							.haveData(true)
							.achievements(true)
							.confirmed(true)
							.needToWork(needToWork)
							.supportCategory(SupportCategory.NotCheering.value)
							.shiftCode(shiftMaster.isPresent() ? shiftMaster.get().shiftMasterCode : null)
							.shiftName(shiftMaster.isPresent() ? shiftMaster.get().shiftMasterName : null)
							.shiftEditState(null)
							.workHolidayCls(null)
							.isEdit(false)
							.isActive(false)
							.conditionAa1(false)
							.conditionAa2(false)
							.build();
					listWorkScheduleShift.add(dto);
				}
			}
		});

		// convert list to Map
		Map<ShiftMaster, Optional<WorkStyle>> mapShiftMasterWithWorkStyle2 = new HashMap<>();
		String companyId = AppContexts.user().companyId();
		for (ShiftMasterMapWithWorkStyle obj : listShiftMaster) {
			ShiftMasterDisInfor displayInfor = new ShiftMasterDisInfor(new ShiftMasterName(obj.shiftMasterName),new ColorCodeChar6(obj.color),new ColorCodeChar6(obj.color), Optional.of(new Remarks(obj.remark)));
			//TODO 取り込みコード追加
			ShiftMaster ShiftMaster = new ShiftMaster(companyId, new ShiftMasterCode(obj.shiftMasterCode), displayInfor,obj.workTypeCode, obj.workTimeCode, Optional.empty());
			mapShiftMasterWithWorkStyle2.put(ShiftMaster, obj.workStyle == null ? Optional.empty(): Optional.of(EnumAdaptor.valueOf(Integer.valueOf(obj.workStyle), WorkStyle.class)));
		}
		return new WorkScheduleShiftBaseResult(listWorkScheduleShift, mapShiftMasterWithWorkStyle2);
	}

	@AllArgsConstructor
	private static class RequireCombiAndWorkHolidayImpl implements GetCombinationrAndWorkHolidayAtrService.Require {

		private final String companyId = AppContexts.user().companyId();

		@Inject
		private ShiftMasterRepository shiftMasterRepo;
		@Inject
		private BasicScheduleService service;
		@Inject
		private WorkTypeRepository workTypeRepo;
		@Inject
		private WorkTimeSettingRepository workTimeSettingRepository;
		@Inject
		private WorkTimeSettingService workTimeSettingService;
		@Inject
		private FixedWorkSettingRepository fixedWorkSet;
		@Inject
		private FlowWorkSettingRepository flowWorkSet;
		@Inject
		private FlexWorkSettingRepository flexWorkSet;
		@Inject
		private PredetemineTimeSettingRepository predetemineTimeSet;

		@Override
		public List<ShiftMaster> getByListEmp(String companyID, List<String> lstShiftMasterCd) {
			List<ShiftMaster> data = shiftMasterRepo.getByListShiftMaterCd2(companyId, lstShiftMasterCd);
			return data;
		}

		@Override
		public List<ShiftMaster> getByListWorkInfo(String companyId, List<WorkInformation> lstWorkInformation) {
			List<ShiftMaster> data = shiftMasterRepo.get(companyId, lstWorkInformation);
			return data;
		}

		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			 return service.checkNeededOfWorkTimeSetting(workTypeCode);
		}

		@Override
		public Optional<WorkType> workType(String companyId, WorkTypeCode workTypeCode) {
			return workTypeRepo.findByPK(companyId, workTypeCode.v());
		}

		@Override
		public Optional<WorkTimeSetting> workTimeSetting(String companyId, WorkTimeCode workTimeCode) {
			return workTimeSettingRepository.findByCode(companyId, workTimeCode.v());
		}

//		@Override
//		public PredetermineTimeSetForCalc getPredeterminedTimezone(String workTypeCd, String workTimeCd, Integer workNo) {
//			return workTimeSettingService .getPredeterminedTimezone(companyId, workTimeCd, workTypeCd, workNo);
//		}

		@Override
		public Optional<FixedWorkSetting> fixedWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return fixedWorkSet.findByKey(companyId, workTimeCode.v());
		}
		@Override
		public Optional<FlowWorkSetting> flowWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return flowWorkSet.find(companyId, workTimeCode.v());
		}
		@Override
		public Optional<FlexWorkSetting> flexWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return flexWorkSet.find(companyId, workTimeCode.v());
		}
		@Override
		public Optional<PredetemineTimeSetting> predetemineTimeSetting(String companyId, WorkTimeCode workTimeCode) {
			return predetemineTimeSet.findByWorkTimeCode(companyId, workTimeCode.v());
		}

	}

}
