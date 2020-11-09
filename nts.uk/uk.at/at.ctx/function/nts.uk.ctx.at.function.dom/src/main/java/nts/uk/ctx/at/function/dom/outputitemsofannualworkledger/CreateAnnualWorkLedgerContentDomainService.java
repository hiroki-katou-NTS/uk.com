package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.function.dom.adapter.actualmultiplemonth.MonthlyRecordValueImport;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceResultDto;
import nts.uk.ctx.at.function.dom.commonform.ClosureDateEmployment;
import nts.uk.ctx.at.function.dom.commonform.GetSuitableDateByClosureDateUtility;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemDetailSelectionAttendanceItem;
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
            throw new BusinessException("Msg_1816");
        }
        // 1 - Call 社員の指定期間中の所属期間を取得する
        val listEmployeeStatus = require.getListAffComHistByListSidAndPeriod(listSid, datePeriod);

        // ※１．印刷対象フラグ　==　true
        val dailyOutputItems = outputSetting.getOutputItemList().stream()
                .filter(x -> x.getDailyMonthlyClassification() == DailyMonthlyClassification.DAILY && x.isPrintTargetFlag())
                .collect(Collectors.toList());
        val monthlyOutputItems = outputSetting.getOutputItemList().stream()
                .filter(x -> x.getDailyMonthlyClassification() == DailyMonthlyClassification.MONTHLY && x.isPrintTargetFlag())
                .collect(Collectors.toList());
        // Loop 「社員の会社所属状況」の「対象社員」
        for (StatusOfEmployee emp : listEmployeeStatus) {// 日次データ
            val employee = lstEmployee.get(emp.getEmployeeId());
            val closureDateEmployment = lstClosureDateEmployment.get(emp.getEmployeeId());
            if (closureDateEmployment == null) continue;
            val workplaceInfo = lstWorkplaceInfor.get(emp.getEmployeeId());
            val closureHistory = closureDateEmployment.getClosure().getClosureHistories().get(0);

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

                int finalIndex = index;
                if (finalIndex == 0) {
                    leftColumnName = item.getName().v();
                    leftAttribute = item.getItemDetailAttributes();
                } else {
                    rightColumnName = item.getName().v();
                    rightAttribute = item.getItemDetailAttributes();
                }

                val listItem = item.getSelectedAttendanceItemList();
                // 1の「所属状況」の「期間」の中にループを行い: ・社員ID、・①の「印刷期間」の一つ
                emp.getListPeriod().forEach((DatePeriod i) -> i.datesBetween().forEach(wd -> {
                    val listAttendances = require.getValueOf(emp.getEmployeeId(), wd,
                            listItem.stream().map(OutputItemDetailSelectionAttendanceItem::getAttendanceItemId).collect(Collectors.toList()));

                    StringBuilder character = new StringBuilder();
                    Double actualValue = 0d;

                    if (item.getItemDetailAttributes() == CommonAttributesOfForms.WORK_TYPE ||
                            item.getItemDetailAttributes() == CommonAttributesOfForms.WORKING_HOURS) {
                        for (OutputItemDetailSelectionAttendanceItem ite : listItem) {
                            val subItem = (listAttendances.getAttendanceItems().stream().
                                    filter(x -> x.getItemId() == ite.getAttendanceItemId()).findFirst());
                            subItem.ifPresent(attendanceItemDtoValue -> character.append(attendanceItemDtoValue.getValue()));
                        }
                    } else {
                        for (OutputItemDetailSelectionAttendanceItem ite : listItem) {
                            val subItem = (listAttendances.getAttendanceItems().stream().
                                    filter(x -> x.getItemId() == ite.getAttendanceItemId()).findFirst());
                            if (subItem.isPresent()) {
                                if (ite.getOperator() == OperatorsCommonToForms.ADDITION) {
                                    actualValue += Double.parseDouble(subItem.get().getValue());
                                } else if (ite.getOperator() == OperatorsCommonToForms.SUBTRACTION)
                                    actualValue -= Double.parseDouble(subItem.get().getValue());
                            }
                        }
                    }
                    if (finalIndex == 0) {
                        lstLeftValue.add(new DailyValue(actualValue, character.toString(), wd));
                    } else {
                        lstRightValue.add(new DailyValue(actualValue, character.toString(), wd));
                    }
                }));
            }
            DailyData dailyData = new DailyData(
                    lstRightValue,
                    lstLeftValue,
                    rightColumnName,
                    rightAttribute,
                    leftColumnName,
                    leftAttribute
            );

            List<MonthlyData> lstMonthlyData = new ArrayList<>();
            // Loop 出力項目 月次
            for (OutputItem monthlyItem : monthlyOutputItems) {
                List<MonthlyValue> lstMonthlyValue = new ArrayList<>();
                emp.getListPeriod().forEach((DatePeriod period) -> {
                    val listItem = monthlyItem.getSelectedAttendanceItemList();
                    val itemIds = listItem.stream().map(OutputItemDetailSelectionAttendanceItem::getAttendanceItemId).collect(Collectors.toList());
                    val yearMonthPeriod = GetSuitableDateByClosureDateUtility.getByClosureDate(
                            period, closureHistory.getClosureDate().getClosureDay().v());
                    val monthlyRecordValues = (require.getActualMultipleMonth(
                            new ArrayList<>(Collections.singletonList(emp.getEmployeeId())), yearMonthPeriod, itemIds)).get(emp.getEmployeeId());

                    for (val monthlyRecordValue : monthlyRecordValues) {
                        StringBuilder character = new StringBuilder();
                        Double actualValue = 0d;
                        if (monthlyItem.getItemDetailAttributes() == CommonAttributesOfForms.WORK_TYPE ||
                                monthlyItem.getItemDetailAttributes() == CommonAttributesOfForms.WORKING_HOURS) {
                            for (OutputItemDetailSelectionAttendanceItem ite : listItem) {
                                val subItem = (monthlyRecordValue.getItemValues().stream().
                                        filter(x -> x.getItemId() == ite.getAttendanceItemId()).findFirst());
                                subItem.ifPresent(attendanceItemDtoValue -> character.append(attendanceItemDtoValue.getValue()));
                            }
                        } else {
                            for (OutputItemDetailSelectionAttendanceItem ite : listItem) {
                                val subItem = (monthlyRecordValue.getItemValues().stream().
                                        filter(x -> x.getItemId() == ite.getAttendanceItemId()).findFirst());
                                if (subItem.isPresent()) {
                                    if (ite.getOperator() == OperatorsCommonToForms.ADDITION) {
                                        actualValue += Double.parseDouble(subItem.get().getValue());
                                    } else if (ite.getOperator() == OperatorsCommonToForms.SUBTRACTION)
                                        actualValue -= Double.parseDouble(subItem.get().getValue());
                                }
                            }
                        }

                        lstMonthlyValue.add(new MonthlyValue(actualValue, character.toString(), monthlyRecordValue.getYearMonth()));
                    }
                });

                lstMonthlyData.add(new MonthlyData(lstMonthlyValue, monthlyItem.getName().v(), monthlyItem.getItemDetailAttributes()));
            }

            AnnualWorkLedgerContent model = new AnnualWorkLedgerContent(
                    dailyData,
                    lstMonthlyData,
                    employee == null ? "" : employee.getEmployeeCode(),
                    employee == null ? "" : employee.getEmployeeName(),
                    closureHistory.getClosureName().v(),
                    workplaceInfo == null ? "" : workplaceInfo.getWorkplaceCode(),
                    workplaceInfo == null ? "" : workplaceInfo.getWorkplaceName(),
                    closureDateEmployment.getEmploymentCode(),
                    closureDateEmployment.getEmploymentName()
            );
            result.add(model);
        }

        return result;
    }

    public static interface Require {
        //[RQ 588] 社員の指定期間中の所属期間を取得する
        List<StatusOfEmployee> getListAffComHistByListSidAndPeriod(List<String> sid, DatePeriod datePeriod);

        AttendanceResultDto getValueOf(String employeeId, GeneralDate workingDate, Collection<Integer> itemIds);

        // [No.495]勤怠項目IDを指定して月別実績の値を取得（複数レコードは合算）
        Map<String, List<MonthlyRecordValueImport>> getActualMultipleMonth(
                List<String> employeeIds, YearMonthPeriod period, List<Integer> itemIds);
    }
}
