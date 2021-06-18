package nts.uk.screen.com.app.find.cmm015;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.query.pub.jobtitle.affiliate.JobTitleHistoryQueryPub;
import nts.uk.query.pub.workplace.affiliate.WorkplaceHistoryQueryPub;

/**
 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM015_職場異動の登録.A:職場異動の登録.メニュー別OCD.当日の異動を取得する.当日の異動を取得する
 * @author NWS-DungDV
 *
 */
@Stateless
public class TransferOfDayScreenQuery {

	@Inject
	private WorkplaceHistoryQueryPub workplaceHistoryQuery;
	
	@Inject
	private JobTitleHistoryQueryPub jobTitleHistoryQuery;
	
	/**
	 * 当日の異動を取得する
	 * @param sids
	 * @param referDate
	 * @return 【output】 職場異動社員：List<社員>, 職位異動社員：List<社員>
	 */
	public TransferOfDay get(List<String> sids, GeneralDate baseDate) {
		
		// 1: 基準日時点に所属職場履歴変更している社員を取得する
		List<String> wkpEmployees = workplaceHistoryQuery.getEmployees(sids, baseDate);
		
		// 2: 基準日時点に所属職位履歴変更している社員を取得する
		List<String> jtEmployees = jobTitleHistoryQuery.getEmployees(sids, baseDate);
		
		return new TransferOfDay(wkpEmployees, jtEmployees);
		
	}
}
