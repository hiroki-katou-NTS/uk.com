package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.function.dom.adapter.actualmultiplemonth.MonthlyRecordValueImport;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceItemDtoValue;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceResultDto;
import nts.uk.ctx.at.function.dom.commonform.ClosureDateEmployment;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemDetailAttItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemWorkLedger;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.AffiliationStatusDto;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.EmpAffInfoExportDto;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.DailyMonthlyClassification;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.OperatorsCommonToForms;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeBasicInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Query: 年間勤務台帳の表示内容を作成する
 */
@Stateless
public class CreateAnnualWorkLedgerContentQuery {
    private static final int WORK_TYPE = 1;
    private static final int WORKING_HOURS = 2;

    public static List<AnnualWorkLedgerContent> getData(Require require,
                                                        DatePeriod datePeriod,
                                                        Map<String, EmployeeBasicInfoImport> lstEmployee,
                                                        AnnualWorkLedgerOutputSetting outputSetting,
                                                        Map<String, WorkplaceInfor> lstWorkplaceInfor,
                                                        Map<String, ClosureDateEmployment> lstClosureDateEmployment,
                                                        YearMonthPeriod yearMonthPeriod) {
        if (outputSetting == null) {
            throw new BusinessException("Msg_1860");
        }
        List<AnnualWorkLedgerContent> result = new ArrayList<>();

        List<String> listSid = new ArrayList<>(lstEmployee.keySet());
        val cid = AppContexts.user().companyId();

        // 1 - [CALL _RQ 589]
        EmpAffInfoExportDto listEmployeeStatus = require.getAffiliationPeriod(listSid, yearMonthPeriod, datePeriod.end());

        // ※１ = 「３」年間勤務台帳の出力設定．日次出力項目リスト
        val dailyOutputItems = outputSetting.getDailyOutputItemList().stream()
                .filter(x -> x.getDailyMonthlyClassification() == DailyMonthlyClassification.DAILY && x.isPrintTargetFlag())
                .collect(Collectors.toList());
        // ※２ = 「３」年間勤務台帳の出力設定．日次出力項目リスト．選択勤怠項目リスト
        val listIdDailys = dailyOutputItems.stream()
                .flatMap(x -> x.getSelectedAttendanceItemList().stream()
                        .map(OutputItemDetailAttItem::getAttendanceItemId))
                .distinct().collect(Collectors.toCollection(ArrayList::new));

        // ※３ = 「３」年間勤務台帳の出力設定．月次出力項目リスト
        val monthlyOutputItems = outputSetting.getMonthlyOutputItemList().stream()
                .filter(x -> x.getDailyMonthlyClassification() == DailyMonthlyClassification.MONTHLY && x.isPrintTargetFlag())
                .collect(Collectors.toList());
        // ※４ = 「３」年間勤務台帳の出力設定．月次出力項目リスト．選択勤怠項目リスト
        val listIdMonthly = monthlyOutputItems.stream()
                .flatMap(x -> x.getSelectedAttendanceItemList().stream()
                        .map(OutputItemDetailAttItem::getAttendanceItemId))
                .distinct().collect(Collectors.toCollection(ArrayList::new));
        if (listEmployeeStatus == null || (dailyOutputItems.isEmpty() && monthlyOutputItems.isEmpty())) {
            throw new BusinessException("Msg_1860");
        }
        // DAILY
        List<AttendanceResultDto> listItemValue = require.getValueOf(listSid, datePeriod, listIdDailys);
        Map<String, Map<GeneralDate, Map<Integer, AttendanceItemDtoValue>>> mapValue = new HashMap<>();
        Map<Integer, Map<String, CodeNameInfoDto>> allDataMaster = require.getAllDataMaster(cid, datePeriod.end(), listIdDailys);
        listSid.forEach(e -> {
            val listValueEm = listItemValue.stream().filter(i -> i.getEmployeeId().equals(e)).collect(Collectors.toList());
            Map<GeneralDate, Map<Integer, AttendanceItemDtoValue>> values = listValueEm.stream()
                    .collect(Collectors.toMap(AttendanceResultDto::getWorkingDate,
                            i -> i.getAttendanceItems().stream().collect(Collectors.toMap(AttendanceItemDtoValue::getItemId, l -> l))));
            mapValue.put(e, values);
        });
        // MONTHLY
        Map<String, List<MonthlyRecordValueImport>> actualMultipleMonth =
                require.getActualMultipleMonth(listSid, yearMonthPeriod, listIdMonthly);

        List<AffiliationStatusDto> affiliationStatus = listEmployeeStatus.getAffiliationStatus();

        // Loop 「社員の会社所属状況」の「対象社員」
        affiliationStatus.parallelStream().forEach(emp -> {
            val employee = lstEmployee.get(emp.getEmployeeID());
            val workplaceInfo = lstWorkplaceInfor.get(emp.getEmployeeID());
            if (employee == null || workplaceInfo == null) return;
            Map<GeneralDate, Map<Integer, AttendanceItemDtoValue>> allValue = mapValue
                    .getOrDefault(emp.getEmployeeID(), new HashMap<>());
            // 日次データ
            DailyData dailyData = null;
            if (!dailyOutputItems.isEmpty() && allValue != null) {
                dailyData = getDailyData(emp, dailyOutputItems, allValue, allDataMaster);
            }
            // 月次データ
            List<MonthlyData> lstMonthlyData = new ArrayList<>();
            String closureDate = null;
            String employmentCode = null;
            String employmentName = null;

            if (lstClosureDateEmployment.size() == 0) return;
            val closureDateEmployment = lstClosureDateEmployment.getOrDefault(emp.getEmployeeID(), null);
            if (closureDateEmployment == null) return;
            val closureOptional = closureDateEmployment.getClosure();
            if (closureOptional.isPresent()) {
                val closure = closureOptional.get();
                if (closure.getClosureHistories() != null && closure.getClosureHistories().size() > 0) {
                    val closureHistory = closure.getClosureHistories().get(0);
                    List<MonthlyRecordValueImport> valueImports = actualMultipleMonth
                            .getOrDefault(emp.getEmployeeID(), Collections.emptyList());
                    if (!monthlyOutputItems.isEmpty() && valueImports != null) {
                        lstMonthlyData = getMonthlyData(emp, monthlyOutputItems, valueImports);
                    }
                    closureDate = closureHistory.getClosureName().v();
                    employmentCode = closureDateEmployment.getEmploymentCode();
                    employmentName = closureDateEmployment.getEmploymentName();
                }
            }
            AnnualWorkLedgerContent model = new AnnualWorkLedgerContent(
                    dailyData,
                    lstMonthlyData,
                    employee.getEmployeeCode(),
                    employee.getEmployeeName(),
                    closureDate,
                    workplaceInfo.getWorkplaceCode(),
                    workplaceInfo.getHierarchyCode(),
                    workplaceInfo.getWorkplaceName(),
                    employmentCode,
                    employmentName
            );
            result.add(model);
        });
        if (result.size() == 0) {
            throw new BusinessException("Msg_1860");
        }
        return result;
    }

