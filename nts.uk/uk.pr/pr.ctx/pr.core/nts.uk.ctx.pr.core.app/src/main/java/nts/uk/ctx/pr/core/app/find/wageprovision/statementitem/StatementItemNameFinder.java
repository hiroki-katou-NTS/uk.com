package nts.uk.ctx.pr.core.app.find.wageprovision.statementitem;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemNameRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class StatementItemNameFinder {
	@Inject
	private StatementItemNameRepository statementItemNameRepository;

	/**
	 * ドメインモデル「明細書項目名称」を取得する
	 * 
	 * @param 給与項目ID
	 * @return
	 */
	public StatementItemNameDto findStatementItemName(int categoryAtr, String itemNameCd) {
		String cid = AppContexts.user().companyId();
		val statementItemName = statementItemNameRepository.getStatementItemNameById(cid, categoryAtr, itemNameCd);
		return statementItemName.map(i -> StatementItemNameDto.fromDomain(i)).orElse(null);
	}
}
