package nts.uk.ctx.pereg.app.command.person.setting.selectionitem;

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
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.IPerInfoSelectionItemRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.PerInfoHistorySelection;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.PerInfoHistorySelectionRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.PerInfoSelectionItem;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AddSelectionItemCommandHandler extends CommandHandlerWithResult<AddSelectionItemCommand, String> {

	@Inject
	private IPerInfoSelectionItemRepository perInfoSelectionItemRepo;

	@Inject
	private PerInfoHistorySelectionRepository historySelectionRepository;
	
	@Inject
	private ICompanyRepo companyRepo;

	@Override
	protected String handle(CommandHandlerContext<AddSelectionItemCommand> context) {
		AddSelectionItemCommand command = context.getCommand();
		String newId = IdentifierUtil.randomUniqueId();
		
		//
		String rootCID = AppContexts.user().zeroCompanyIdInContract();
		String newHistId = IdentifierUtil.randomUniqueId();

		// ドメインモデル「個人情報の選択項目」のエラーチェック
		Optional<PerInfoSelectionItem> optCheckExistByName = this.perInfoSelectionItemRepo
				.getSelectionItemByName(command.getSelectionItemName());

		// 「選択項目名称」は重複してはならない
		if (optCheckExistByName.isPresent()) {
			throw new BusinessException(new RawErrorMessage("Msg_513"));
		}

		//Checked -> person -> 0
		//Uncheck -> employee -> 1
		int isSelect = command.isSelectionItemClassification() == true ? 0 : 1;
		
		// ドメインモデル「個人情報の選択項目」を追加登録する
		PerInfoSelectionItem domain = PerInfoSelectionItem.createFromJavaType(newId, command.getSelectionItemName(),
				command.getMemo(), isSelect, AppContexts.user().contractCode(), command.getIntegrationCode(),
				command.getFormatSelection().getSelectionCode(),
				command.getFormatSelection().isSelectionCodeCharacter() == true ? 1 : 0,
				command.getFormatSelection().getSelectionName(),
				command.getFormatSelection().getSelectionExternalCode());

		// 「個人情報の選択項目」を追加登録する
		this.perInfoSelectionItemRepo.add(domain);

		// ドメインモデル「選択肢履歴」を登録する
		boolean itemClassification = command.isSelectionItemClassification();
		GeneralDate startDate = GeneralDate.ymd(1900, 1, 1);
		GeneralDate endDate = GeneralDate.ymd(9999, 12, 31);
		DatePeriod period = new DatePeriod(startDate, endDate);

		// 画面項目「グループ会社で共有する：選択項目区分をチェックする」
		if (itemClassification == true) {// TRUE → 0会社の場合
			PerInfoHistorySelection domainHist = PerInfoHistorySelection.createHistorySelection(newHistId, newId,
					rootCID, period);

			// 0会社の場合:「選択肢履歴」を登録する
			this.historySelectionRepository.add(domainHist);
		} else {// FALSE → 全会社 の場合
			List<String> companyIdList = companyRepo.acquireAllCompany();
			for (String cid : companyIdList) {
				newHistId = IdentifierUtil.randomUniqueId();
				PerInfoHistorySelection domainHist = PerInfoHistorySelection.createHistorySelection(newHistId, newId,
						cid, period);

				// 全会社 の場合:「選択肢履歴」を登録する
				this.historySelectionRepository.add(domainHist);
			}

			newHistId = IdentifierUtil.randomUniqueId();
			PerInfoHistorySelection domainHist = PerInfoHistorySelection.createHistorySelection(newHistId, newId,
					rootCID, period);

			// 0会社の場合: 「選択肢履歴」を登録する
			this.historySelectionRepository.add(domainHist);
		}

		return newId;
	}
}
