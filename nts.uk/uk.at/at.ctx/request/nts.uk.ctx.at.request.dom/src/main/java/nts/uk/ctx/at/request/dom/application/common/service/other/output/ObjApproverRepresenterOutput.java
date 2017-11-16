package nts.uk.ctx.at.request.dom.application.common.service.other.output;

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
public class ObjApproverRepresenterOutput {
	String approver;
	String representer;
}
