package nts.uk.screen.com.app.find.cmm030.a;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.screen.com.app.find.cmm030.a.dto.InitScreenADto;
import nts.uk.screen.com.app.find.cmm030.a.param.InitScreenAParam;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM030_自分の承認者設定.A：自分の承認者設定.メニュー別OCD.Ａ：自分の承認者設定を起動する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class InitScreenAFinder {

	@Inject
	private Cmm030AScreenQuery screenQuery;

	public InitScreenADto findData(InitScreenAParam param) {
		String sid = AppContexts.user().employeeId();
		return new InitScreenADto(this.screenQuery.initScreenA(), this.screenQuery.findCurrentEmployeeInfo(),
				this.screenQuery.findApproverDisplayData(sid, param.getBaseDate()));
	}
}
