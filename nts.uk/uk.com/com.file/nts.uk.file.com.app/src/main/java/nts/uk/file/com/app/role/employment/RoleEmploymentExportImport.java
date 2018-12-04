package nts.uk.file.com.app.role.employment;

import nts.uk.ctx.sys.auth.dom.role.EmployeeReferenceRange;
import nts.uk.ctx.sys.auth.dom.role.RoleCode;
import nts.uk.ctx.sys.auth.dom.role.RoleType;

public class RoleEmploymentExportImport {
    /** The role id. */
    // Id
    private String roleId;

    /** The role code. */
    // コード
    private String roleCode;

    /** The role type. */
    // ロール種類
    private RoleType roleType;

    /** The personalinfo reference range. */
    // 参照範囲
    private String employeeReferenceRange;
    /**
     * スケジュール画面社員参照
     */
    private int scheduleEmployeeRef;

    /** Webメニュー名称 */
    private String webMenuName;
    /**
     * 未来日参照許可 FUTURE_DATE_REF_PERMIT
     */
    private int futureDateRefPermit;


}
