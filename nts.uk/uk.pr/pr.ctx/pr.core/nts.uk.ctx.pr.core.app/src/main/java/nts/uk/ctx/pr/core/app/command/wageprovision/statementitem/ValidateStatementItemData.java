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
	 * 
	 * @param command
	 */
	public void validate(StatementItemDataCommand command) {
		String cid = AppContexts.user().companyId();
		val statementItem = command.getStatementItem();
		val statementItemName = command.getStatementItemName();

		if (statementItem == null) {
			return;
		}
		val listStatementItem = statementItemRepository.getByCategory(cid, statementItem.getCategoryAtr());
		if (command.isCheckCreate() && listStatementItem.stream()
				.anyMatch(i -> i.getItemNameCd().v().equals(statementItem.getItemNameCd()))) {
			throw new BusinessException("Msg_3");
		}

		if (statementItemName == null) {
			return;
		}
		val listCode = listStatementItem.stream().map(i -> {
			return i.getItemNameCd().v();
		}).collect(Collectors.toList());
		if (!command.isCheckCreate()) {
			listCode.removeIf(c -> c.equals(statementItem.getItemNameCd()));
		}
		if (listCode.size() == 0) {
			return;
		}
		val listStatementItemName = statementItemNameRepository.getStatementItemNameByListCode(cid, statementItem.getCategoryAtr(),
				listCode);
		if (listStatementItemName.stream().anyMatch(i -> i.getName().v().equals(statementItemName.getName()))) {
			throw new BusinessException("Msg_358");
		}
	}
}
