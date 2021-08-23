package nts.uk.file.at.app.export.schedule.personalschedulebydate;

import lombok.val;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.GetListWtypeWtimeUseDailyAttendRecordService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.screen.at.app.ksu003.start.dto.EmployeeWorkScheduleDto;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 日付別予定情報を取得する
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSU003_個人スケジュール修正(日付別).D：出力の設定.メニュー別OCD.日付別予定情報を取得する.日付別予定情報を取得する
 */
@Stateless
public class ScheduleInformationByDateExportQuery {
    @Inject
    private WorkTypeRepository workTypeRepo;

    @Inject
    private WorkTimeSettingRepository workTimeSettingRepo;

    public EmployeeWorkScheduleDto get(List<IntegrationOfDaily> lstIntegrationOfDaily, boolean graphVacationDisplay,
                                       boolean doubleWorkDisplay) {
        String companyId = AppContexts.user().companyId();

        // 日別勤怠の勤務情報リスト = List<日別勤怠(Work)>．values: List $.勤務情報
        val lstWorkInfoOfDailyAttendance = lstIntegrationOfDaily.stream().map(IntegrationOfDaily::getWorkInformation)
                .collect(Collectors.toList());
        // 1. 取得(List<日別勤怠の勤務情報>): param arg input.日別勤怠の勤務情報 , return 日別勤怠の実績で利用する勤務種類と就業時間帯
        val workTypeWorkTimeUseDailyAttendanceRecord = GetListWtypeWtimeUseDailyAttendRecordService.getdata(lstWorkInfoOfDailyAttendance);

        // 2. get(ログイン社員ID, 日別勤怠の実績で利用する勤務種類と就業時間帯.勤務種類リスト) : return WorkType
        final List<WorkType> workTypeList = workTypeRepo.findByCidAndWorkTypeCodes(
                companyId,
                workTypeWorkTimeUseDailyAttendanceRecord.getLstWorkTypeCode().stream().map(PrimitiveValueBase::v).collect(Collectors.toList())
        );

        // 3. get(社員ID,List<就業時間帯コード>): return WorkTimeSetting
        val workTimeSettingList = workTimeSettingRepo.getListWorkTimeSetByListCode(
                companyId,
                workTypeWorkTimeUseDailyAttendanceRecord.getLstWorkTimeCode().stream().map(PrimitiveValueBase::v).collect(Collectors.toList())
        );

        // 4. loop：日別勤怠(Work) in input.List<日別勤怠(Work)>
        for (IntegrationOfDaily integrationOfDaily : lstIntegrationOfDaily) {
        }

        // 4.1. 勤務形態を取得する() : return 就業時間帯の勤務形態
        // getWorkTimeForm

        // 4.2. コアタイム時間帯を取得する: GetTimezoneOfCoreTimeService

        // 4.3. 就業時間帯の勤務形態 == 固定勤務 (Working hours == Fixed work)

        // 4.3.1. get(会社ID, 就業時間帯コード): return Optional<固定勤務設定>

        // 4.3.2. 指定した午前午後区分の残業時間帯を取得する(午前午後区分): param arg 出勤日区分.午前午後区分に変換() , return List<計算用時間帯>

        // 4.4. グラフ休暇表示==true: 時間休暇を取得する() : return Map<時間休暇種類, 時間休暇>

        // 4.5. create()

        return null;
    }
}
