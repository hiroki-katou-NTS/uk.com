package nts.uk.ctx.sys.portal.dom.generalsearch.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.adapter.generalsearch.LoginRoleResponsibleAdapter;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class GeneralSearchHistoryServiceImpl.
 */
@Stateless
public class GeneralSearchHistoryServiceImpl implements GeneralSearchHistoryService {

	/** The adapter. */
	@Inject
	private LoginRoleResponsibleAdapter adapter;
	/**
	 * Check role search manual.
	 * ログイン者はマニュアル検索できるか判断する
	 * @return true, if successful
	 */
	@Override
	public boolean checkRoleSearchManual() {
		// ログインユーザコンテキスト．権限情報．システム管理者のロールID ≠ null 
		// OR ログインユーザコンテキスト．権限情報．会社管理者のロールID ≠ null 
		if (AppContexts.user().roles().forCompanyAdmin() != null || AppContexts.user().roles().forSystemAdmin() != null) {
			return true;
		}
		// call：ログイン者が担当者かチェックする＃担当者か ()														
		// return　担当者かのOUTPUT											
		return this.adapter.getLoginResponsible().isPersonIncharge();
	}

}
