package nts.uk.ctx.at.aggregation.dom.scheduletable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.aggregation.dom.adapter.employee.EmployeeInformationImport;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmpRankInfor;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice.EmpTeamInfor;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle.EmpLicenseClassification;

import java.util.Map;
import java.util.Optional;

/**
 * スケジュール表の個人情報
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonalInfoScheduleTable {
    // 社員ID
    private String employeeId;

    // 社員情報
    private EmployeeInformationImport employeeInfo;

    // 社員所属チーム情報
    private Optional<EmpTeamInfor> employeeTeamInfo;

    // 社員ランク情報
    private Optional<EmpRankInfor> employeeRankInfo;

    // 社員免許区分
    private Optional<EmpLicenseClassification> employeeLicenseClassification;

    // 個人情報Map
    // private Map<ScheduleTablePersonalInfoItem, ScheduleTablePersonalInfoItemData> personInfoMap;
}
