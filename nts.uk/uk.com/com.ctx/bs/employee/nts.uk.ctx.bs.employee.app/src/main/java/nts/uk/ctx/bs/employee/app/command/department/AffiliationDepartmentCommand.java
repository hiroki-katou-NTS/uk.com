package nts.uk.ctx.bs.employee.app.command.department;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.PeregRecordId;

@Getter
public class AffiliationDepartmentCommand {

	/** The history Id. */
	// 履歴ID
	@PeregRecordId
	private String historyId;

	/** The Employee Id. */
	// 社員ID
	@PeregEmployeeId
	private String employeeId;

	/** The department code. */
	/* 部門コード */
	private String departmentCode;

	/** The Affiliation History Transfer type. */
	// 所属履歴異動種類
	@PeregItem("")
	private String affHistoryTranfsType;

	/** The Employee Id. */
	// 社員ID
	@PeregItem("")
	private String distributionRatio;
	
	/** The period. */
	@PeregItem("")
	private GeneralDate startDate;
	
	@PeregItem("")
	private GeneralDate endDate;
}