    /**
     * 日次データ
     */
    private static DailyData getDailyData(AffiliationStatusDto emp,
                                          List<DailyOutputItemsAnnualWorkLedger> dailyOutputItemList,
                                          Map<GeneralDate, Map<Integer, AttendanceItemDtoValue>> allValue,
                                          Map<Integer, Map<String, CodeNameInfoDto>> allDataMaster) {
        List<DailyValue> lstRightValue = new ArrayList<>();
        List<DailyValue> lstLeftValue = new ArrayList<>();
        String rightColumnName = null;
        CommonAttributesOfForms rightAttribute = null;
        String leftColumnName = null;
        CommonAttributesOfForms leftAttribute = null;
        // Loop 出力項目 日次
        for (int index = 0; index < dailyOutputItemList.size(); index++) {
            DailyOutputItemsAnnualWorkLedger item = dailyOutputItemList.get(index);
            val rank = item.getRank();
            if (index > 1) {
                break;
            }
            val itemValue = new ArrayList<DailyValue>();
            List<GeneralDate> listDate = emp.getPeriodInformation()
                    .stream()
                    .flatMap(y -> y.getDatePeriod().datesBetween().stream().map(q -> GeneralDate.legacyDate(q.date())))
                    .collect(Collectors.toList());
            for (GeneralDate l : listDate) {
                val value =
                        allValue.getOrDefault(l, null);
                if (value == null || item.getItemDetailAttributes() == CommonAttributesOfForms.OTHER_CHARACTERS
                        || item.getItemDetailAttributes() == CommonAttributesOfForms.OTHER_CHARACTER_NUMBER
                        || item.getItemDetailAttributes() == CommonAttributesOfForms.OTHER_NUMERICAL_VALUE) continue;
                val listAtId = item.getSelectedAttendanceItemList();
                StringBuilder character = new StringBuilder();
                Double actualValue = 0D;
                boolean alwaysNull = true;
                if (item.getItemDetailAttributes() == CommonAttributesOfForms.WORK_TYPE
                        || item.getItemDetailAttributes() == CommonAttributesOfForms.WORKING_HOURS) {
                    for (val d : listAtId) {
                        val sub = value.getOrDefault(d.getAttendanceItemId(), null);
                        if (sub == null || sub.getValue() == null) continue;
                        val master = item.getItemDetailAttributes() == CommonAttributesOfForms.WORK_TYPE ?
                                allDataMaster.getOrDefault(WORK_TYPE, null)
                                : allDataMaster.getOrDefault(WORKING_HOURS, null);

                        val name = master != null ? master.getOrDefault(sub.getValue(), null) : null;
                        character.append(" ").append(name != null ? name.getName() : sub.value());
                    }
                    itemValue.add(
                            new DailyValue(null, character.toString(), l));
                } else {
                    for (val d : listAtId) {
                        val sub = value.getOrDefault(d.getAttendanceItemId(), null);
                        if (sub == null || sub.getValue() == null) continue;
                        alwaysNull = false;
                        actualValue = actualValue + ((d.getOperator() == OperatorsCommonToForms.ADDITION ? 1 : -1) * Double.parseDouble(sub.getValue()));
                    }
                    itemValue.add(new DailyValue(alwaysNull ? null : actualValue, character.toString(), l));
                }

            }

            if (rank == 1) {
                leftColumnName = item.getName().v();
                leftAttribute = item.getItemDetailAttributes();
                lstLeftValue.addAll(itemValue);
            } else {
                lstRightValue.addAll(itemValue);
                rightColumnName = item.getName().v();
                rightAttribute = item.getItemDetailAttributes();
            }


        }

        return new DailyData(
                lstRightValue,
                lstLeftValue,
                rightColumnName,
                rightAttribute,
                leftColumnName,
                leftAttribute
        );
    }

