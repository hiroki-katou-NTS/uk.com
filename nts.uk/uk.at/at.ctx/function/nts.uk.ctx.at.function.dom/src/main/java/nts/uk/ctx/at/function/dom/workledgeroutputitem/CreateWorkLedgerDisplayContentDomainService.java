package nts.uk.ctx.at.function.dom.workledgeroutputitem;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.function.dom.adapter.actualmultiplemonth.MonthlyRecordValueImport;
import nts.uk.ctx.at.function.dom.commonform.AttendanceItemToPrint;
import nts.uk.ctx.at.function.dom.commonform.ClosureDateEmployment;
import nts.uk.ctx.at.function.dom.commonform.GetClosureDateEmploymentDomainService;
import nts.uk.ctx.at.function.dom.commonform.GetSuitableDateByClosureDateUtility;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.EmployeeInfor;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.StatusOfEmployee;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.WorkPlaceInfo;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
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
     * @param require              Require
     * @param datePeriod           期間
     * @param employeeInfoList     List<社員情報>
     * @param workLedgerOutputItem 勤務台帳の出力項目
     * @param workPlaceInfo        List<WorkPlaceInfo>
     * @return List<勤務台帳の帳票表示内容>
     */
    public static List<WorkLedgerDisplayContent> createWorkLedgerDisplayContent(
            Require require,
            DatePeriod datePeriod,
            List<EmployeeInfor> employeeInfoList,
            WorkLedgerOutputItem workLedgerOutputItem,
            List<WorkPlaceInfo> workPlaceInfo) {

        val listSid = employeeInfoList.parallelStream().map(EmployeeInfor::getEmployeeId).collect(Collectors.toList());
        // ① = call() [RQ 588]  社員の指定期間中の所属期間を取得する
        val listEmployeeStatus = require.getAffiliateEmpListDuringPeriod(datePeriod, listSid);
        val cid = AppContexts.user().companyId();
        val mapSids = employeeInfoList.parallelStream().collect(Collectors.toMap(EmployeeInfor::getEmployeeId, e -> e));

        val mapWrps = workPlaceInfo.parallelStream().collect(Collectors.toMap(WorkPlaceInfo::getWorkPlaceId, e -> e));
        val baseDate = datePeriod.end();
        // ② = call() 基準日で社員の雇用と締め日を取得する
        val closureDateEmploymentList = GetClosureDateEmploymentDomainService.get(require, baseDate, listSid);
        // ③ 取得する(会社ID)
        val attIds = require.getAggregableMonthlyAttId(cid);
        val closureDayMap = closureDateEmploymentList.stream().collect(Collectors.toMap(ClosureDateEmployment::getEmployeeId, e -> e));
        val monthlyOutputItems = workLedgerOutputItem.getOutputItemList();

        if (monthlyOutputItems == null ||monthlyOutputItems.isEmpty()) {
            throw new BusinessException("Msg_1926");
        }
        val listAttIds = monthlyOutputItems.parallelStream().map(AttendanceItemToPrint::getAttendanceId)
                .distinct().collect(Collectors.toCollection(ArrayList::new));
        // ④ Call 会社の月次項目を取得する
        val attName = require.getMonthlyItems(cid, null, listAttIds, null).stream()
                .collect(Collectors.toMap(AttItemName::getAttendanceItemId, q -> q));
        List<WorkLedgerDisplayContent> rs = new ArrayList<>();
        if (attName.isEmpty()) {
            throw new BusinessException("Msg_1926");
        }
        listEmployeeStatus.parallelStream().forEach(e -> {
            val item = new WorkLedgerDisplayContent();
            val eplInfo = mapSids.getOrDefault(e.getEmployeeId(), null);
            if (eplInfo != null) {
                item.setEmployeeCode(eplInfo.getEmployeeCode());
                item.setEmployeeName(eplInfo.getEmployeeName());
                val wplInfo = mapWrps.getOrDefault(eplInfo.getWorkPlaceId(), null);
                if (wplInfo != null) {
                    item.setWorkplaceCode(wplInfo.getWorkPlaceCode());
                    item.setWorkplaceName(wplInfo.getWorkPlaceName());
                } else return;
            } else return;

            List<MonthlyRecordValueImport> listAttendances = new ArrayList<>();
            val closureDay = closureDayMap.get(e.getEmployeeId()).getClosure().getClosureHistories()
                    .get(0).getClosureDate().getClosureDay().v();
            for (val date : e.getListPeriod()) {
                //5    ⑤ Cal 月別実績取得の為に年月日から適切な年月に変換する
                val yearMonthPeriod = GetSuitableDateByClosureDateUtility.convertPeriod(date, closureDay);
                //5.1  ⑥ Call [No.495]勤怠項目IDを指定して月別実績の値を取得（複数レコードは合算）
                listAttendances.addAll(require.getActualMultipleMonth(Collections.singletonList(e.getEmployeeId()),
                        yearMonthPeriod, listAttIds).get(e.getEmployeeId()));

            }
            Map<YearMonth, Map<Integer, ItemValue>> allValue = listAttendances.stream()
                    .collect(Collectors.toMap(MonthlyRecordValueImport::getYearMonth,
                            k -> k.getItemValues().stream()
                                    .collect(Collectors.toMap(ItemValue::getItemId, l -> l))));
            val iemOneLine = new ArrayList<MonthlyOutputLine>();
            for (val att : monthlyOutputItems) {
                val monthlyValues = new ArrayList<MonthlyValue>();
                val value = attName.getOrDefault(att.getAttendanceId(), null);
                if (value == null) continue;
                val attribute = convertMonthlyToAttForms(value.getTypeOfAttendanceItem());
                allValue.forEach((key, values) -> {
                    val valueSub = values.getOrDefault(att.getAttendanceId(), null);
                    boolean isCharacter = attribute == CommonAttributesOfForms.WORK_TYPE ||
                            attribute == CommonAttributesOfForms.WORKING_HOURS;
                    if (valueSub != null) {
                        monthlyValues.add(new MonthlyValue(
                                valueSub.itemId(),
                                !isCharacter ? valueSub.doubleOrDefault() : null,
                                key,
                                isCharacter ? valueSub.stringOrDefault() : null
                        ));
                    }

                });
                double total = monthlyValues.stream().filter(x -> x.getActualValue() != null && checkAttId(attIds, x.getAttId()))
                        .mapToDouble(MonthlyValue::getActualValue).sum();
                iemOneLine.add(
                        new MonthlyOutputLine(
                                monthlyValues,
                                value.getAttendanceItemName(),
                                att.getRanking(),
                                total,
                                attribute));

            }
            item.setMonthlyDataList(iemOneLine);
            rs.add(item);

        });
        if (rs.isEmpty()) {
            throw new BusinessException("Msg_1926");
        }
        return rs;
    }

    public interface Require extends GetClosureDateEmploymentDomainService.Require {
        // [RQ 588]  社員の指定期間中の所属期間を取得する
        List<StatusOfEmployee> getAffiliateEmpListDuringPeriod(DatePeriod datePeriod, List<String> empIdList);

        List<AttItemName> getMonthlyItems(String cid, Optional<String> authorityId, List<Integer> attendanceItemIds,
                                          List<MonthlyAttendanceItemAtr> itemAtrs);

        // [No.495]勤怠項目IDを指定して月別実績の値を取得（複数レコードは合算）
        Map<String, List<MonthlyRecordValueImport>> getActualMultipleMonth(
                List<String> employeeIds, YearMonthPeriod period, List<Integer> itemIds);

        //
        List<Integer> getAggregableMonthlyAttId(String cid);
    }

    private static CommonAttributesOfForms convertMonthlyToAttForms(Integer typeOfAttendanceItem) {
        if (typeOfAttendanceItem == MonthlyAttendanceItemAtr.TIME.value) {
            return CommonAttributesOfForms.TIME;
        } else if (typeOfAttendanceItem == MonthlyAttendanceItemAtr.NUMBER.value) {
            return CommonAttributesOfForms.NUMBER_OF_TIMES;
        } else if (typeOfAttendanceItem == MonthlyAttendanceItemAtr.AMOUNT.value) {
            return CommonAttributesOfForms.AMOUNT_OF_MONEY;
        } else if (typeOfAttendanceItem == MonthlyAttendanceItemAtr.DAYS.value) {
            return CommonAttributesOfForms.DAYS;
        } else
            return null;
    }

    private static boolean checkAttId(List<Integer> attIds, int attId) {
        //return attIds.stream().anyMatch(e -> e == attId); TODO QA
        return true;
    }

}
