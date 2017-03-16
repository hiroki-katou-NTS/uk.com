package nts.uk.ctx.pr.core.app.find.itemmaster.itemsalaryperiod;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.app.find.itemmaster.dto.itemsalaryperiod.ItemSalaryPeriodDto;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalaryperiod.ItemSalaryPeriod;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalaryperiod.ItemSalaryPeriodRepository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
public class ItemSalaryPeriodFinder {
	@Inject
	ItemSalaryPeriodRepository itemSalaryPeriodRepo;

	public ItemSalaryPeriodDto find(String itemCode) {

		Optional<ItemSalaryPeriod> itemOpt = this.itemSalaryPeriodRepo.find(AppContexts.user().companyCode(), itemCode);
		if (!itemOpt.isPresent())
			return null;
		return ItemSalaryPeriodDto.fromDomain(itemOpt.get());

	}

}
