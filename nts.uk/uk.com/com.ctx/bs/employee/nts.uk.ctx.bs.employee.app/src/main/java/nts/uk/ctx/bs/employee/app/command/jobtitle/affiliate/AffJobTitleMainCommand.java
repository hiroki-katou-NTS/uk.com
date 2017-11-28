package nts.uk.ctx.bs.employee.app.command.jobtitle.affiliate;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.PeregRecordId;

@Getter
public class AffJobTitleMainCommand {

	@PeregEmployeeId
	// 社員ID
	private String sid;
	
	/** The job title code. */
	// 職位コード
	private String jobTitleCode;
	
	/** The AffJobHistoryItemNote. */
	// 備考
	@PeregItem("")
	private String note;
	
	@PeregRecordId
	private String historyId;
	
	/** The job title history. */
	@PeregItem("")
	private GeneralDate startDate;
	
	@PeregItem("")
	private GeneralDate endDate;

}
