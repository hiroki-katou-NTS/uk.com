package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author Anh.BD
 *
 */

@AllArgsConstructor
@Getter
public class AgentInfoImport {
	/**
	 * 承認者社員ID
	 */
	private String approverID;
	
	/**
	 * 代行者社員ID
	 */
	private String agentID;
	
	/**
	 * 開始日
	 */
	private GeneralDate startDate;
	
	/**
	 * 終了日
	 */
	private GeneralDate endDate;
}
