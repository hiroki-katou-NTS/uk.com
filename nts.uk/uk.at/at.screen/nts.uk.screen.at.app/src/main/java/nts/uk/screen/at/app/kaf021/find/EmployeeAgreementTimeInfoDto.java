package nts.uk.screen.at.app.kaf021.find;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeAgreementTimeInfoDto {
    /**
     * 社員ID
     */
    private String employeeId;
    /**
     * 社員コード
     */
    private String employeeCode;
    /**
     * 社員名
     */
    private String employeeName;
    /**
     * 所属ID
     */
    private String affId;
    /**
     * 所属名称
     */
    private String affName;
    /**
     * 管理期間の36協定時間
     */
    private List<AgreementTimeOfManagePeriodDto> agreementTimes;

}