package nts.uk.ctx.sys.gateway.dom.login.service;

import java.util.List;

import nts.uk.ctx.sys.gateway.dom.stopbycompany.StopByCompany;
/**
 * 切替可能な会社一覧を取得する
 * @author Doan Duy Hung
 *
 */
public interface CollectCompanyList {
	
	/**
	 * 切替可能な会社一覧を取得する
	 * @param userID
	 * @return
	 */
	public List<String> getCompanyList(String userID);
	/**
	 * @author hoatt
	 * 利用停止のチェック
	 * @param 契約コード - contractCd
	 * @param ・会社ID（List） Before filter - lstCID
	 * @return 会社ID（List） After filter
	 */
	public List<String> checkStopUse(String contractCd, List<String> lstCID);
	/**
	 * @author hoatt
	 * 利用停止会社リストを取得する
	 * @param ドメインモデル「会社単位の利用停止」 - lstComStop
	 * @return 利用停止会社ID（List）
	 */
	public List<String> getLstComStopUse(List<StopByCompany> lstComStop);
}
