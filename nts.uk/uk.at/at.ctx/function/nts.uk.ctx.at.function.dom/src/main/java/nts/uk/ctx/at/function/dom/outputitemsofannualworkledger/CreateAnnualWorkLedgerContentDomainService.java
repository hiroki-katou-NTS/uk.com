package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.function.dom.adapter.actualmultiplemonth.MonthlyRecordValueImport;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceResultDto;
import nts.uk.ctx.at.function.dom.commonform.ClosureDateEmployment;
import nts.uk.ctx.at.function.dom.commonform.GetSuitableDateByClosureDateUtility;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemDetailAttItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.StatusOfEmployee;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.DailyMonthlyClassification;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.OperatorsCommonToForms;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeBasicInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;

import javax.ejb.Stateless;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 年間勤務台帳の表示内容を作成する
 */
@Stateless
public class CreateAnnualWorkLedgerContentDomainService {
    public static List<AnnualWorkLedgerContent> getData(Require require, DatePeriod datePeriod, Map<String, EmployeeBasicInfoImport> lstEmployee,
                                                        AnnualWorkLedgerOutputSetting outputSetting, Map<String, WorkplaceInfor> lstWorkplaceInfor,
                                                        Map<String, ClosureDateEmployment> lstClosureDateEmployment) {
        List<AnnualWorkLedgerContent> result = new ArrayList<>();

        List<String> listSid = new ArrayList<>(lstEmployee.keySet());
        if (listSid.size() == 0) {
            throw new BusinessException("Msg_1862");
        }
        // 1 - Call 社員の指定期間中の所属期間を取得する
        val listEmployeeStatus = require.getListAffComHistByListSidAndPeriod(listSid, datePeriod);

        // 日次の場合 - 印刷対象フラグ　==　true
        val dailyOutputItems = outputSetting.getMonthlyOutputItemList().stream()
                .filter(x -> x.getDailyMonthlyClassification() == DailyMonthlyClassification.DAILY && x.isPrintTargetFlag())
                .collect(Collectors.toList());
        // 月次の場合 - 印刷対象フラグ　==　true
        val monthlyOutputItems = outputSetting.getMonthlyOutputItemList().stream()
                .filter(x -> x.getDailyMonthlyClassification() == DailyMonthlyClassification.MONTHLY && x.isPrintTargetFlag())
                .collect(Collectors.toList());

        // Loop 「社員の会社所属状況」の「対象社員」
        for (StatusOfEmployee emp : listEmployeeStatus) {

            val employee = lstEmployee.get(emp.getEmployeeId());
            val workplaceInfo = lstWorkplaceInfor.get(emp.getEmployeeId());
            val closureDateEmployment = lstClosureDateEmployment.get(emp.getEmployeeId());
            if (employee == null || workplaceInfo == null || closureDateEmployment == null) continue;

            val closureHistory = closureDateEmployment.getClosure().getClosureHistories().get(0);
            val closureDay = closureHistory.getClosureDate().getClosureDay().v();

            // 日次データ
            val dailyData = getDailyData(require, emp, dailyOutputItems);
            // 月次データ
            val lstMonthlyData = getMonthlyData(require, emp, monthlyOutputItems, closureDay);

            AnnualWorkLedgerContent model = new AnnualWorkLedgerContent(
                    dailyData,
                    lstMonthlyData,
                    employee.getEmployeeCode(),
                    employee.getEmployeeName(),
                    closureHistory.getClosureName().v(),
                    workplaceInfo.getWorkplaceCode(),
                    workplaceInfo.getWorkplaceName(),
                    closureDateEmployment.getEmploymentCode(),
                    closureDateEmployment.getEmploymentName()
            );
            result.add(model);
        }

        return result;
    }

