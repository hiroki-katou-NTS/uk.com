package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

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
public class ApproverRepresenterImport {
	String approver;
	RepresenterInformationImport representer;
}
