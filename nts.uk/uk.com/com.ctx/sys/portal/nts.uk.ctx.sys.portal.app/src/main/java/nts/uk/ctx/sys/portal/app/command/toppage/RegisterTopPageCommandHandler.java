/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.app.command.toppage;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.app.find.toppage.TopPageNewDto;
import nts.uk.ctx.sys.portal.dom.enums.MenuClassification;
import nts.uk.ctx.sys.portal.dom.enums.System;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenu;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenuRepository;
import nts.uk.ctx.sys.portal.dom.toppage.ToppageNew;
import nts.uk.ctx.sys.portal.dom.toppage.ToppageNewRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class RegisterTopPageCommandHandler.
 */
@Stateless
public class RegisterTopPageCommandHandler extends CommandHandler<RegisterTopPageCommand> {

	/** The top page repository. */
	@Inject
	private ToppageNewRepository topPageNewRepository;
	
	@Inject
	private StandardMenuRepository standardMenuRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<RegisterTopPageCommand> context) {
		RegisterTopPageCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		String topPageCode = command.getTopPageCode();
		Optional<ToppageNew> findTopPage = topPageNewRepository.getByCidAndCode(companyId, topPageCode);
		// ドメインモデル「トップページ」の重複をチェックする
		if (findTopPage.isPresent()) {
			// エラーメッセージ（#Msg_3#）を表示する
			throw new BusinessException("Msg_3");
		} else {
			// to Domain
			TopPageNewDto memento = new TopPageNewDto();
			memento.setCid(companyId);
			memento.setLayoutDisp(BigDecimal.valueOf(command.getLayoutDisp()));
			memento.setTopPageCode(command.getTopPageCode());
			memento.setTopPageName(command.getTopPageName());
			ToppageNew topPage = ToppageNew.createFromMemento(memento);
			
			// ドメインモデル「トップページ」を登録する
			topPageNewRepository.insert(topPage);
			
			// アルゴリズム「標準メニューを新規登録する」を実行する
			int maxDisplayOrder = standardMenuRepository.maxOrderStandardMenu(companyId, System.COMMON.value, MenuClassification.TopPage.value);

			/**	EA add to standard menu
			 *  入力：
			 * 	会社ID←ログイン会社ID
			 * 	コード←トップページ。 コード
			 * 	システム←標準メニュー。 システム
			 * 	メニュー分類←標準メニュー。 メニューの分類
			 * 	対象項目←トップページ。 名
			 * 	表示名←トップページ 名
			 * 	表示順序←上記の表示順序	
			 * 	メニュー属性←0
			 * 	URL← "/nts.uk.com.web/view/ccg/008/a/index.xhtml"
			 * 	ウェブメニュー設定表示分類←1
			 * 	ログイン後表示分類←0
			 * 	ログ設定インジケータ←1
			 *  画面ID←A
			 *  クエリ文字列← "toppagecode =" +トップページ。 コード
			 * 	プログラムID← "CCG008"
			 *  Update by NWS_HuyCV ver11
			*/
			StandardMenu standardMenu = StandardMenu.toNewDomain(companyId, System.COMMON.value, MenuClassification.TopPage.value,
					"A", "toppagecode=" + topPage.getTopPageCode(), "CCG008", topPage.getTopPageCode().v(), topPage.getTopPageName().v(),
					topPage.getTopPageName().v(), maxDisplayOrder + 1, 0, "/nts.uk.com.web/view/ccg/008/a/index.xhtml", 1, 0, 1, 1, 0);
			// 画面項目「トップページ一覧」に登録した内容を追加する
			standardMenuRepository.insertStandardMenu(standardMenu);
		}
	}
}