    /**
     * 日次データ
     */
    private static DailyData getDailyData(Require require, StatusOfEmployee emp, List<OutputItem> dailyOutputItems) {
        List<DailyValue> lstRightValue = new ArrayList<>();
        List<DailyValue> lstLeftValue = new ArrayList<>();
        String rightColumnName = null;
        CommonAttributesOfForms rightAttribute = null;
        String leftColumnName = null;
        CommonAttributesOfForms leftAttribute = null;
        // Loop 出力項目 日次
        for (int index = 0; index < dailyOutputItems.size(); index++) {
            OutputItem item = dailyOutputItems.get(index);
            if (index > 1) {
                break;
            }

            if (index == 0) {
                leftColumnName = item.getName().v();
                leftAttribute = item.getItemDetailAttributes();
            } else {
                rightColumnName = item.getName().v();
                rightAttribute = item.getItemDetailAttributes();
            }

            val itemIds = item.getSelectedAttendanceItemList().stream().map(OutputItemDetailAttItem::getAttendanceItemId).collect(Collectors.toList());
            // 「所属状況」の「期間」の中にループを行い: ・社員ID、・①の「印刷期間」の一つ
            for (DatePeriod period : emp.getListPeriod()) {
                // 指定した期間内の勤怠項目IDに対応する項目を返す
                val listAttendances = require.getValueOf(emp.getEmployeeId(), period, itemIds);
                if (listAttendances != null) {
                    for (AttendanceResultDto attendance : listAttendances) {
                        val dailyData = fromAttendanceDto(attendance, item);
                        if (index == 0) {
                            lstLeftValue.add(dailyData);
                        } else {
                            lstRightValue.add(dailyData);
                        }
                    }
                }
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
    private static List<MonthlyData> getMonthlyData(Require require, StatusOfEmployee emp, List<OutputItem> monthlyOutputItems, int closureDay) {
        List<MonthlyData> lstMonthlyData = new ArrayList<>();
        // Loop 出力項目 月次
        for (OutputItem monthlyItem : monthlyOutputItems) {
            List<MonthlyValue> lstMonthlyValue = new ArrayList<>();
            val itemIds = monthlyItem.getSelectedAttendanceItemList().stream().map(OutputItemDetailAttItem::getAttendanceItemId).collect(Collectors.toList());

            // 「期間」の中にループを行い
            for (DatePeriod period : emp.getListPeriod()) {
                val yearMonthPeriod = GetSuitableDateByClosureDateUtility.getByClosureDate(period, closureDay);
                val monthlyRecordValues = (require.getActualMultipleMonth(
                        new ArrayList<>(Collections.singletonList(emp.getEmployeeId())), yearMonthPeriod, itemIds)).get(emp.getEmployeeId());
                if (monthlyRecordValues != null) {
                    for (val monthlyRecordValue : monthlyRecordValues) {
                        lstMonthlyValue.add(fromMonthlyRecordValue(monthlyRecordValue, monthlyItem));
                    }
                }
            }

            lstMonthlyData.add(new MonthlyData(lstMonthlyValue, monthlyItem.getName().v(), monthlyItem.getItemDetailAttributes()));
        }
        return lstMonthlyData;
    }

    private static DailyValue fromAttendanceDto(AttendanceResultDto attendance, OutputItem item) {
        StringBuilder character = new StringBuilder();
        Double actualValue = 0d;

        for (OutputItemDetailAttItem ite : item.getSelectedAttendanceItemList()) {
            val subItem = (attendance.getAttendanceItems().stream().
                    filter(x -> x.getItemId() == ite.getAttendanceItemId()).findFirst());
            if (subItem.isPresent()) {
                val rawValue = subItem.get().getValue();
                if (item.getItemDetailAttributes() == CommonAttributesOfForms.WORK_TYPE ||
                        item.getItemDetailAttributes() == CommonAttributesOfForms.WORKING_HOURS) {
                    character.append(rawValue);
                } else {
                    Double value = rawValue == null ? 0 : Double.parseDouble(rawValue);
                    if (ite.getOperator() == OperatorsCommonToForms.ADDITION) {
                        actualValue += value;
                    } else if (ite.getOperator() == OperatorsCommonToForms.SUBTRACTION)
                        actualValue -= value;
                }
            }
        }
        return new DailyValue(actualValue, character.toString(), attendance.getWorkingDate());
    }

    private static MonthlyValue fromMonthlyRecordValue(MonthlyRecordValueImport monthlyRecordValue, OutputItem item) {
        StringBuilder character = new StringBuilder();
        Double actualValue = 0d;

        for (OutputItemDetailAttItem ite : item.getSelectedAttendanceItemList()) {
            val subItem = (monthlyRecordValue.getItemValues().stream().
                    filter(x -> x.getItemId() == ite.getAttendanceItemId()).findFirst());
            if (subItem.isPresent()) {
                val rawValue = subItem.get().getValue();
                if (item.getItemDetailAttributes() == CommonAttributesOfForms.WORK_TYPE ||
                        item.getItemDetailAttributes() == CommonAttributesOfForms.WORKING_HOURS) {
                    character.append(rawValue);
                } else {
                    Double value = rawValue == null ? 0 : Double.parseDouble(rawValue);
                    if (ite.getOperator() == OperatorsCommonToForms.ADDITION) {
                        actualValue += value;
                    } else if (ite.getOperator() == OperatorsCommonToForms.SUBTRACTION)
                        actualValue -= value;
                }
            }
        }
        return new MonthlyValue(actualValue, character.toString(), monthlyRecordValue.getYearMonth());
    }

    public static interface Require {
        //[RQ 588] 社員の指定期間中の所属期間を取得する
        List<StatusOfEmployee> getListAffComHistByListSidAndPeriod(List<String> sid, DatePeriod datePeriod);

        List<AttendanceResultDto> getValueOf(String employeeId, DatePeriod workingDatePeriod, Collection<Integer> itemIds);

        // [No.495]勤怠項目IDを指定して月別実績の値を取得（複数レコードは合算）
        Map<String, List<MonthlyRecordValueImport>> getActualMultipleMonth(
                List<String> employeeIds, YearMonthPeriod period, List<Integer> itemIds);

    }
}
