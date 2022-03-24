package nts.uk.screen.com.app.find.cmm030.a;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.screen.com.app.find.cmm030.a.dto.GetSetEmployeeListDto;
import nts.uk.screen.com.app.find.cmm030.a.param.GetSetEmployeeListParam;

/**
 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM030_自分の承認者設定.A：自分の承認者設定.メニュー別OCD.Ａ：複写のため既に設定されている社員を取得する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetSetEmployeeListFinder {

	@Inject
	private Cmm030AScreenQuery screenQuery;

	public GetSetEmployeeListDto findData(GetSetEmployeeListParam param) {
		return new GetSetEmployeeListDto(this.screenQuery.getSetEmployeeList(param.getBaseDate()));
	}
}
