package nts.uk.query.app.user.information.employee.data.management.information;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;

/**
 * Dto 社員データ管理情報
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDataMngInfoDto {
    /**
     * 会社ID
     */
    private String companyId;

    /**
     * 個人ID
     */
    private String personId;

    /**
     * 社員ID
     */
    private String employeeId;

    /**
     * 社員コード
     */
    private String employeeCode;

    /**
     * 削除状況
     */
    private Integer deletedStatus;

    /**
     * 一時削除日時
     */
    private GeneralDateTime deleteDateTemporary;

    /**
     * 削除理由
     */
    private String removeReason;

    /**
     * 外部コード
     */
    private String externalCode;

    public static EmployeeDataMngInfoDto toDto (EmployeeDataMngInfo domain) {
        return new EmployeeDataMngInfoDto(
                domain.getCompanyId(),
                domain.getPersonId(),
                domain.getEmployeeId(),
                domain.getEmployeeCode().v(),
                domain.getDeletedStatus().value,
                domain.getDeleteDateTemporary(),
                domain.getRemoveReason().v(),
                domain.getExternalCode().v()
        );
    }
}
