package nts.uk.ctx.workflow.dom.hrapproverstatemana;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmPerson;
/**
 * 人事承認枠
 * @author hoatt
 *
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ApprovalFrameHr extends DomainObject {
	/**承認枠No*/
	private int frameOrder;
	/**確定区分*/
	private ConfirmPerson confirmAtr;
	/**対象日*/
	private GeneralDate appDate;
	/**承認者情報*/
	private List<ApproverInforHr> lstApproverInfo;
	
	public static ApprovalFrameHr convert( int frameOrder, int confirmAtr, GeneralDate appDate, List<ApproverInforHr> lstApproverInfo){
		return new  ApprovalFrameHr (frameOrder,
				EnumAdaptor.valueOf(confirmAtr, ConfirmPerson.class),
				 appDate, lstApproverInfo);
	}
}