    /**
     * 月次データ
     */
    private static List<MonthlyData> getMonthlyData(AffiliationStatusDto emp,
                                                    List<OutputItemWorkLedger> monthlyOutputItems,
                                                    List<MonthlyRecordValueImport> valueImports) {
        List<MonthlyData> lstMonthlyData = new ArrayList<>();
        // Loop 出力項目 月次
        List<YearMonth> yearMonthList = emp.getPeriodInformation()
                .stream()
                .flatMap(y -> y.getYearMonthPeriod().yearMonthsBetween().stream())
                .collect(Collectors.toList());

        Map<YearMonth, Map<Integer, ItemValue>> allValue = valueImports.stream()
                .collect(Collectors.toMap(MonthlyRecordValueImport::getYearMonth,
                        k -> k.getItemValues().stream().filter(distinctByKey(ItemValue::getItemId))
                                .collect(Collectors.toMap(ItemValue::getItemId, l -> l))));
        // Loop 出力項目 日次
        for (OutputItemWorkLedger monthlyItem : monthlyOutputItems) {
            List<MonthlyValue> lstMonthlyValue = new ArrayList<>();
            // 「期間」の中にループを行い
            for (val y : yearMonthList) {
                val value = allValue.getOrDefault(y, new HashMap<>());
                val monthlyRecordValues = monthlyItem.getSelectedAttendanceItemList();
                if (monthlyRecordValues != null) {
                    lstMonthlyValue.add(fromMonthlyRecordValue(monthlyItem, monthlyRecordValues, value, y));
                }
            }

            lstMonthlyData.add(new MonthlyData(lstMonthlyValue, monthlyItem.getName().v(), monthlyItem.getItemDetailAttributes()));
        }
        return lstMonthlyData;
    }

    private static MonthlyValue fromMonthlyRecordValue(OutputItemWorkLedger monthlyItem, List<OutputItemDetailAttItem> selectedAttendanceItemList, Map<Integer, ItemValue> itemValueMap, YearMonth ym) {
        StringBuilder character = new StringBuilder();
        Double actualValue = 0d;
        for (OutputItemDetailAttItem d : selectedAttendanceItemList) {
            val subItem = itemValueMap.getOrDefault(d.getAttendanceItemId(), null);
            if (subItem == null || monthlyItem.getItemDetailAttributes() == CommonAttributesOfForms.OTHER_CHARACTERS
                    || monthlyItem.getItemDetailAttributes() == CommonAttributesOfForms.OTHER_CHARACTER_NUMBER
                    || monthlyItem.getItemDetailAttributes() == CommonAttributesOfForms.OTHER_NUMERICAL_VALUE) continue;
            val rawValue = subItem.getValue();
            if (monthlyItem.getItemDetailAttributes() == CommonAttributesOfForms.WORK_TYPE ||
                    monthlyItem.getItemDetailAttributes() == CommonAttributesOfForms.WORKING_HOURS) {
                character.append(rawValue);
            } else {
                Double value = rawValue == null ? 0 : Double.parseDouble(rawValue);
                actualValue += value * (d.getOperator() == OperatorsCommonToForms.ADDITION ? 1 : -1);
            }

        }

        return new MonthlyValue(actualValue, character.toString(), ym);
    }

    public static interface Require {
        //[RQ 589] 社員の指定期間中の所属期間を取得する（年月）
        EmpAffInfoExportDto getAffiliationPeriod(List<String> listSid, YearMonthPeriod YMPeriod, GeneralDate baseDate);

        List<AttendanceResultDto> getValueOf(List<String> employeeIds, DatePeriod workingDatePeriod, Collection<Integer> itemIds);

        // [No.495]勤怠項目IDを指定して月別実績の値を取得（複数レコードは合算）
        Map<String, List<MonthlyRecordValueImport>> getActualMultipleMonth(
                List<String> employeeIds, YearMonthPeriod period, List<Integer> itemIds);

        Map<Integer, Map<String, CodeNameInfoDto>> getAllDataMaster(String companyId, GeneralDate dateReference,
                                                                    List<Integer> lstDivNO);

    }

    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
