package nts.uk.file.at.app.export.schedule.personalschedulebyworkplace;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.common.ScheRecGettingAtr;
import nts.uk.ctx.at.aggregation.dom.scheduletable.ScheduleTableAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceDayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 一日分の社員の表示情報を取得す
 */
@Stateless
public class EmployeeOneDayAttendanceInfoQuery {
    @Inject
    private DailyRecordConverter dailyRecordConverter;

    @Inject
    private WorkTimeSettingRepository workTimeSettingRepo;

    @Inject
    private ShiftMasterRepository shiftMasterRepo;

    @Inject
    private WorkTypeRepository workTypeRepo;

    /**
     * 取得する
     */
    public List<EmployeeOneDayAttendanceInfo> get(List<IntegrationOfDaily> integrationOfDailyList, List<ScheduleTableAttendanceItem> attendanceItems) {
        String companyId = AppContexts.user().companyId();
        List<EmployeeOneDayAttendanceInfo> listEmpOneDayAttendanceInfo = new ArrayList<>();
        // Loop：日別勤怠 in Input.List<日別勤怠(Work)>
        DailyRecordToAttendanceItemConverter converter = dailyRecordConverter.createDailyConverter().completed();
        List<Integer> attendanceItemIdList = attendanceItems.stream().map(i -> i.value).collect(Collectors.toList());
        for (IntegrationOfDaily integration : integrationOfDailyList) {
            converter.setData(integration);
            List<ItemValue> listItemValue = converter.convert(attendanceItemIdList);
            Map<ScheduleTableAttendanceItem, ItemValue> attendanceData = listItemValue.stream()
                    .collect(Collectors.toMap(i -> EnumAdaptor.valueOf(i.getItemId(), ScheduleTableAttendanceItem.class), Function.identity()));
            // 1.1 作る(日別勤怠(Work))
            EmployeeOneDayAttendanceInfo data = new EmployeeOneDayAttendanceInfo();
            data.setEmployeeId(integration.getEmployeeId());
            data.setDate(integration.getYmd());
            data.setAttendanceData(attendanceData);
            // if List<スケジュール表の勤怠項目>.contains(就業時間帯)
            if (attendanceItems.contains(ScheduleTableAttendanceItem.WORK_TIME)) {
                String workTimeCode = attendanceData.get(ScheduleTableAttendanceItem.WORK_TIME).getValue();
                Optional<WorkTimeSetting> optWorkTimeSetting = workTimeSettingRepo.findByCode(companyId, workTimeCode);
                if (optWorkTimeSetting.isPresent()) {
                    data.setWorkTimeName(Optional.of(optWorkTimeSetting.get().getWorkTimeDisplayName().getWorkTimeAbName().v()));
                }
            }
            // if List<スケジュール表の勤怠項目>.contains(シフト)
            if (attendanceItems.contains(ScheduleTableAttendanceItem.SHIFT)) {
                String shiftCode = attendanceData.get(ScheduleTableAttendanceItem.SHIFT).getValue();
                Optional<ShiftMaster> optShiftMaster = shiftMasterRepo.getByShiftMaterCd(companyId, shiftCode);
                if (optShiftMaster.isPresent()) {
                    data.setShiftMasterName(Optional.of(optShiftMaster.get().getDisplayInfor().getName().v()));
                    data.setShiftBackgroundColor(Optional.of(optShiftMaster.get().getDisplayInfor().getColor().v()));
                }
            }
            //
            if (attendanceItems.contains(ScheduleTableAttendanceItem.WORK_TYPE)) {
                String workTypeCode = attendanceData.get(ScheduleTableAttendanceItem.WORK_TYPE).getValue();
                Optional<WorkType> optWorkType = workTypeRepo.findByPK(companyId, workTypeCode);
                if (optWorkType.isPresent()) {
                    data.setWorkTimeName(Optional.of(optWorkType.get().getAbbreviationName().v()));
                    data.setAttendanceDayAttr(Optional.of(optWorkType.get().chechAttendanceDay()));
                }
            }
            listEmpOneDayAttendanceInfo.add(data);
        }

        // dummy data
        DatePeriod period = new DatePeriod(GeneralDate.ymd(2021, 9, 1), GeneralDate.ymd(2021, 9, 30));
        period.datesBetween().forEach(date -> {
            EmployeeOneDayAttendanceInfo data = new EmployeeOneDayAttendanceInfo();
            data.setEmployeeId(AppContexts.user().employeeId());
            data.setDate(date);
            Map<ScheduleTableAttendanceItem, ItemValue> attendanceData = new HashMap<>();
            attendanceItems.forEach(itemId -> {
                attendanceData.put(itemId, new ItemValue(ValueType.UNKNOWN, "", itemId.value, "00:00"));
            });
            data.setAttendanceData(attendanceData);
            if (attendanceItems.contains(ScheduleTableAttendanceItem.WORK_TIME)) {
                data.setWorkTimeName(Optional.of("WTm"));
            }
            if (attendanceItems.contains(ScheduleTableAttendanceItem.WORK_TYPE)) {
                data.setWorkTypeName(Optional.of("WTp"));
                Random rd = new Random();
                data.setAttendanceDayAttr(Optional.of(EnumAdaptor.valueOf(rd.nextInt(5), AttendanceDayAttr.class)));
            }
            if (attendanceItems.contains(ScheduleTableAttendanceItem.SHIFT)) {
                data.setShiftMasterName(Optional.of("Shift"));
                data.setShiftBackgroundColor(Optional.of("#30cc40"));
            }
            listEmpOneDayAttendanceInfo.add(data);
        });
        return listEmpOneDayAttendanceInfo;
    }
}
