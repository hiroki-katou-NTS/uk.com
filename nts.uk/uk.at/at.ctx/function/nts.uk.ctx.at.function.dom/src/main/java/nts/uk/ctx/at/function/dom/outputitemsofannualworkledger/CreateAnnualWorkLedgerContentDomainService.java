package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceResultDto;
import nts.uk.ctx.at.function.dom.commonform.ClosureDateEmployment;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemDetailSelectionAttendanceItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.StatusOfEmployee;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.DailyMonthlyClassification;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeBasicInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
        val outputItems = outputSetting.getOutputItemList().stream().filter(OutputItem::isPrintTargetFlag).collect(Collectors.toList());
        // Loop 「社員の会社所属状況」の「対象社員」
        listEmployeeStatus.forEach(emp -> {
            // Loop 出力項目
            outputItems.forEach(item -> {
                if (item.getDailyMonthlyClassification() == DailyMonthlyClassification.DAILY) {
                    // 1の「所属状況」の「期間」の中にループを行い: ・社員ID、・①の「印刷期間」の一つ
                    emp.getListPeriod().forEach(i -> i.datesBetween().forEach(wd -> {
                        val listAttendances = require.getValueOf(emp.getEmployeeId(), wd,
                                item.getSelectedAttendanceItemList().stream().map(OutputItemDetailSelectionAttendanceItem::getAttendanceItemId)
                                        .collect(Collectors.toList()));

                        val employee = lstEmployee.get(emp.getEmployeeId());
                        val closureDateEmployment = lstClosureDateEmployment.get(emp.getEmployeeId());
                        val workplaceInfo = lstWorkplaceInfor.get(emp.getEmployeeId());

                        AnnualWorkLedgerContent model = new AnnualWorkLedgerContent(
                                null,
                                null,
                                employee == null ? "" : employee.getEmployeeCode(),
                                employee == null ? "" : employee.getEmployeeName(),
                                closureDateEmployment == null ? "" : closureDateEmployment.getClosureDate().toString(), //TODO QA
                                workplaceInfo == null ? "" : workplaceInfo.getWorkplaceCode(),
                                workplaceInfo == null ? "" : workplaceInfo.getWorkplaceName(),
                                closureDateEmployment == null ? "" : closureDateEmployment.getEmploymentCode(),
                                closureDateEmployment == null ? "" : closureDateEmployment.getEmploymentName()
                        );
                    }));
                } else {

                }
            });
        });

        return null;
    }

    public static interface Require {
        //[RQ 588] 社員の指定期間中の所属期間を取得する
        List<StatusOfEmployee> getListAffComHistByListSidAndPeriod(List<String> sid, DatePeriod datePeriod);

        AttendanceResultDto getValueOf(String employeeId, GeneralDate workingDate, Collection<Integer> itemIds);
    }
}
