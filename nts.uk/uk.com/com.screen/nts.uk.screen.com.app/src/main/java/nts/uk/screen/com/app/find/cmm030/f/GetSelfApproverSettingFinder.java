package nts.uk.screen.com.app.find.cmm030.f;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.screen.com.app.find.cmm030.f.dto.SelfApproverSettingDto;
import nts.uk.screen.com.app.find.cmm030.f.param.GetSelfApproverSettingParam;

/**
 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM030_自分の承認者設定.F：承認者の履歴確認.メニュー別OCD.Ｆ：自分の承認者設定を取得する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetSelfApproverSettingFinder {

	@Inject
	private Cmm030FScreenQuery screenQuery;
	
	public SelfApproverSettingDto findData(GetSelfApproverSettingParam param) {
		return this.screenQuery.getSelfApproverSetting(param.getSid(), param.getApprovalIds());
	}
}
