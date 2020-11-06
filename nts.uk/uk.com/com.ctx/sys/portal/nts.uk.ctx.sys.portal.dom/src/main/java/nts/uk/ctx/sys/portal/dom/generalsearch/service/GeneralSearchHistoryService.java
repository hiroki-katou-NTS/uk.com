package nts.uk.ctx.sys.portal.dom.generalsearch.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.adapter.generalsearch.LoginRoleResponsibleAdapter;

/**
 * The Interface GeneralSearchHistoryService.
 */
@Stateless
public class GeneralSearchHistoryService {
	
	/** The adapter. */
	@Inject
	private LoginRoleResponsibleAdapter adapter;
	
	/**
	 * Check role search manual.
	 * ログイン者はマニュアル検索できるか判断する
	 *
	 * @param forCompanyAdmin the for company admin
	 * @param forSystemAdmin the for system admin
	 * @return true, if successful
	 */
	public boolean checkRoleSearchManual(String forCompanyAdmin, String forSystemAdmin) {
		// ログインユーザコンテキスト．権限情報．システム管理者のロールID ≠ null 
		// OR ログインユーザコンテキスト．権限情報．会社管理者のロールID ≠ null 
		if (forCompanyAdmin != null || forSystemAdmin != null) {
			return true;
		}
		// call：ログイン者が担当者かチェックする＃担当者か ()														
		// return　担当者かのOUTPUT											
		return this.adapter.getLoginResponsible().isPersonIncharge();
	}
}
