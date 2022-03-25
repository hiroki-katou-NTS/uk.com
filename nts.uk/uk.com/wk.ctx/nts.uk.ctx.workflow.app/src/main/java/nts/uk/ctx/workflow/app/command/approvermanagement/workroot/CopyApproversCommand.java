package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class CopyApproversCommand {

	/**
	 * 複写元の社員ID
	 */
	private String sourceSid;
	
	/**
	 * 基準日
	 */
	private GeneralDate baseDate;
	
	/**
	 * 複写先の社員ID<List>
	 */
	private List<String> targetSids;
}
