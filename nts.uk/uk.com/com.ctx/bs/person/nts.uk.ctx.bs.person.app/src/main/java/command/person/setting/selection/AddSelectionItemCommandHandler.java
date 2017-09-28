package command.person.setting.selection;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import command.person.info.category.GetListCompanyOfContract;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.bs.person.dom.person.setting.selection.IPerInfoSelectionItemRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selection.PerInfoHistorySelection;
import nts.uk.ctx.bs.person.dom.person.setting.selection.PerInfoHistorySelectionRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selection.PerInfoSelectionItem;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddSelectionItemCommandHandler extends CommandHandlerWithResult<AddSelectionItemCommand, String> {

	@Inject
	private IPerInfoSelectionItemRepository perInfoSelectionItemRepo;

	@Inject
	private PerInfoHistorySelectionRepository historySelectionRepository;

	@Override
	protected String handle(CommandHandlerContext<AddSelectionItemCommand> context) {
		AddSelectionItemCommand command = context.getCommand();
		String newId = IdentifierUtil.randomUniqueId();
		String rootCID = PersonInfoCategory.ROOT_COMPANY_ID;
		String newHistId = IdentifierUtil.randomUniqueId();

		Optional<PerInfoSelectionItem> opt = this.perInfoSelectionItemRepo
				.checkItemName(command.getSelectionItemName());
		if (opt.isPresent()) {// neu da ton tai SelectionItemName
			throw new BusinessException(new RawErrorMessage("Msg_513"));
		}

		PerInfoSelectionItem domain = PerInfoSelectionItem.createFromJavaType(newId, command.getSelectionItemName(),
				command.getMemo(), command.isSelectionItemClassification() == true ? 1 : 0,
				AppContexts.user().contractCode(), command.getIntegrationCode(),
				command.getFormatSelection().getSelectionCode(),
				command.getFormatSelection().isSelectionCodeCharacter() == true ? 1 : 0,
				command.getFormatSelection().getSelectionName(),
				command.getFormatSelection().getSelectionExternalCode());

		this.perInfoSelectionItemRepo.add(domain);

		// check SelectionItemClassification: true/false
		boolean itemClassification = command.isSelectionItemClassification();
		GeneralDate startDate = GeneralDate.ymd(1900, 1, 1);
		GeneralDate endDate = GeneralDate.ymd(9999, 12, 31);
		if (itemClassification == true) {
			PerInfoHistorySelection domainHist = PerInfoHistorySelection.historySelection(newHistId, newId, rootCID,
					endDate, startDate);

			this.historySelectionRepository.add(domainHist);
		} else {
			List<String> companyIdList = GetListCompanyOfContract.LIST_COMPANY_OF_CONTRACT;// todo
			for (String cid : companyIdList) {
				newHistId = IdentifierUtil.randomUniqueId();
				PerInfoHistorySelection domainHist = PerInfoHistorySelection.historySelection(newHistId, newId, cid,
						endDate, startDate);

				this.historySelectionRepository.add(domainHist);
			}

			newHistId = IdentifierUtil.randomUniqueId();
			PerInfoHistorySelection domainHist = PerInfoHistorySelection.historySelection(newHistId, newId, rootCID,
					endDate, startDate);

			this.historySelectionRepository.add(domainHist);
		}

		return newId;
	}
}
