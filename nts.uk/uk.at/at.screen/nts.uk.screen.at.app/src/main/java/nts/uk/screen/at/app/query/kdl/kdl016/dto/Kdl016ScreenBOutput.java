package nts.uk.screen.at.app.query.kdl.kdl016.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;

import java.util.List;

@Data
@AllArgsConstructor
public class Kdl016ScreenBOutput {
    /** List＜クエリモデル「社員情報」＞ */
    private List<EmployeeInformationDto> employeeInforList;

    /** Map<組織ID、組織の表示情報> */
    private List<OrganizationDisplayInfoDto> orgInfoList;
}


