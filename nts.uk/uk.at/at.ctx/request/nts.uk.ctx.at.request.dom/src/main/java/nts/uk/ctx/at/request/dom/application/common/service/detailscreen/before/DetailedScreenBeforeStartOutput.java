package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.User;

@Data
@AllArgsConstructor
public class DetailedScreenBeforeStartOutput {
	// 利用者
	private User user;
	// ステータス
	private ReflectPlanPerState reflectPlanState;
	// 承認できるフラグ
	private boolean authorizableFlags;
	// ログイン者の承認区分
	private ApprovalAtr approvalATR;
	// 代行期限切れフラグ
	private boolean alternateExpiration;
	// 過去申請か（boolean)
	private boolean isPastApp = false;
	
	public DetailedScreenBeforeStartOutput(
			User user,
			ReflectPlanPerState reflectPlanState,
			boolean authorizableFlags,
			ApprovalAtr approvalATR,
			boolean alternateExpiration
			) {
		
		this.user = user;
		this.reflectPlanState = reflectPlanState;
		this.authorizableFlags = authorizableFlags;
		this.approvalATR = approvalATR;
		this.alternateExpiration = alternateExpiration;
	}
	
}
