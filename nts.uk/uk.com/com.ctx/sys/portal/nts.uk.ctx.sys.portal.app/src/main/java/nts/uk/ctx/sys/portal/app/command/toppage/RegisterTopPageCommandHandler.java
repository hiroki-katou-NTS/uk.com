/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.app.command.toppage;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.enums.MenuAtr;
import nts.uk.ctx.sys.portal.dom.enums.MenuClassification;
import nts.uk.ctx.sys.portal.dom.enums.System;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenu;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenuRepository;
import nts.uk.ctx.sys.portal.dom.toppage.TopPage;
import nts.uk.ctx.sys.portal.dom.toppage.TopPageRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class RegisterTopPageCommandHandler.
 */
@Stateless
public class RegisterTopPageCommandHandler extends CommandHandler<RegisterTopPageCommand> {

	/** The top page repository. */
	@Inject
	private TopPageRepository topPageRepository;
	
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
		Optional<TopPage> findTopPage = topPageRepository.findByCode(companyId, topPageCode);
		if (findTopPage.isPresent()) {
			throw new BusinessException("Msg_3");
		} else {
			// to Domain
			TopPage topPage = command.toDomain();
			// add
			topPageRepository.add(topPage);

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
			*/
			StandardMenu standardMenu = StandardMenu.createFromJavaType(companyId, topPage.getTopPageCode().v(),
					topPage.getTopPageName().v(), topPage.getTopPageName().v(), 0, MenuAtr.Menu.value,
					"/nts.uk.com.web/view/ccg/008/a/index.xhtml", System.COMMON.value, MenuClassification.TopPage.value,
					1, 0, "CCG008", "A", "toppagecode=" + topPage.getTopPageCode(), 1, 1, 1);
			standardMenuRepository.insertStandardMenu(standardMenu);
		}
	}
}
