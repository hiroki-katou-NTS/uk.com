package nts.uk.ctx.at.function.dom.workledgeroutputitem;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.function.dom.adapter.actualmultiplemonth.MonthlyRecordValueImport;
import nts.uk.ctx.at.function.dom.commonform.AttendanceItemToPrint;
import nts.uk.ctx.at.function.dom.commonform.ClosureDateEmployment;
import nts.uk.ctx.at.function.dom.commonform.GetClosureDateEmploymentDomainService;
import nts.uk.ctx.at.function.dom.commonform.GetSuitableDateByClosureDateUtility;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.CodeNameInfoDto;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.*;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 勤務台帳の表示内容を作成する
 *
 * @author khai.dh
 */
public class CreateWorkLedgerDisplayContentQuery {

    public static List<WorkLedgerDisplayContent> createWorkLedgerDisplayContent(
            Require require,
            DatePeriod datePeriod,
            List<EmployeeInfor> employeeInfoList,
            WorkLedgerOutputItem workLedgerOutputItem,
            List<WorkPlaceInfo> workPlaceInfo,
            YearMonthPeriod yearMonthPeriod) {

        val listSid = employeeInfoList.stream().map(EmployeeInfor::getEmployeeId).distinct().collect(Collectors.toList());
        // ① = call() [RQ 589] 社員の指定期間中の所属期間を取得する（年月）
        val listEmployeeStatus = require.getAffiliationPeriod(listSid, yearMonthPeriod, datePeriod.end());

        val cid = AppContexts.user().companyId();
        val mapSids = employeeInfoList.stream()
                .filter(distinctByKey(EmployeeInfor::getEmployeeId))
                .collect(Collectors.toMap(EmployeeInfor::getEmployeeId, e -> e));

        val mapWrps = workPlaceInfo.stream()
                .filter(distinctByKey(WorkPlaceInfo::getWorkPlaceId))
                .collect(Collectors.toMap(WorkPlaceInfo::getWorkPlaceId, e -> e));

        val monthlyOutputItems = workLedgerOutputItem != null ? workLedgerOutputItem.getOutputItemList() : null;

        if (monthlyOutputItems == null || monthlyOutputItems.isEmpty()) {
            throw new BusinessException("Msg_1926");
        }

        // ③ 取得する(会社ID)
        val attIds = require.getAggregableMonthlyAttId(cid);

        val listAttIds = monthlyOutputItems.stream().map(AttendanceItemToPrint::getAttendanceId)
                .distinct().collect(Collectors.toCollection(ArrayList::new));

        Map<String, List<MonthlyRecordValueImport>> actualMultipleMonth =
                require.getActualMultipleMonth(listSid, yearMonthPeriod, listAttIds);
        if (actualMultipleMonth.isEmpty() || actualMultipleMonth.values().isEmpty()) {
            throw new BusinessException("Msg_1926");
        }
        // 4    ④月次の勤怠項目を取得する
        val attendanceItemList = require.findByAttendanceItemId(cid, listAttIds)
                .stream().filter(distinctByKey(MonthlyAttendanceItem::getAttendanceItemId))
                .collect(Collectors.toMap(MonthlyAttendanceItem::getAttendanceItemId, q -> q));
        Map<Integer, Map<String, CodeNameInfoDto>> allDataMaster = require.getAllDataMaster(cid, datePeriod.end(), listAttIds);
        // ④.1 ⑤ Call 会社の月次項目を取得する
        val attName = require.getMonthlyItems(cid, Optional.empty(), listAttIds, null).stream()
                .filter(distinctByKey(AttItemName::getAttendanceItemId))
                .collect(Collectors.toMap(AttItemName::getAttendanceItemId, q -> q));
        List<WorkLedgerDisplayContent> rs = new ArrayList<>();
        if (attName.isEmpty()) {
            throw new BusinessException("Msg_1926");
        }

        List<AffiliationStatusDto> affiliationStatus = listEmployeeStatus.getAffiliationStatus();
        affiliationStatus.forEach((AffiliationStatusDto e) -> {
            val item = new WorkLedgerDisplayContent();
            val eplInfo = mapSids.getOrDefault(e.getEmployeeID(), null);
            if (eplInfo != null) {
                item.setEmployeeCode(eplInfo.getEmployeeCode());
                item.setEmployeeName(eplInfo.getEmployeeName());
                val wplInfo = mapWrps.getOrDefault(eplInfo.getWorkPlaceId(), null);
                if (wplInfo != null) {
                    item.setWorkplaceCode(wplInfo.getWorkPlaceCode());
                    item.setWorkplaceName(wplInfo.getWorkPlaceName());
                    item.setHierarchyCd(wplInfo.getHierarchyCd());
                } else return;
            } else return;

            // Loop 出力項目 月次
            List<YearMonth> yearMonthList = e.getPeriodInformation()
                    .stream()
                    .flatMap(y -> y.getYearMonthPeriod().yearMonthsBetween().stream())
                    .collect(Collectors.toList());
            List<MonthlyRecordValueImport> valueImports = actualMultipleMonth
                    .getOrDefault(e.getEmployeeID(), Collections.emptyList());

            Map<YearMonth, Map<Integer, ItemValue>> allValue = valueImports.stream()
                    .collect(Collectors.toMap(MonthlyRecordValueImport::getYearMonth,
                            k -> k.getItemValues().stream().filter(distinctByKey(ItemValue::getItemId))
                                    .collect(Collectors.toMap(ItemValue::getItemId, l -> l))));

            val iemOneLine = new ArrayList<MonthlyOutputLine>();
            for (val att : monthlyOutputItems) {
                val monthlyValues = new ArrayList<MonthlyValue>();
                val value = attName.getOrDefault(att.getAttendanceId(), null);
                if (value == null || value.getTypeOfAttendanceItem() == null) continue;
                val attributeMonthly = attendanceItemList.getOrDefault(att.getAttendanceId(), null);
                if (attributeMonthly != null && allValue != null) {
                    val attribute = convertMonthlyToAttForms(attributeMonthly.getMonthlyAttendanceAtr().value);
                    if (attribute == null) continue;
                    for (val y : yearMonthList) {
                        val values = allValue.getOrDefault(y, null);
                        if (values == null) continue;
                        val valueSub = values.getOrDefault(att.getAttendanceId(), null);
                        boolean isCharacter = attribute == CommonAttributesOfForms.WORK_TYPE
                                ||attribute == CommonAttributesOfForms.OTHER_CHARACTERS
                                ||attribute == CommonAttributesOfForms.OTHER_CHARACTER_NUMBER
                                ||attribute == CommonAttributesOfForms.WORKING_HOURS;
                        val primitiveValue = attributeMonthly.getPrimitiveValue().isPresent() ? attributeMonthly.getPrimitiveValue().get() : null;
                        String nameDisplay = "";
                        if (valueSub != null) {
                            if (primitiveValue != null ) {
                                val key = valueSub.getValue();
                                val name = allDataMaster.getOrDefault(primitiveValue.value, null);
                                if (name != null) {
                                    nameDisplay = name.get(key) != null ? name.get(key).getName() : key;
                                }else {
                                    nameDisplay = key;
                                }
                            }
                            monthlyValues.add(new MonthlyValue(
                                    valueSub.itemId(),
                                    !isCharacter ? valueSub.doubleOrDefault() : null,
                                    y,
                                    isCharacter ? valueSub.stringOrDefault() : null,
                                    nameDisplay

                            ));
                        }
                    }
                    double total = monthlyValues.stream().filter(x -> x.getActualValue() != null && checkAttId(attIds, x.getAttId()))
                            .mapToDouble(MonthlyValue::getActualValue).sum();
                    iemOneLine.add(
                            new MonthlyOutputLine(
                                    monthlyValues,
                                    value.getAttendanceItemName(),
                                    att.getRanking(),
                                    total,
                                    attribute,
                                    attributeMonthly.getPrimitiveValue().isPresent() ? attributeMonthly.getPrimitiveValue().get().value : null,
                                    attributeMonthly.getDisplayNumber()
                            ));
                }

            }
            if (iemOneLine.size() != 0) {
                item.setMonthlyDataList(iemOneLine);
                if (!item.getMonthlyDataList().isEmpty() || item.getMonthlyDataList().size() != 0) {
                    rs.add(item);
                }
            }
        });
        if (rs.isEmpty()) {
            throw new BusinessException("Msg_1926");
        }
        return rs;
    }

