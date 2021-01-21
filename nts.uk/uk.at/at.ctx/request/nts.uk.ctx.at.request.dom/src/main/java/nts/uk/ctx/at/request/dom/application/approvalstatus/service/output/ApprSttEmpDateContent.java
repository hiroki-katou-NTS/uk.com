package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprSttEmpDateContent {
	private String dateStr;
	
	private int appType;
	
	private int prePostAtr;
	
	private String content;
	
	private String reflectedState;
	
	private String approvalStatus;
	
	private String phase1;
	
	private String phase2;
	
	private String phase3;
	
	private String phase4;
	
	private String phase5;
}
