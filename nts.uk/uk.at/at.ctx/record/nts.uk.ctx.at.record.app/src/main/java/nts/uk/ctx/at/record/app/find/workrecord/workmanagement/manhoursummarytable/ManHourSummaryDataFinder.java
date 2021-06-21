package nts.uk.ctx.at.record.app.find.workrecord.workmanagement.manhoursummarytable;

import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.record.dom.adapter.function.alarmworkplace.EmployeeInfoImport;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDailyRepo;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.*;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Query: 工数集計データを取得する
 */
@Stateless
public class ManHourSummaryDataFinder {
    @Inject
    private OuenWorkTimeSheetOfDailyRepo ouenRepo;

    @Inject
    private WorkplaceInformationAdapter workplaceAdapter;

    @Inject
    private EmployeeDisplayNameAdapter empAdapter;

    @Inject
    private WorkMasterAdapter taskAdapter;

    public ManHourSummaryData getManHourSummary(DatePeriod datePeriod, YearMonthPeriod yearMonthPeriod) {
        val cid = AppContexts.user().companyId();

        // 1. get ManHourRecordReferenceSetting (cid) [工数実績参照設定]
        //INPUT「年月日期間」.isPresent　⇒　対象期間 = INPUT「年月日期間」
        //INPUT「年月日期間」.isEmpty　⇒　対象期間 = 期間#期間(INPUT「年月期間」.開始年月.初日(), INPUT「年月期間」.終了年月.末日())
        //基準日 = 求めた「対象期間」．終了日
        // val manHourRecordRefer = ;  //TODO: QA #49892

        // 2.参照可能範囲を取得する(@Require, 会社ID, ユーザID, 社員ID, 年月日)
        // return Map<社員ID,職場ID>
        Map<String, String> rangeMap = new HashMap<>();  //TODO: QA #49892

        // 3.作業詳細データを取得する(社員リスト,職場リスト,期間)
        //社員リスト = 取得した「参照可能範囲」：map $.key distinct
        //職場リスト = 取得した「参照可能範囲」：map $.value distinct
        List<String> empIds = rangeMap.keySet().stream().distinct().collect(Collectors.toList());
        List<String> wkpIds = rangeMap.entrySet().stream().map(Map.Entry::getValue).distinct().collect(Collectors.toList());
        List<WorkDetailData> workDetailList = this.ouenRepo.getWorkDetailData(empIds, wkpIds, datePeriod);

        // 4.取得する(会社ID, 職場ID, 年月日)
        //4.1回目: FirstTime
        val affWkpList = workDetailList.stream().map(WorkDetailData::getAffWorkplaceId).distinct().collect(Collectors.toList());
        List<WorkplaceInfor> affWorkplaceInfors = this.workplaceAdapter.getWorkplaceInfor(cid, affWkpList, datePeriod.end());

        //4.2回目: SecondTime
        val wkpList = workDetailList.stream().map(WorkDetailData::getWorkplaceId).distinct().collect(Collectors.toList());
        List<WorkplaceInfor> workplaceInfors = this.workplaceAdapter.getWorkplaceInfor(cid, wkpList, datePeriod.end());

        // 5.取得する(社員ID)
        val sIds = workDetailList.stream().map(WorkDetailData::getEmployeeId).distinct().collect(Collectors.toList());
        List<EmployeeInfoImport> empList = this.empAdapter.getByListSID(sIds);

        // 6.取得する(会社ID, 作業枠NO, 作業コード)
        val taskList1 = getTaskListBy(workDetailList, SummaryItemType.TASK1, 1);
        val taskList2 = getTaskListBy(workDetailList, SummaryItemType.TASK2, 2);
        val taskList3 = getTaskListBy(workDetailList, SummaryItemType.TASK3, 3);
        val taskList4 = getTaskListBy(workDetailList, SummaryItemType.TASK4, 4);
        val taskList5 = getTaskListBy(workDetailList, SummaryItemType.TASK5, 5);

        // 7. Create
        return new ManHourSummaryData(
                new MasterNameInformation(
                        affWorkplaceInfors,
                        workplaceInfors,
                        empList,
                        taskList1,
                        taskList2,
                        taskList3,
                        taskList4,
                        taskList5
                ),
                workDetailList
        );
    }

    /**
     * Get work list by type
     *
     * @param workDetailList
     * @param itemType
     * @return List<TaskImport>
     */
    private List<TaskImport> getTaskListBy(List<WorkDetailData> workDetailList, SummaryItemType itemType, int frameNo) {
        val workCodes = workDetailList.stream().map(c -> c.mapSummaryItem(itemType)).distinct().collect(Collectors.toList());
        val lstTask = this.taskAdapter.getTaskList(AppContexts.user().companyId(), frameNo, workCodes);

        return !lstTask.isEmpty() ? lstTask : Collections.emptyList();
    }
}
