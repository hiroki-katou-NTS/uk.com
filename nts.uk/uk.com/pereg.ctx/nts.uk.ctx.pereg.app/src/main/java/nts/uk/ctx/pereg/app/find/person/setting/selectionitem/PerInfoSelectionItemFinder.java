package nts.uk.ctx.pereg.app.find.person.setting.selectionitem;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem.IPerInfoSelectionItemRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem.PerInfoSelectionItem;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PerInfoSelectionItemFinder {

	@Inject
	private IPerInfoSelectionItemRepository perInfoSelectionItemRepo;

	public List<PerInfoSelectionItemDto> getAllPerInfoSelectionItem(boolean isCps017) {
		String contractCode = AppContexts.user().contractCode();
		
		List<PerInfoSelectionItemDto> dtoList = this.perInfoSelectionItemRepo
				.getAllSelectionItemByContractCd(contractCode).stream()
				.map(selectionItem -> PerInfoSelectionItemDto.fromDomain(selectionItem)).collect(Collectors.toList());
		
		return dtoList;
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

	public List<PerInfoSelectionItemDto> getAllSelectionItem() {
		return this.perInfoSelectionItemRepo.getAllSelection(AppContexts.user().contractCode())
				.stream().map(c -> PerInfoSelectionItemDto.fromDomain(c)).collect(Collectors.toList());
	}
}
