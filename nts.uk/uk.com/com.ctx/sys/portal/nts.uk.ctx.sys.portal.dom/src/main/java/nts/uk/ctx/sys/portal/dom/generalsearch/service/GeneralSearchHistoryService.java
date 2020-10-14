package nts.uk.ctx.sys.portal.dom.generalsearch.service;

/**
 * The Interface GeneralSearchHistoryService.
 */
public interface GeneralSearchHistoryService {

	/**
	 * Check role search manual.
	 * ログイン者はマニュアル検索できるか判断する
	 * @return true, if successful
	 */
	public boolean checkRoleSearchManual();
}
