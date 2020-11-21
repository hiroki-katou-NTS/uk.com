package nts.uk.ctx.at.function.dom.workledgeroutputitem;

import lombok.val;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.function.dom.adapter.actualmultiplemonth.MonthlyRecordValueImport;
import nts.uk.ctx.at.function.dom.commonform.ClosureDateEmployment;
import nts.uk.ctx.at.function.dom.commonform.GetClosureDateEmploymentDomainService;
import nts.uk.ctx.at.function.dom.commonform.GetSuitableDateByClosureDateUtility;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemDetailAttItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.EmployeeInfor;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.StatusOfEmployee;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.WorkPlaceInfo;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.DailyMonthlyClassification;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
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
            List<EmployeeInfor> employeeInfoList,
            WorkLedgerOutputSetting workLedgerOutputSetting,
            List<WorkPlaceInfo> workPlaceInfo) {

        val listSid = employeeInfoList.parallelStream().map(EmployeeInfor::getEmployeeId).collect(Collectors.toList());
        // ① = call() 社員の指定期間中の所属期間を取得する
        val listEmployeeStatus = require.getAffiliateEmpListDuringPeriod(datePeriod, listSid);
        val cid = AppContexts.user().companyId();
        val roleId = Optional.of(AppContexts.user().roles().forAttendance());
        val mapSids = employeeInfoList.parallelStream().collect(Collectors.toMap(EmployeeInfor::getEmployeeId, e -> e));

        val mapWrps = workPlaceInfo.parallelStream().collect(Collectors.toMap(WorkPlaceInfo::getWorkPlaceId, e -> e));
        val baseDate = datePeriod.end();
        // ② = call() 基準日で社員の雇用と締め日を取得する
        val closureDateEmploymentList = GetClosureDateEmploymentDomainService.getByDate(require, baseDate, listSid);

        val closureDayMap = closureDateEmploymentList.stream().collect(Collectors.toMap(ClosureDateEmployment::getEmployeeId, e -> e));
        val monthlyOutputItems = workLedgerOutputSetting.getOutputItemList().stream()
                .filter(x -> x.getDailyMonthlyClassification() == DailyMonthlyClassification.MONTHLY && x.isPrintTargetFlag())
                .collect(Collectors.toList());
        List<WorkLedgerDisplayContent> rs = new ArrayList<>();
        listEmployeeStatus.parallelStream().forEach(e -> {
            val item = new WorkLedgerDisplayContent();
            val eplInfo = mapSids.get(e.getEmployeeId());
            if (eplInfo != null) {
                item.setEmployeeCode(eplInfo.getEmployeeCode());
                item.setEmployeeName(eplInfo.getEmployeeName());
                val wplInfo = mapWrps.get(eplInfo.getWorkPlaceId());
                if (wplInfo != null) {
                    item.setWorkplaceCode(wplInfo.getWorkPlaceCode());
                    item.setWorkplaceName(wplInfo.getWorkPlaceName());
                } else return;
            } else return;
            val listAttIds = monthlyOutputItems.parallelStream().flatMap(i -> i.getSelectedAttendanceItemList()
                    .stream().map(OutputItemDetailAttItem::getAttendanceItemId)).distinct().collect(Collectors.toCollection(ArrayList::new));
            val attName = require.getMonthlyItems(cid, roleId, listAttIds, null).stream()
                    .collect(Collectors.toMap(AttItemName::getAttendanceItemId, q -> q));
            List<MonthlyRecordValueImport> listAttendancesz = new ArrayList<>();
            val closureDay = closureDayMap.get(e.getEmployeeId()).getClosure().getClosureHistories()
                    .get(0).getClosureDate().getClosureDay().v();
            for (val date : e.getListPeriod()) {
                val yearMonthPeriod = GetSuitableDateByClosureDateUtility.getByClosureDate(date, closureDay);
                listAttendancesz.addAll(require.getActualMultipleMonth(Collections.singletonList(e.getEmployeeId()),
                        yearMonthPeriod, listAttIds).get(e.getEmployeeId()));
            }
            Map<YearMonth, Map<Integer, ItemValue>> allValue = listAttendancesz.stream()
                    .collect(Collectors.toMap(MonthlyRecordValueImport::getYearMonth,
                            k -> k.getItemValues().stream()
                                    .collect(Collectors.toMap(ItemValue::getItemId, l -> l))));
            for (val j : monthlyOutputItems) {
                val listAtts = j.getSelectedAttendanceItemList();
                if (listAtts.isEmpty()) continue;
                val listMonthlyOutputLine = new ArrayList<MonthlyOutputLine>();
                for (val sub : listAtts) {
                    Double total = 0D;
                    val keySet = allValue.keySet();
                    List<MonthlyValue> attendanceItemValueList = new ArrayList<>();
                    for (val key : keySet) {
                        val valueSub = allValue.get(key).get(sub.getAttendanceItemId());
                        total += valueSub.doubleOrDefault();
                        val monthly = new MonthlyValue(
                                valueSub.doubleOrDefault(),
                                key,
                                valueSub.stringOrDefault()
                        );
                        attendanceItemValueList.add(monthly);
                    }
                    val outputLine = new MonthlyOutputLine(
                            attendanceItemValueList,
                            attName.get(sub.getAttendanceItemId()).getAttendanceItemName(),
                            j.getRank(),
                            total,
                            j.getItemDetailAttributes()
                    );
                    listMonthlyOutputLine.add(outputLine);
                }
                item.setMonthlyDataList(listMonthlyOutputLine);
                rs.add(item);
            }
        });
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
