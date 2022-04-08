package nts.uk.screen.com.app.find.cmm030.b;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;

import nts.uk.screen.com.app.find.cmm030.b.dto.GetWorkplaceInfoDto;
import nts.uk.screen.com.app.find.cmm030.b.param.GetWorkplaceInfoParam;

/**
 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM030_自分の承認者設定.B：社員選択.メニュー別OCD.Ｂ：所属職場IDを取得する
 */
@Stateless
@TransactionAttribute
public class GetWorkplaceInfoFinder {

	@Inject
	private Cmm030BScreenQuery screenQuery;

	public GetWorkplaceInfoDto findData(GetWorkplaceInfoParam param) {
		return new GetWorkplaceInfoDto(this.screenQuery.getWorkplaceInfo(param.getSids(), param.getBaseDate()));
	}
}
