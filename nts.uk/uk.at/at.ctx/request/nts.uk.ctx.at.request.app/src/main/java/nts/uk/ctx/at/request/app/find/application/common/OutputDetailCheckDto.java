package nts.uk.ctx.at.request.app.find.application.common;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailedScreenPreBootModeOutput;
@Value
public class OutputDetailCheckDto {
	//利用者
		private int user;
		//ステータス
		private int reflectPlanState;
		//承認できるフラグ
		private boolean authorizableFlags;
		//ログイン者の承認区分
		private int approvalATR;
		//代行期限切れフラグ
		private boolean alternateExpiration;
		//ドメインモデル「申請」．入力者 == ログイン者社員ID OR ドメインモデル「申請」．申請者 == ログイン者社員ID
		private boolean loginInputOrApproval;
	
		public static OutputDetailCheckDto fromDomain(DetailedScreenPreBootModeOutput domain) {
			return new OutputDetailCheckDto(
					domain.getUser().value,
					domain.getReflectPlanState().value,
					domain.isAuthorizableFlags(),
					domain.getApprovalATR().value,
					domain.isAlternateExpiration(),
					domain.isLoginInputOrApproval()
					);
		}
}
