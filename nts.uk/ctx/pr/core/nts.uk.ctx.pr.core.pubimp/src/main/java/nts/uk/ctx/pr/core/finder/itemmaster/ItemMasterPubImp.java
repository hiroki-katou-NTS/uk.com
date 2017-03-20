package nts.uk.ctx.pr.core.finder.itemmaster;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.itemmaster.ItemMasterRepository;
import nts.uk.ctx.pr.core.finder.itemmaster.ItemMasterPub;
import nts.uk.ctx.pr.core.finder.itemmaster.ItemMasterSEL_3_Dto;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ItemMasterPubImp implements ItemMasterPub {
	@Inject
	private ItemMasterRepository itemMasterRepo;

	@Override
	public List<ItemMasterSEL_3_Dto> find_SEL_3(int categoryAtr) {
		
		return this.itemMasterRepo.findAll_SEL_3(AppContexts.user().companyCode(), categoryAtr).stream()
				.map(item -> {
					return new ItemMasterSEL_3_Dto(item.getItemCode().v(), item.getItemAbName().v());
				}).collect(Collectors.toList());

	}
}
