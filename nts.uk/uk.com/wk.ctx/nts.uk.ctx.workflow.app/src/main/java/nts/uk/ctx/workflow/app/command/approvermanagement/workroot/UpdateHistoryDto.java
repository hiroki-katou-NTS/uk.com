package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateHistoryDto {
	/**承認ID*/
	public String approvalId;
	/**履歴ID*/
	private String historyId;
	 /**申請種類*/
    private Integer applicationType;
    /**就業ルート区分*/
    private int employRootAtr;
}
