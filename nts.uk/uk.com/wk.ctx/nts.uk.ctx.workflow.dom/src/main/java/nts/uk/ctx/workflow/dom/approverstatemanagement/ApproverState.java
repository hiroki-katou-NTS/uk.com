package nts.uk.ctx.workflow.dom.approverstatemanagement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
/**
 * 承認枠 : 承認者
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApproverState extends DomainObject {
	
	private String companyID;
	
	private String rootStateID;
	
	private RootType rootType;
	
	private Integer phaseOrder;
	
	private Integer frameOrder;
	
	private String approverID;
}
