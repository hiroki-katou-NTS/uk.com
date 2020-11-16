package nts.uk.ctx.at.function.dom.workledgeroutputitem;

import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.function.dom.adapter.actualmultiplemonth.MonthlyRecordValueImport;
import nts.uk.ctx.at.function.dom.commonform.GetClosureDateEmploymentDomainService;
import nts.uk.ctx.at.function.dom.commonform.GetSuitableDateByClosureDateUtility;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemDetailSelectionAttendanceItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.StatusOfEmployee;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.DailyMonthlyClassification;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.OperatorsCommonToForms;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeBasicInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 勤務台帳の表示内容を作成する
 *
 * @author khai.dh
 */
public class CreateWorkLedgerDisplayContentDomainService {
    @Inject
    private GetAggregableMonthlyAttendanceItemAdapter getAggblMonthlyAtddItemAdapter;

    /**
     * 勤務台帳の表示内容を作成する
     *
     * @param require     Require
     * @param datePeriod  期間
     * @param empInfoList List<社員情報>
     * @return List<勤務台帳の帳票表示内容>
     */
    public static List<WorkLedgerDisplayContent> createWorkLedgerDisplayContent(
            Require require,
            DatePeriod datePeriod,
            List<EmployeeBasicInfoImport> empInfoList,
            WorkLedgerOutputSetting workLedgerOutputSetting,
            Map<String, WorkplaceInfor> lstWorkplaceInfor) {

        List<String> empIdList = empInfoList.stream().map(EmployeeBasicInfoImport::getSid).collect(Collectors.toList());
        // ① = call() 社員の指定期間中の所属期間を取得する
        val listEmployeeStatus = require.getAffiliateEmpListDuringPeriod(datePeriod, empIdList);
        val cid = AppContexts.user().companyId();
        Map<String, EmployeeBasicInfoImport> lstEmployee = empInfoList.stream().collect(Collectors.toMap(EmployeeBasicInfoImport::getSid, i -> i));
        Map<String, WorkplaceInfor> mapEmployeeWorkplace = new HashMap<>();
        listEmployeeStatus.forEach(x -> {
            WorkplaceInfor workplaceInfor = lstWorkplaceInfor.get(x.getEmployeeId());
            mapEmployeeWorkplace.put(x.getEmployeeId(), workplaceInfor);
        });
        val baseDate = datePeriod.end();
        // ② = call() 基準日で社員の雇用と締め日を取得する
        val closureDateEmploymentList = GetClosureDateEmploymentDomainService.getByDate(require, baseDate, empIdList);


        val monthlyOutputItems = workLedgerOutputSetting.getOutputItemList().stream()
                .filter(x -> x.getDailyMonthlyClassification() == DailyMonthlyClassification.MONTHLY && x.isPrintTargetFlag())
                .collect(Collectors.toList());
        List<WorkLedgerDisplayContent> rs = new ArrayList<>();
        //Loop 「社員の会社所属状況」の「対象社員」in ①
        listEmployeeStatus.parallelStream().forEach(e -> {
                    List<MonthlyOutputLine> outputLines = new ArrayList<>();
                    val eInfor = lstEmployee.get(e.getEmployeeId());
                    val wInfor = mapEmployeeWorkplace.get(e.getEmployeeId());
                    Double total = 0D;
                    for (val monthlyItem : monthlyOutputItems) {

                        val listItem = monthlyItem.getSelectedAttendanceItemList();
                        val itemIds = listItem.stream().map(OutputItemDetailSelectionAttendanceItem::getAttendanceItemId)
                                .collect(Collectors.toList());
                        // 4  会社の月次項目を取得する->・List<月次の勤怠項目>
                        val monthlyAttendanceItems = require.getMonthlyItems(cid, Optional.empty(), itemIds, null);
                        e.getListPeriod().parallelStream().forEach((DatePeriod period) -> {
                            val getClosureDate = closureDateEmploymentList.parallelStream()
                                    .filter(j -> j.getEmployeeId().equals(e.getEmployeeId())).findFirst();
                            if (getClosureDate.isPresent()) {
                                val closureHistory = getClosureDate.get().getClosure().getClosureHistories().get(0);
                                val yearMonthPeriod = GetSuitableDateByClosureDateUtility.getByClosureDate(period,
                                        closureHistory.getClosureDate().getClosureDay().v());
                                val monthlyValue = require.getActualMultipleMonth(
                                        new ArrayList<>(Collections.singletonList(e.getEmployeeId())),
                                        yearMonthPeriod, itemIds).get(e.getEmployeeId());
                                if (monthlyValue != null) {
                                    List<MonthlyValue> lstMonthlyValue = new ArrayList<>();

                                    
                                }
                            }
                        });
//                        val item = new MonthlyOutputLine(lstMonthlyValue, monthlyItem.getName().v(), monthlyItem.getRank(), total, monthlyItem.getItemDetailAttributes());
//                        outputLines.add(item);
                    }

                    rs.add(new WorkLedgerDisplayContent(
                            outputLines,
                            eInfor.getEmployeeCode(),
                            eInfor.getEmployeeName(),
                            wInfor.getWorkplaceCode(),
                            wInfor.getWorkplaceName()));
                }
        );
        return rs;
    }

    public interface Require extends GetClosureDateEmploymentDomainService.Require {
        /**
         * 社員の指定期間中の所属期間を取得する
         */
        List<StatusOfEmployee> getAffiliateEmpListDuringPeriod(DatePeriod datePeriod, List<String> empIdList);

        List<AttItemName> getMonthlyItems(String cid, Optional<String> authorityId, List<Integer> attendanceItemIds,
                                          List<MonthlyAttendanceItemAtr> itemAtrs);

        // [No.495]勤怠項目IDを指定して月別実績の値を取得（複数レコードは合算）
        Map<String, List<MonthlyRecordValueImport>> getActualMultipleMonth(
                List<String> employeeIds, YearMonthPeriod period, List<Integer> itemIds);
    }
}
