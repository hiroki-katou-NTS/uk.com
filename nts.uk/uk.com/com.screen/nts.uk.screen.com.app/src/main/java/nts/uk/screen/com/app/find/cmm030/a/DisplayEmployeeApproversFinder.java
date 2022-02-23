package nts.uk.screen.com.app.find.cmm030.a;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.screen.com.app.find.cmm030.a.dto.DisplayEmployeeApproversDto;
import nts.uk.screen.com.app.find.cmm030.a.param.DisplayEmployeeApproversParam;

/**
 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM030_自分の承認者設定.A：自分の承認者設定.メニュー別OCD.Ａ：社員の承認者を表示する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DisplayEmployeeApproversFinder {

	@Inject
	private Cmm030AScreenQuery screenQuery;

	public DisplayEmployeeApproversDto findData(DisplayEmployeeApproversParam param) {
		return new DisplayEmployeeApproversDto(
				this.screenQuery.findApproverDisplayData(param.getSid(), param.getBaseDate()));
	}
}
