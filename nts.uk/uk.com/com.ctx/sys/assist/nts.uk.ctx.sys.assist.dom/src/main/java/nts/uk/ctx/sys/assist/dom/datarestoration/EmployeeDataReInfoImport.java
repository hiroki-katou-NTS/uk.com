package nts.uk.ctx.sys.assist.dom.datarestoration;

import lombok.Value;
import nts.arc.time.GeneralDateTime;

//社員データ管理情報 <imported>
@Value
public class EmployeeDataReInfoImport {

	/** 会社ID */
	private String companyId;

	/** 個人ID */
	private String personId;

	/** 社員ID */
	private String employeeId;

	/** 社員コード */
	private String employeeCode;

	/** 一時削除日時 */
	private GeneralDateTime deleteDateTemporary;

	/** 削除理由 */
	private String removeReason;

	/** 外部コード */
	private String externalCode;
}
