package nts.uk.ctx.pereg.app.find.person.setting.selectionitem;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.person.setting.selectionitem.IPerInfoSelectionItemRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.PerInfoSelectionItem;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PerInfoSelectionItemFinder {

	@Inject
	private IPerInfoSelectionItemRepository perInfoSelectionItemRepo;

	public List<PerInfoSelectionItemDto> getAllPerInfoSelectionItem(boolean hasCompanyId) {
		String contractCode = AppContexts.user().contractCode();
		if (hasCompanyId) {
			String companyId = AppContexts.user().companyId();
			return this.perInfoSelectionItemRepo.getAllSelectionItemByContractCdAndCID(contractCode, companyId).stream()
					.map(i -> PerInfoSelectionItemDto.fromDomain(i)).collect(Collectors.toList());
		} else {
			return this.perInfoSelectionItemRepo.getAllSelectionItemByContractCd(contractCode).stream()
					.map(i -> PerInfoSelectionItemDto.fromDomain(i)).collect(Collectors.toList());
		}
	}

	public PerInfoSelectionItemDto getPerInfoSelectionItem(String selectionItemId) {
		Optional<PerInfoSelectionItem> opt = this.perInfoSelectionItemRepo
				.getSelectionItemBySelectionItemId(selectionItemId);

		if (!opt.isPresent()) {
			return null;
		}
		return PerInfoSelectionItemDto.fromDomain(opt.get());
	}
	// getAllSelection

	public List<PerInfoSelectionItemDto> getAllSelectionItem(int selectionItemClsAtr) {
		return this.perInfoSelectionItemRepo.getAllSelection(selectionItemClsAtr).stream()
				.map(c -> PerInfoSelectionItemDto.fromDomain(c)).collect(Collectors.toList());
	}
}
