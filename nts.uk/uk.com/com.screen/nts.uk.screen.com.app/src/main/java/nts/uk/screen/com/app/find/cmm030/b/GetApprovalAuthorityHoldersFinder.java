package nts.uk.screen.com.app.find.cmm030.b;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.screen.com.app.find.cmm030.b.dto.GetApprovalAuthorityDto;
import nts.uk.screen.com.app.find.cmm030.b.param.GetApprovalAuthorityHoldersParam;

import javax.ejb.TransactionAttribute;

/**
 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM030_自分の承認者設定.B：社員選択.メニュー別OCD.Ｂ：承認権限保持者を取得する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetApprovalAuthorityHoldersFinder {

	@Inject
	private Cmm030BScreenQuery screenQuery;

	public GetApprovalAuthorityDto findData(GetApprovalAuthorityHoldersParam param) {
		return new GetApprovalAuthorityDto(
				this.screenQuery.getApprovalAuthorityHolders(param.getWorkplaceId(), param.getBaseDate()));
	}
}
