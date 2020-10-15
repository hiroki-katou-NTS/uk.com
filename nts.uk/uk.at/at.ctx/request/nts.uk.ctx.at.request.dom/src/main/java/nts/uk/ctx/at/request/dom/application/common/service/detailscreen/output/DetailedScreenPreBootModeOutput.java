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
}