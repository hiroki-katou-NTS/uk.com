package nts.uk.ctx.bs.employee.dom.jobtitle.approver;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleCode;

@Getter
@AllArgsConstructor
public class ApproverGroup extends AggregateRoot {
	
	private String companyID;
	
	private JobTitleCode approverGroupCD;
	
	private ApproverName approverGroupName;
	
	private List<ApproverJob> approverJobList;
}
