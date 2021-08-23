package nts.uk.file.at.app.export.schedule.personalschedulebydate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.ctx.at.schedule.dom.shift.management.DateInformation;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.DisplayInfoOrganization;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonalScheduleByDateDataSource {
    // 会社情報
    private String companyName;

    /* 組織の表示情報 */
    private DisplayInfoOrganization displayInfoOrganization;

    /* 年月日情報 */
    private DateInformation dateInformation;

    /* 社員情報リスト */
    private List<EmployeeInformationImport> employeeInfoList;

    /* List< 社員勤務予定・実績 dto> */
}
