package nts.uk.ctx.pr.core.app.find.wageprovision.statementitem;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class StatementItemFinder {
	@Inject
	private StatementItemRepository statementItemRepository;

	/**
	 * ドメインモデル「明細書項目」を取得する
	 * 
	 * @return
	 */
	public List<StatementItemDto> findAllStatementItem() {
		String cid = AppContexts.user().companyId();
		val listStatementItem = statementItemRepository.getAllItemByCid(cid);
		return listStatementItem.stream().map(item -> {
			return StatementItemDto.fromDomain(item);
		}).collect(Collectors.toList());
	}
	
	/**
	 * ドメインモデル「明細書項目」を取得する
	 * 
	 * @param カテゴリ区分
	 * @param 項目名コード
	 * @return 
	 */
	public List<StatementItemDto> findByItemNameCd(int categoryAtr, String itemNameCd) {
		String cid = AppContexts.user().companyId();
		val listStatementItem = statementItemRepository.getByItemNameCd(cid, categoryAtr, itemNameCd);
		return listStatementItem.stream().map(item -> {
			return StatementItemDto.fromDomain(item);
		}).collect(Collectors.toList());
	}
	
	/**
	 * ドメインモデル「明細書項目」を取得する
	 * 
	 * @param カテゴリ区分
	 * @return
	 */
	public List<StatementItemDto> findByCategory(int categoryAtr) {
		String cid = AppContexts.user().companyId();
		val listStatementItem = statementItemRepository.getByCategory(cid, categoryAtr);
		return listStatementItem.stream().map(item -> {
			return StatementItemDto.fromDomain(item);
		}).collect(Collectors.toList());
	}

}
