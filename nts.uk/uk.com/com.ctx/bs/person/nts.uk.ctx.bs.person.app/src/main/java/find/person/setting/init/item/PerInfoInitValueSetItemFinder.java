package find.person.setting.init.item;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.setting.init.item.PerInfoInitValueSetItem;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.PerInfoInitValueSetItemRepository;

@Stateless
public class PerInfoInitValueSetItemFinder {
	
	@Inject
	private PerInfoInitValueSetItemRepository settingItemRepo;

	public List<PerInfoInitValueSetItem> getAllItem(String perInfoCtgId) {
		
		List<PerInfoInitValueSetItem> itemlst = this.settingItemRepo.getAllItem(perInfoCtgId);

		return itemlst;
	}

}
