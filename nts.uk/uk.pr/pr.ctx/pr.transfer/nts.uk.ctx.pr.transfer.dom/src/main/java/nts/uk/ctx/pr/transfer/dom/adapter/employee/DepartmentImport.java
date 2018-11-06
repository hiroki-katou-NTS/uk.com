package nts.uk.ctx.pr.transfer.dom.adapter.employee;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * 所属部門
 */
@Value
@AllArgsConstructor
public class DepartmentImport {

    /**
     * The department code.
     */
    private String departmentCode; // 部門コード

    /**
     * The department name.
     */
    private String departmentName; // 部門表示名

    /**
     * The department generic name.
     */
    private String departmentGenericName; // 部門総称
}
