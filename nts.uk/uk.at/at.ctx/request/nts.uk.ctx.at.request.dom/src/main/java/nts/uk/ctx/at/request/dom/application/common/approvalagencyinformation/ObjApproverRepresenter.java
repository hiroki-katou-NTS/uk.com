package nts.uk.ctx.at.request.dom.application.common.approvalagencyinformation;

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
public class ObjApproverRepresenter {
	String approver;
	String representer;
}
