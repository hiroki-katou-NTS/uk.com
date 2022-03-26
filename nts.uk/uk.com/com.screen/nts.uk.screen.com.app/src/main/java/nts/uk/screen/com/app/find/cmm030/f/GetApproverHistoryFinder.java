package nts.uk.screen.com.app.find.cmm030.f;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.screen.com.app.find.cmm030.f.dto.SummarizePeriodDto;

import javax.ejb.TransactionAttribute;

/**
 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM030_自分の承認者設定.F：承認者の履歴確認.メニュー別OCD.Ｆ：承認者の履歴を取得する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetApproverHistoryFinder {

	@Inject
	private Cmm030FScreenQuery screenQuery;
	
	public List<SummarizePeriodDto> findData(String sid) {
		return this.screenQuery.getApproverHistory(sid);
	}
}
