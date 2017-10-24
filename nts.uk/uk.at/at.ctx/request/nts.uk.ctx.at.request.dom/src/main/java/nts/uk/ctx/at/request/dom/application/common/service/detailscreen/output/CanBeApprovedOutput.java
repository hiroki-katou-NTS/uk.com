package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;

@Data
@AllArgsConstructor
public class CanBeApprovedOutput {
	/**
	 * 承認できるフラグ
	 */
	Boolean authorizableFlags;
	/**
	 * ログイン者の承認区分
	 */
	ApprovalAtr approvalATR;
	/**
	 * 代行期限切れフラグ
	 */
	Boolean alternateExpiration;
	
}