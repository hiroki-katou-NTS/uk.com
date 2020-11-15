package nts.uk.ctx.at.function.dom.workledgeroutputitem;

import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.function.dom.adapter.actualmultiplemonth.MonthlyRecordValueImport;
import nts.uk.ctx.at.function.dom.commonform.GetClosureDateEmploymentDomainService;
import nts.uk.ctx.at.function.dom.commonform.GetSuitableDateByClosureDateUtility;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemDetailSelectionAttendanceItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.StatusOfEmployee;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.DailyMonthlyClassification;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeBasicInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 勤務台帳の表示内容を作成する
 *
 * @author khai.dh
 */
public class CreateWorkLedgerDisplayContentDomainService {
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
            List<WorkplaceInfor> workplaceInfoList) {

        val listSid = empInfoList.parallelStream().map(EmployeeBasicInfoImport::getSid).collect(Collectors.toList());
        // ① = <call> 社員の指定期間中の所属期間を取得する
        val listEmployeeStatus = require.getAffiliateEmpListDuringPeriod(datePeriod, listSid);

        val baseDate = datePeriod.end();
        // ② =  <cal> 基準日で社員の雇用と締め日を取得する
        val closureDateEmploymentList = GetClosureDateEmploymentDomainService.getByDate(require, baseDate, listSid);
        List<Integer> attendanceItemIds = new ArrayList<>();
        val monthlyOutputItems = workLedgerOutputSetting.getOutputItemList().stream()
                .filter(x -> x.getDailyMonthlyClassification() == DailyMonthlyClassification.MONTHLY && x.isPrintTargetFlag())
                .collect(Collectors.toList());
        monthlyOutputItems.parallelStream().forEach(e ->
                attendanceItemIds.addAll(e.getSelectedAttendanceItemList().parallelStream()
                        .map(OutputItemDetailSelectionAttendanceItem::getAttendanceItemId).collect(Collectors.toList())));
        listEmployeeStatus.parallelStream().forEach(e -> {
                    for (val monthlyItem : monthlyOutputItems) {
                        val listItem = monthlyItem.getSelectedAttendanceItemList();
                        val itemIds = listItem.stream().map(OutputItemDetailSelectionAttendanceItem::getAttendanceItemId)
                                .collect(Collectors.toList());
                        val getClosureDate = closureDateEmploymentList.parallelStream()
                                .filter(j -> j.getEmployeeId().equals(e.getEmployeeId())).findFirst();
                        if (getClosureDate.isPresent()) {
                            val closureHistory = getClosureDate.get().getClosure().getClosureHistories().get(0);
                            val period = GetSuitableDateByClosureDateUtility.getByClosureDate(datePeriod,
                                    closureHistory.getClosureDate().getClosureDay().v());
                            val monthlyValue =
                                    require.getActualMultipleMonth(
                                            new ArrayList<>(Collections.singletonList(e.getEmployeeId())),
                                            period, itemIds);

                        }
                    }

                }

        );
        return null;
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
