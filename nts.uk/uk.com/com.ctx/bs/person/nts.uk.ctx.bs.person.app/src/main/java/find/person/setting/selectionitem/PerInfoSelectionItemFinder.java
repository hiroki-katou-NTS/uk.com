package find.person.setting.selectionitem;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.IPerInfoSelectionItemRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.PerInfoHistorySelectionRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.PerInfoSelectionItem;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PerInfoSelectionItemFinder {

	@Inject
	private IPerInfoSelectionItemRepository perInfoSelectionItemRepo;

	@Inject
	private PerInfoHistorySelectionRepository historySelectionRepo;

	public List<PerInfoSelectionItemDto> getAllPerInfoSelectionItem() {
		// contracCode cua user dang dang nhap
		String contractCode = AppContexts.user().contractCode();
		return this.perInfoSelectionItemRepo.getAllPerInfoSelectionItem(contractCode).stream()
				.map(i -> PerInfoSelectionItemDto.fromDomain(i)).collect(Collectors.toList());
	}

	// selectionItemId
	public PerInfoSelectionItemDto getPerInfoSelectionItem(String selectionItemId) {
		//get selectionItemId
		Optional<PerInfoSelectionItem> opt = this.perInfoSelectionItemRepo.getPerInfoSelectionItem(selectionItemId);
		
		//check selectionItemId
		if (!opt.isPresent()) {
			return null;
		}
		return PerInfoSelectionItemDto.fromDomain(opt.get());
	}
}
