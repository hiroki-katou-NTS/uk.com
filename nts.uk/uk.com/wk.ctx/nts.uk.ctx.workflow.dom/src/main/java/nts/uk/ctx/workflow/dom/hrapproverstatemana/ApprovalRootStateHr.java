package nts.uk.ctx.workflow.dom.hrapproverstatemana;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
/**
 * 人事承認ルートインスタンス
 * @author hoatt
 *
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ApprovalRootStateHr extends AggregateRoot {
	/**ID*/
	private String rootStateID;
	/**対象日*/
	private GeneralDate appDate;
	/**対象者*/
	private String employeeID;
	/**承認フェーズ*/
	private List<ApprovalPhaseStateHr> lstPhaseState;
}
