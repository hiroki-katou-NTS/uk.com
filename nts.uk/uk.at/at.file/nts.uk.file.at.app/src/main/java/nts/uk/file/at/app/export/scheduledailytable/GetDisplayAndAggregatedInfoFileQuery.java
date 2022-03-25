package nts.uk.file.at.app.export.scheduledailytable;

import nts.arc.primitive.PrimitiveValueBase;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.aggregation.dom.adapter.dailyrecord.DailyRecordAdapter;
import nts.uk.ctx.at.aggregation.dom.adapter.workschedule.WorkScheduleAdapter;
import nts.uk.ctx.at.aggregation.dom.common.DailyAttendanceGettingService;
import nts.uk.ctx.at.aggregation.dom.common.ScheRecGettingAtr;
import nts.uk.ctx.at.aggregation.dom.scheduledailytable.PersonCounterTimesNumberCounterResult;
import nts.uk.ctx.at.aggregation.dom.scheduledailytable.ScheduleDailyTablePersonCounterService;
import nts.uk.ctx.at.aggregation.dom.scheduledailytable.ScheduleDailyTableWorkplaceCounterService;
import nts.uk.ctx.at.aggregation.dom.scheduledailytable.WorkplaceCounterTimesNumberCounterResult;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesRepository;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterRepository;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.dto.ShiftMasterDto;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 表示情報と集計情報を取得する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetDisplayAndAggregatedInfoFileQuery {
    @Inject
    private DailyRecordAdapter dailyRecordAdapter;
    @Inject
    private WorkScheduleAdapter workScheduleAdapter;

    @Inject
    private TotalTimesRepository totalTimesRepo;

    @Inject
    private ShiftMasterRepository shiftMasterRepo;

    @Inject
    private WorkTypeRepository workTypeRepo;

    @Inject
    private AttendanceItemConvertFactory factory;

    /**
     * 取得する
     * @param employeeIds 社員IDリスト
     * @param targetPeriod 対処期間
     * @param printTarget 印刷対象
     * @param personalCounters 個人計
     * @param workplaceCounters 職場計
     * @return 職場グループに関係する表示情報dto
     */
    public EmployeeDisplayAndAggregatedInfoDto get(List<String> employeeIds, DatePeriod targetPeriod, int printTarget, List<Integer> personalCounters, List<Integer> workplaceCounters) {
        String companyId = AppContexts.user().companyId();
        ScheRecGettingAtr targetData;
        if (printTarget == 0) targetData = ScheRecGettingAtr.ONLY_SCHEDULE;
        else if (printTarget == 1) targetData = ScheRecGettingAtr.ONLY_RECORD;
        else targetData = ScheRecGettingAtr.SCHEDULE_WITH_RECORD;
        Map<ScheRecGettingAtr, List<IntegrationOfDaily>> dailyIntegrationMap = DailyAttendanceGettingService.get(
                new DailyAttendanceGettingService.Require() {
                    @Override
                    public List<IntegrationOfDaily> getSchduleList(List<EmployeeId> empIds, DatePeriod period) {
                        return workScheduleAdapter.getList(empIds.stream().map(PrimitiveValueBase::v).collect(Collectors.toList()), period);
                    }
                    @Override
                    public List<IntegrationOfDaily> getRecordList(List<EmployeeId> empIds, DatePeriod period) {
                        return dailyRecordAdapter.getDailyRecordByScheduleManagement(empIds.stream().map(PrimitiveValueBase::v).collect(Collectors.toList()), period);
                    }
                },
                employeeIds.stream().map(EmployeeId::new).collect(Collectors.toList()),
                targetPeriod,
                targetData
        );

        List<PersonCounterTimesNumberCounterResult> personalCounterResult = ScheduleDailyTablePersonCounterService.aggregate(
                new ScheduleDailyTablePersonCounterService.Require() {
                    @Override
                    public Optional<WorkType> workType(String companyId, String workTypeCd) {
                        return workTypeRepo.findByPK(companyId, workTypeCd);
                    }
                    @Override
                    public DailyRecordToAttendanceItemConverter createDailyConverter() {
                        return factory.createDailyConverter();
                    }
                    @Override
                    public List<TotalTimes> getTotalTimesList(List<Integer> totalTimeNos) {
                        return totalTimesRepo.getTotalTimesDetailByListNo(companyId, totalTimeNos);
                    }
                },
                targetData,
                personalCounters,
                dailyIntegrationMap
        );

        List<WorkplaceCounterTimesNumberCounterResult> workplaceCounterResult = ScheduleDailyTableWorkplaceCounterService.aggregate(
                new ScheduleDailyTableWorkplaceCounterService.Require() {
                    @Override
                    public Optional<WorkType> workType(String companyId, String workTypeCd) {
                        return workTypeRepo.findByPK(companyId, workTypeCd);
                    }
                    @Override
                    public DailyRecordToAttendanceItemConverter createDailyConverter() {
                        return factory.createDailyConverter();
                    }
                    @Override
                    public List<TotalTimes> getTotalTimesList(List<Integer> totalTimeNos) {
                        return totalTimesRepo.getTotalTimesDetailByListNo(companyId, totalTimeNos);
                    }
                },
                workplaceCounters,
                dailyIntegrationMap.get(targetData)
        );

        Set<Integer> totalNos = new HashSet<>();
        totalNos.addAll(personalCounters);
        totalNos.addAll(workplaceCounters);
        List<TotalTimes> totalTimesList = totalTimesRepo.getTotalTimesDetailByListNo(companyId, new ArrayList<>(totalNos));
        List<TotalTimes> personalTotalTimes = new ArrayList<>();
        personalCounters.forEach(c -> {
            totalTimesList.stream().filter(t -> t.getTotalCountNo() == c).findFirst().ifPresent(i -> {
                personalTotalTimes.add(i);
            });
        });
        List<TotalTimes> workplaceTotalTimes = new ArrayList<>();
        workplaceCounters.forEach(c -> {
            totalTimesList.stream().filter(t -> t.getTotalCountNo() == c).findFirst().ifPresent(i -> {
                workplaceTotalTimes.add(i);
            });
        });

        List<ShiftMasterDto> shiftMasters = shiftMasterRepo.getAllByCid(companyId);
        List<WorkType> workTypes = workTypeRepo.findByCompanyId(companyId);
        WorkInformation.Require require = new WorkInformation.Require() {
            @Override
            public Optional<WorkType> workType(String companyId, WorkTypeCode workTypeCode) {
                return workTypeRepo.findByPK(companyId, workTypeCode.v());
            }

            @Override
            public Optional<PredetemineTimeSetting> predetemineTimeSetting(String companyId, WorkTimeCode workTimeCode) {
                return Optional.empty();
            }

            @Override
            public Optional<FlowWorkSetting> flowWorkSetting(String companyId, WorkTimeCode workTimeCode) {
                return Optional.empty();
            }

            @Override
            public Optional<FlexWorkSetting> flexWorkSetting(String companyId, WorkTimeCode workTimeCode) {
                return Optional.empty();
            }

            @Override
            public Optional<FixedWorkSetting> fixedWorkSetting(String companyId, WorkTimeCode workTimeCode) {
                return Optional.empty();
            }

            @Override
            public Optional<WorkTimeSetting> workTimeSetting(String companyId, WorkTimeCode workTimeCode) {
                return Optional.empty();
            }

            @Override
            public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
                return null;
            }

        };
        List<ShiftDisplayInfoDto> shiftDisplayInfos = new ArrayList<>();
        dailyIntegrationMap.forEach((key, value) -> {
            if (key != ScheRecGettingAtr.SCHEDULE_WITH_RECORD) {
                value.forEach(integrationOfDaily -> {
                    Optional<ShiftMasterDto> shiftMaster = shiftMasters.stream()
                            .filter(sh -> {
                                if (sh.getWorkTypeCd().equals(integrationOfDaily.getWorkInformation().getRecordInfo().getWorkTypeCode().v())) {
                                    if (integrationOfDaily.getWorkInformation().getRecordInfo().getWorkTimeCode() == null)
                                        return StringUtil.isNullOrEmpty(sh.getWorkTimeCd(), true);
                                    else
                                        return integrationOfDaily.getWorkInformation().getRecordInfo().getWorkTimeCode().v().equals(sh.getWorkTimeCd());
                                }
                                return false;
                            })
                            .findFirst();
                    Optional<WorkStyle> workStyle = Optional.empty();
                    if (shiftMaster.isPresent()) {
                        workStyle = integrationOfDaily.getWorkInformation().getRecordInfo().getWorkStyle(require,AppContexts.user().companyId());
                    }
                    shiftDisplayInfos.add(new ShiftDisplayInfoDto(
                            integrationOfDaily.getEmployeeId(),
                            integrationOfDaily.getYmd(),
                            key,
                            shiftMaster.map(i -> i.getShiftMasterCode()),
                            shiftMaster.map(i -> i.getShiftMasterName()),
                            workStyle.map(WorkStyle::toAttendanceHolidayAttr)
                    ));
                });
            }
        });

        return new EmployeeDisplayAndAggregatedInfoDto(
                personalCounterResult,
                workplaceCounterResult,
                shiftDisplayInfos,
                personalTotalTimes,
                workplaceTotalTimes
        );
    }
}
