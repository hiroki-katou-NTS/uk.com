package nts.uk.ctx.pr.core.app.command.wageprovision.statementitem;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemNameRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ValidateStatementItemData {
	@Inject
	private StatementItemRepository statementItemRepository;
	@Inject
	private StatementItemNameRepository statementItemNameRepository;
	/**
	 * アルゴリズム「登録時チェック」を実施
	 * @param command
	 */
	public void validate(StatementItemDataCommand command){
		String cid = AppContexts.user().companyId();
		val listStatementItem = statementItemRepository.getByCategory(cid, command.getCategoryAtr());

		if (listStatementItem.stream().anyMatch(i -> i.getItemNameCd().v().equals(command.getItemNameCd()))) {
			throw new BusinessException("Msg_3");
		}

		val listSalaryItemId = listStatementItem.stream().map(i -> {
			return i.getSalaryItemId();
		}).collect(Collectors.toList());

		val listStatementItemName = statementItemNameRepository.getStatementItemNameByListSalaryItemId(cid, listSalaryItemId);
		if (listStatementItemName.stream().anyMatch(i -> i.getName().v().equals(command.getName()))) {
			throw new BusinessException("Msg_358");
		}
	}
}
