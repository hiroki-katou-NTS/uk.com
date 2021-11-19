package nts.uk.file.at.app.export.form9.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.aggregation.dom.form9.EmployeeIdAndYmd;
import nts.uk.ctx.at.aggregation.dom.form9.Form9OutputEmployeeInfo;
import nts.uk.ctx.at.aggregation.dom.form9.MedicalTimeOfEmployee;
import nts.uk.ctx.bs.employee.dom.workplace.group.hospitalofficeinfo.HospitalBusinessOfficeInfo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 職場グループに関係する表示情報dto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisplayInfoRelatedToWorkplaceGroupDto {
    /** Map<社員IDと年月日、社員の出力医療時間> */
    private Map<EmployeeIdAndYmd, MedicalTimeOfEmployee> medicalTimeOfEmpMap;

    /** 病棟・事業所情報 */
    private Optional<HospitalBusinessOfficeInfo> hospitalBusinessOffice;

    /** 職場グループID  */
    private String wkpGroupId;

    /** 職場グループコード */
    private String wkpGroupCode;

    /** 職場グループ名称  */
    private String wkpGroupName;

    /** List<様式９の出力社員情報> */
    private List<Form9OutputEmployeeInfo> form9OutputEmpInfos;

}
