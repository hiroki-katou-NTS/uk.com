package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class DeleteLastHistoryCommand {

	/**
	 * 社員ID
	 */
	private String sid;
	
	/**
	 * 開始日
	 */
	private GeneralDate startDate;
	
	/**
	 * 承認ID<List>
	 */
	private List<UpdateHistoryDto> approvalInfos;
}
