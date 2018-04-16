package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;

@Data
@AllArgsConstructor
public class DetailedScreenPreBootModeOutput {
	//利用者
	User user;
	//ステータス
	ReflectPlanPerState reflectPlanState;
	//承認できるフラグ
	boolean authorizableFlags;
	//ログイン者の承認区分
	ApprovalAtr approvalATR;
	//代行期限切れフラグ
	boolean alternateExpiration;
	//ドメインモデル「申請」．入力者 == ログイン者社員ID OR ドメインモデル「申請」．申請者 == ログイン者社員ID
	private boolean loginInputOrApproval;
}