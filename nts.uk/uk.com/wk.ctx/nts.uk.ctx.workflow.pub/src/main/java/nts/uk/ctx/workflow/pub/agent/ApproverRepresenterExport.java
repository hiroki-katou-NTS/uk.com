package nts.uk.ctx.workflow.pub.agent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 承認者の代行情報リスト
 * 
 * @author tutk
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApproverRepresenterExport {
	String approver;
	RepresenterInformationExport representer;
}
