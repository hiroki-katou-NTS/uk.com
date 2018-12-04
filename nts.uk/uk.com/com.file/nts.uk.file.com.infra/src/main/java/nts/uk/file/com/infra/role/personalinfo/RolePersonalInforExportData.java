package nts.uk.file.com.infra.role.personalinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.util.Map;

@Data
@Value
@AllArgsConstructor
public class RolePersonalInforExportData {
    /** The role id. */
    // Id
    private String roleId;

    /** The role code. */
    // コード
    private String roleCode;

    /** The role type. */
    // ロール種類
    private String roleType;

    /** The personalinfo reference range. */
    // 参照範囲
    private int employeeReferenceRange;

    /**
     * 未来日参照許可
     */
    private Boolean referFutureDate;

    private Map<String, String> functionNo;
}