    public interface Require extends GetClosureDateEmploymentDomainService.Require {
        //[RQ 589] 社員の指定期間中の所属期間を取得する（年月）
        EmpAffInfoExportDto getAffiliationPeriod(List<String> listSid, YearMonthPeriod YMPeriod, GeneralDate baseDate);


        List<AttItemName> getMonthlyItems(String cid, Optional<String> authorityId, List<Integer> attendanceItemIds,
                                          List<MonthlyAttendanceItemAtr> itemAtrs);

        // [No.495]勤怠項目IDを指定して月別実績の値を取得（複数レコードは合算）
        Map<String, List<MonthlyRecordValueImport>> getActualMultipleMonth(
                List<String> employeeIds, YearMonthPeriod period, List<Integer> itemIds);

        //
        List<Integer> getAggregableMonthlyAttId(String cid);

        // 月次の勤怠項目を取得する
        List<MonthlyAttendanceItem> findByAttendanceItemId(String companyId, List<Integer> attendanceItemIds);

        Map<Integer, Map<String, CodeNameInfoDto>> getAllDataMaster(String companyId, GeneralDate dateReference,
                                                                    List<Integer> lstDivNO);
    }

    public static CommonAttributesOfForms convertMonthlyToAttForms(Integer typeOfAttendanceItem) {
        //・時間　→　時間
        if (typeOfAttendanceItem.equals(MonthlyAttendanceItemAtr.TIME.value)) {
            return CommonAttributesOfForms.TIME;
        }
        // ・回数　→　回数
        else if (typeOfAttendanceItem.equals(MonthlyAttendanceItemAtr.NUMBER.value)) {
            return CommonAttributesOfForms.NUMBER_OF_TIMES;
            // ・日数　→　日数
        } else if (typeOfAttendanceItem.equals(MonthlyAttendanceItemAtr.DAYS.value)) {
            return CommonAttributesOfForms.DAYS;
        }
        // ・金額　→　金額
        else if (typeOfAttendanceItem.equals(MonthlyAttendanceItemAtr.AMOUNT.value)) {
            return CommonAttributesOfForms.AMOUNT_OF_MONEY;
        }
        // ・マスタを参照する　→　なし(その他_文字数値)
        else if (typeOfAttendanceItem.equals(MonthlyAttendanceItemAtr.REFER_TO_MASTER.value)) {
            return CommonAttributesOfForms.OTHER_CHARACTER_NUMBER;
        }
        //・コード　→　なし(その他_文字数値)
        else if (typeOfAttendanceItem.equals(MonthlyAttendanceItemAtr.CODE.value)) {
            return CommonAttributesOfForms.OTHER_CHARACTER_NUMBER;
        }
        // 区分　→　なし(その他_数値)
        else if (typeOfAttendanceItem.equals(MonthlyAttendanceItemAtr.CLASSIFICATION.value)) {
            return CommonAttributesOfForms.OTHER_NUMERICAL_VALUE;
        }
        // ・比率　→　なし(その他_数値)
        else if (typeOfAttendanceItem.equals(MonthlyAttendanceItemAtr.RATIO.value)) {
            return CommonAttributesOfForms.OTHER_NUMERICAL_VALUE;
        }
        // ・文字　→　なし(その他_文字)
        else if (typeOfAttendanceItem.equals(MonthlyAttendanceItemAtr.CHARACTER.value)) {
            return CommonAttributesOfForms.OTHER_CHARACTERS;
        } else
            return null;
    }

    private static boolean checkAttId(List<Integer> attIds, int attId) {
        return attIds.stream().anyMatch(e -> e == attId);
    }

    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
