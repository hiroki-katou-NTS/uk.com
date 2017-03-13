package nts.uk.ctx.pr.core.app.find.itemsalary;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.app.find.itemsalary.dto.ItemSalaryDto;
import nts.uk.ctx.pr.core.dom.itemSalary.ItemSalary;
import nts.uk.ctx.pr.core.dom.itemSalary.ItemSalaryRespository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
public class ItemSalaryFinder {
	@Inject
	ItemSalaryRespository itemSalaryRespo;

	public ItemSalaryDto find(String itemCode) {
		Optional<ItemSalary> itemOpt = this.itemSalaryRespo.find(AppContexts.user().companyCode(), itemCode);
		if (!itemOpt.isPresent())
			return null;
		return ItemSalaryDto.fromDomain(itemOpt.get());
	}

}
