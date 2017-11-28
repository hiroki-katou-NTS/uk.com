package nts.uk.ctx.bs.employee.app.find.employee.mngdata;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class EmployeeDataMngInfoDto {

	/** 会社ID */
	private String companyId;

	/** 個人ID */
	private String personId;

	/** 社員ID */
	private String employeeId;

	/** 社員コード */
	private String employeeCode;

	/** 削除状況 */
	private int deletedStatus;

	/** 一時削除日時 */
	private GeneralDate deleteDateTemporary;

	/** 削除理由 */
	private String removeReason;

	/** 外部コード */
	private String externalCode;
}
