package nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selectionitem.add;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pereg.dom.company.ICompanyRepo;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.history.SelectionHistory;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.history.SelectionHistoryRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem.IPerInfoSelectionItemRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem.PerInfoSelectionItem;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AddSelectionItemCommandHandler extends CommandHandlerWithResult<AddSelectionItemCommand, String> {

	@Inject
	private IPerInfoSelectionItemRepository perInfoSelectionItemRepo;

	@Inject
	private SelectionHistoryRepository selectionHistoryRepo;

	@Inject
	private ICompanyRepo companyRepo;

	@Override
	protected String handle(CommandHandlerContext<AddSelectionItemCommand> context) {
		AddSelectionItemCommand command = context.getCommand();
		String newId = IdentifierUtil.randomUniqueId();
		String contractCode = AppContexts.user().contractCode();
		
		validateSelectionItemName(command.getSelectionItemName(), null);
		
		// ドメインモデル「個人情報の選択項目」を追加登録する
		PerInfoSelectionItem domain = PerInfoSelectionItem.createFromJavaType(contractCode, newId,
				command.getSelectionItemName(), command.isCharacterType(),
				command.getCodeLength(), command.getNameLength(), command.getExtraCodeLength(),
				command.getIntegrationCode(), command.getMemo());

		// 「個人情報の選択項目」を追加登録する
		this.perInfoSelectionItemRepo.add(domain);

		String newHistId = IdentifierUtil.randomUniqueId();

		// ドメインモデル「選択肢履歴」を登録する
		DatePeriod period = new DatePeriod(GeneralDate.min(), GeneralDate.max());
		
		SelectionHistory domainHist = SelectionHistory.createHistorySelection(newHistId, newId,
				AppContexts.user().zeroCompanyIdInContract(), period);

		this.selectionHistoryRepo.add(domainHist);
		
		List<String> companyIdList = companyRepo.acquireAllCompany();
		for (String cid : companyIdList) {
			newHistId = IdentifierUtil.randomUniqueId();
			domainHist = SelectionHistory.createHistorySelection(newHistId, newId,
					cid, period);

			this.selectionHistoryRepo.add(domainHist);
		}
		
		return newId;
	}
	
	private void validateSelectionItemName(String name, String id) {
		Optional<PerInfoSelectionItem> optCheckExistByName = this.perInfoSelectionItemRepo
				.getSelectionItemByName(AppContexts.user().contractCode(), name, id);

		if (optCheckExistByName.isPresent()) {
			throw new BusinessException(new RawErrorMessage("Msg_513"));
		}
	}
}
