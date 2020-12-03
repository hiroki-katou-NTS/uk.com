package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.service.beforemonthlyaggregate;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workplace.EmployeeInfoImported;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.ExtractionMonthlyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.FixedExtractionMonthlyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.service.EmployeeInfoByWorkplaceService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureResultDto;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.WorkClosureService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.月次のアラームチェック.アルゴリズム.月次の集計処理.月次の集計する前のデータを準備
 *
 * @author Le Huu Dat
 */
@Stateless
public class BeforeMonthlyAggregateService {

    @Inject
    private EmployeeInfoByWorkplaceService employeeInfoByWorkplaceService;
    @Inject
    private WorkClosureService workClosureService;
    @Inject
    private AttendanceTimeOfMonthlyRepository attendanceTimeOfMonthlyRepo;

    /**
     * 月次の集計する前のデータを準備
     *
     * @param workplaceIds List＜職場ID＞
     * @param ym           年月
     * @return Map＜職場ID、List＜社員情報＞＞
     * List＜月別実績(Work)＞
     * List＜締め＞
     */
    public MonthlyCheckDataDto prepareData(List<String> workplaceIds, YearMonth ym) {
        DatePeriod period = new DatePeriod(GeneralDate.ymd(ym.year(), ym.month(), 1), ym.lastGeneralDate());
        // 職場ID一覧から社員情報を取得する。
        Map<String, List<EmployeeInfoImported>> empInfosByWpMap = employeeInfoByWorkplaceService.get(workplaceIds, period);

        List<AttendanceTimeOfMonthly> attendanceTimeOfMonthlies = new ArrayList<>();
        if (empInfosByWpMap.size() > 0) {
            List<String> employeeIds = empInfosByWpMap.entrySet().stream()
                    .flatMap(x -> x.getValue().stream().map(EmployeeInfoImported::getSid))
                    .collect(Collectors.toList());

            // 社員ID（List）、期間を設定して月別実績を取得する
            attendanceTimeOfMonthlies = attendanceTimeOfMonthlyRepo.findBySidsAndYearMonths(employeeIds, Collections.singletonList(ym));
        }

        // 会社の締めを取得する
        List<ClosureResultDto> closures = workClosureService.findClosureByReferenceDate(GeneralDate.today());

        return new MonthlyCheckDataDto(empInfosByWpMap, closures, attendanceTimeOfMonthlies);
    }
}
