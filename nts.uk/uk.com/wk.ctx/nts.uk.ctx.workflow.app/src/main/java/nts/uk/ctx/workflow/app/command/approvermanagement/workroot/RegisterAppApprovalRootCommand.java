package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.DataDisplayComDto;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.DataDisplayPsDto;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.DataDisplayWpDto;

@Value
public class RegisterAppApprovalRootCommand {

	/**就業ルート区分: 会社(0)　－　職場(1)　－　社員(2)*/
	private int rootType;
	/**work place id*/
	private String workpplaceId;
	/** employee id*/
	private String employeeId;
	/**check add history*/
	private boolean checkAddHist;
	/**list right*/
	private List<CompanyAppRootADto> root;
	/**ls is selected*/
	private AddHistoryDto addHist;
	
	private boolean checkAddRoot;
	private boolean checkEdit;
	
	private boolean checkDelete;
	private List<DataDeleteDto> lstDelete;
	private List<Integer> lstAppType;
	
	private String startDate;
	private String endDate;
	private String approvalId;
//	private int mode;//0: common; 1: private
}
