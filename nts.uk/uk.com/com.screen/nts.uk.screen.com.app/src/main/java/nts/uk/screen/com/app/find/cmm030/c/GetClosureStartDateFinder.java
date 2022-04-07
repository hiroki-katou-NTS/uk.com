package nts.uk.screen.com.app.find.cmm030.c;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;

/**
 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM030_自分の承認者設定.C：新規の登録.メニュー別OCD.Ｃ：締め初日を取得する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetClosureStartDateFinder {

	@Inject
	private Cmm030CScreenQuery screenQuery;
	
	public GeneralDate findData(String sid) {
		return this.screenQuery.getClosureStartDate(sid).orElse(null);
	}
}
