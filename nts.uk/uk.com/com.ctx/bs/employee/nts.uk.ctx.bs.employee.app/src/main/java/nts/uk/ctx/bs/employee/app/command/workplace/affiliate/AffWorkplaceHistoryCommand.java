package nts.uk.ctx.bs.employee.app.command.workplace.affiliate;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.PeregRecordId;

@Getter
public class AffWorkplaceHistoryCommand {

	/** 社員ID */
	@PeregEmployeeId
	private String employeeId;

	/** The history Id. */
	// 履歴ID
	@PeregRecordId
	private String historyId;
	
	/** The workplaceCode. */
	// 職場コード
	@PeregItem("")
	private String  workplaceCode;
	
	/** The normalWorkplaceCode. */
	// 通常職場コード
	@PeregItem("")
	private String  normalWorkplaceCode;
	
	/** The normalWorkplaceCode. */
	// 場所コード
	@PeregItem("")
	// TODO pending Q&A
	private String  locationCode;
	
	/** 期間 */
	@PeregItem("")
	private GeneralDate startDate;
	
	@PeregItem("")
	private GeneralDate endDate;
}
