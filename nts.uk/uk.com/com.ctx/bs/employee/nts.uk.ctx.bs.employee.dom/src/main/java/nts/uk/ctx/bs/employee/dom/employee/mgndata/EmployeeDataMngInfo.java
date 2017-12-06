package nts.uk.ctx.bs.employee.dom.employee.mgndata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeCode;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDataMngInfo extends AggregateRoot {

	/** 会社ID */
	private String companyId;

	/** 個人ID */
	private String pId;

	/** 社員ID */
	private String sId;

	/** 社員コード */
	private EmployeeCode sCd;

	/** 削除状況 */
	private EmployeeDeletionAttr deletedStatus;

	/** 一時削除日時 */
	private GeneralDate deleteDateTemporary;

	/** 削除理由 */
	private RemoveReason removeReason;
	
	/** 外部コード */
	private String  externalCode;
	
	public static EmployeeDataMngInfo createFromJavaType() {
		return new EmployeeDataMngInfo();
	}
}
