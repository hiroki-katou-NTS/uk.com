package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailedScreenPreBootModeOutput;

@AllArgsConstructor
@Value
public class DetailedScreenPreBootModeDto {
	// 利用者
	int user;
	// ステータス
	int reflectPlanState;
	// 承認できるフラグ
	boolean authorizableFlags;
	// ログイン者の承認区分
	int approvalATR;
	// 代行期限切れフラグ
	boolean alternateExpiration;

	public static DetailedScreenPreBootModeDto convertToDto(DetailedScreenPreBootModeOutput domain) {
		if (domain == null)
			return null;
		return new DetailedScreenPreBootModeDto(
				domain.getUser().value, 
				domain.getReflectPlanState().value,
				domain.isAuthorizableFlags(), 
				domain.getApprovalATR().value, 
				domain.isAlternateExpiration());
	}

}
