package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import java.util.List;

import lombok.Value;

@Value
public class RegisterAppApprovalRootCommand {

	/**就業ルート区分: 会社(0)　－　職場(1)　－　社員(2)*/
	private int rootType;
	private boolean checkDelete;
	private boolean checkAddHist;
	private boolean checkAddRoot;
	private boolean checkEdit;
	private String workpplaceId;
	private String employeeId;
	private List<DataDeleteDto> lstDelete;
	private List<AddHistoryDto> lstAddHist;
}
