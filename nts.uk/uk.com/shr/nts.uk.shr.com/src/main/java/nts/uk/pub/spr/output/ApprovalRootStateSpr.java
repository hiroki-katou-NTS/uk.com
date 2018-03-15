package nts.uk.pub.spr.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprovalRootStateSpr {
	private String rootStateID;
	
	private Integer rootType;
	
	private String historyID;
	
	private GeneralDate approvalRecordDate;
	
	private String employeeID;
}
