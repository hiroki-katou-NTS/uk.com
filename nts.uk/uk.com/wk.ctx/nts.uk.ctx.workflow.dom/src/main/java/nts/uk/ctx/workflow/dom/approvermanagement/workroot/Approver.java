package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
/**
 * 承認者
 * @author hoatt
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class Approver extends DomainObject{
	/**承認者順序*/
	private int approverOrder;
	/**承認者Gコード*/
	private String jobGCD;
	/**社員ID*/
	private String employeeId;
	/**確定者*/
	private ConfirmPerson confirmPerson;
	/**特定職場ID*/
	private String specWkpId;
	
	public static Approver createSimpleFromJavaType(
			int approverOrder,
			String jobGCD,
			String employeeId,
			int confirmPerson,
			String specWkpId){
		return new Approver(
				approverOrder,
				jobGCD,
				employeeId,
				EnumAdaptor.valueOf(confirmPerson, ConfirmPerson.class),
				specWkpId);
	}
	
	public boolean isConfirmer() {
		return this.confirmPerson == ConfirmPerson.CONFIRM;
	}
}
