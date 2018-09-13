package nts.uk.ctx.sys.gateway.dom.login.service;

import java.util.List;
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
	
}
