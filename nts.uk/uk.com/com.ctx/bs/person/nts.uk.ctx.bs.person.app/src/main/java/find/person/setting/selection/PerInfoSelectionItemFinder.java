package find.person.setting.selection;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.setting.selection.IPerInfoSelectionItemRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selection.PerInfoSelectionItem;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PerInfoSelectionItemFinder {

	@Inject
	private IPerInfoSelectionItemRepository perInfoSelectionItemRepo;

	public List<PerInfoSelectionItemDto> getAllPerInfoSelectionItem() {
		// contracCode cua user dang dang nhap
		String contractCode = AppContexts.user().contractCode();
		return this.perInfoSelectionItemRepo.getAllPerInfoSelectionItem(contractCode).stream()
				.map(i -> PerInfoSelectionItemDto.fromDomain(i)).collect(Collectors.toList());
	}

	public PerInfoSelectionItemDto getPerInfoSelectionItem(String selectionItemId) {
		Optional<PerInfoSelectionItem> opt = this.perInfoSelectionItemRepo.getPerInfoSelectionItem(selectionItemId);
		if(!opt.isPresent()){
			return null;
		}
		return PerInfoSelectionItemDto.fromDomain(opt.get());
		

	}
}
