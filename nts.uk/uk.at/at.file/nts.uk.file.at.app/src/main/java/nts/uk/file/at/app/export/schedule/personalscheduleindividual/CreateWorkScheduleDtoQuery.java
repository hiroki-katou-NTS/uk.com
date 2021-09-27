package nts.uk.file.at.app.export.schedule.personalscheduleindividual;

import lombok.val;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.GetListWtypeWtimeUseDailyAttendRecordService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
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
import nts.uk.file.at.app.export.schedule.personalscheduleindividual.dto.WorkScheduleWorkInforDto;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 勤務予定で勤務予定（勤務情報）dtoを作成する
 */
@Stateless
public class CreateWorkScheduleDtoQuery {
    @Inject
    private WorkTypeRepository workTypeRepository;

    @Inject
    private WorkTimeSettingRepository workTimeSettingRepository;
    @Inject
    private BasicScheduleService basicScheduleService;
    @Inject
    private WorkTypeRepository workTypeRepo;
    @Inject
    private FixedWorkSettingRepository fixedWorkSet;
    @Inject
    private FlowWorkSettingRepository flowWorkSet;
    @Inject
    private FlexWorkSettingRepository flexWorkSet;
    @Inject
    private PredetemineTimeSettingRepository predetemineTimeSet;

    /**
     * 取得する     *
     *
     * @param dailyListAttendanceWork
     * @return DatePeriodListDto
     */
    public List<WorkScheduleWorkInforDto> get(List<IntegrationOfDaily> dailyListAttendanceWork) {
        String companyId = AppContexts.user().companyId();
        List<WorkInfoOfDailyAttendance> workInfoOfDailyAttendanceList = dailyListAttendanceWork
                .stream()
                .map(x -> x.getWorkInformation()).collect(Collectors.toList());
       /* //1.日別勤怠の実績で利用する勤務種類と就業時間帯
        val workType = GetListWtypeWtimeUseDailyAttendRecordService.getdata(workInfoOfDailyAttendanceList);
        workInfoOfDailyAttendanceList.stream().map(x -> x.getRecordInfo()).collect(Collectors.toList());

        //2.廃止された勤務種類
        val workTypeList = workTypeRepository.findByCidAndWorkTypeCodes(
                companyId,
                workInfoOfDailyAttendanceList.stream().map(x -> x.getRecordInfo().getWorkTypeCode().v()).collect(Collectors.toList())
        );
        //3.就業時間帯も取得する
        val workTimeList = workTimeSettingRepository.getListWorkTime(
                companyId,
                workInfoOfDailyAttendanceList.stream().map(x -> x.getRecordInfo().getWorkTimeCode().v()).collect(Collectors.toList())
        );*/
        List<WorkScheduleWorkInforDto> workInforDtoList = new ArrayList<>();
        //loop：日別勤怠(Work) in input.List<日別勤怠(Work)>
        for (val ntegrationOfDaily : dailyListAttendanceWork) {
            // 4.1出勤・休日系の判定(@Require)
            val workStyle = ntegrationOfDaily.getWorkInformation().getRecordInfo().getWorkStyle(new WorkInformation.Require() {
                @Override
                public Optional<WorkType> getWorkType(String workTypeCd) {
                    return workTypeRepo.findByPK(companyId, workTypeCd);
                }

                // implements WorkInformation.Require
                @Override
                public Optional<WorkTimeSetting> getWorkTime(String workTimeCode) {
                    return workTimeSettingRepository.findByCode(companyId, workTimeCode);
                }

                // implements WorkInformation.Require
                @Override
                public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
                    return basicScheduleService.checkNeededOfWorkTimeSetting(workTypeCode);
                }

                // implements WorkInformation.Require
                @Override
                public FixedWorkSetting getWorkSettingForFixedWork(WorkTimeCode code) {
                    Optional<FixedWorkSetting> workSetting = fixedWorkSet.findByKey(companyId, code.v());
                    return workSetting.isPresent() ? workSetting.get() : null;
                }

                // implements WorkInformation.Require
                @Override
                public FlowWorkSetting getWorkSettingForFlowWork(WorkTimeCode code) {
                    Optional<FlowWorkSetting> workSetting = flowWorkSet.find(companyId, code.v());
                    return workSetting.isPresent() ? workSetting.get() : null;
                }

                // implements WorkInformation.Require
                @Override
                public FlexWorkSetting getWorkSettingForFlexWork(WorkTimeCode code) {
                    Optional<FlexWorkSetting> workSetting = flexWorkSet.find(companyId, code.v());
                    return workSetting.isPresent() ? workSetting.get() : null;
                }

                // implements WorkInformation.Require
                @Override
                public PredetemineTimeSetting getPredetermineTimeSetting(WorkTimeCode wktmCd) {
                    Optional<PredetemineTimeSetting> workSetting = predetemineTimeSet.findByWorkTimeCode(companyId, wktmCd.v());
                    return workSetting.isPresent() ? workSetting.get() : null;
                }
            });

            String workTypeCode = ntegrationOfDaily.getWorkInformation().getRecordInfo().getWorkTypeCode().v();
            val workTYpeOpt = workTypeRepo.findByPK(companyId, workTypeCode);
            String workTypeName = workTYpeOpt.isPresent() ? workTYpeOpt.get().getAbbreviationName().v() : "";
            String workingHoursCode = ntegrationOfDaily.getWorkInformation().getRecordInfo().getWorkTimeCode().v();
            val workingHourOpt = workTimeSettingRepository.findByCode(companyId, workingHoursCode);
            String workingHoursName = workingHourOpt.isPresent() ? workingHourOpt.get().getWorkTimeDisplayName().getWorkTimeAbName().v() : "";
            Integer startTime = null;
            Integer endTime = null;
            if (ntegrationOfDaily.getAttendanceLeave().isPresent()) {
                val startTimeOpt2 = ntegrationOfDaily.getAttendanceLeave().get().getTimeLeavingWorks().stream().filter(x -> x.getWorkNo().v() == 1).findFirst();
                if (startTimeOpt2.isPresent()) {
                    if (startTimeOpt2.get().getAttendanceStamp().isPresent()) {
                        startTime = startTimeOpt2.get().getAttendanceStamp().get().getTimeVacation().get().getStart().hour();
                    }
                    if (startTimeOpt2.get().getLeaveStamp().isPresent()) {
                        endTime = startTimeOpt2.get().getLeaveStamp().get().getTimeVacation().get().getStart().hour();
                    }
                }
            }
            workInforDtoList.add(
                    new WorkScheduleWorkInforDto(
                            ntegrationOfDaily.getYmd(),
                            workStyle,
                            Optional.of(workTypeCode),
                            Optional.of(workTypeName),
                            Optional.of(workingHoursCode),
                            Optional.of(workingHoursName),
                            Optional.ofNullable(startTime),
                            Optional.ofNullable(endTime)
                    )
            );

        }
        return workInforDtoList;
    }
}
