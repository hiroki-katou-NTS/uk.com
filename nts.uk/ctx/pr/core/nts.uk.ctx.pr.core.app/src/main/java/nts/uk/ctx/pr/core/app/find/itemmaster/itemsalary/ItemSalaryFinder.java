package nts.uk.ctx.pr.core.app.find.itemmaster.itemsalary;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.app.find.itemmaster.dto.itemsalary.ItemSalaryDto;
import nts.uk.ctx.pr.core.app.find.itemmaster.dto.itemsalary.ItemSalaryRegistrationInformationDto;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMaster;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMasterRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ItemSalary;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ItemSalaryRespository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalarybd.ItemSalaryBD;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalarybd.ItemSalaryBDRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ItemSalaryFinder {
	@Inject
	ItemSalaryRespository itemSalaryRespo;
	@Inject
	ItemMasterRepository itemMasterRepo;
	@Inject
	ItemSalaryBDRepository itemSalaryBDRespo;

	public ItemSalaryDto find(String itemCode) {
		Optional<ItemSalary> itemOpt = this.itemSalaryRespo.find(AppContexts.user().companyCode(), itemCode);
		if (!itemOpt.isPresent())
			return null;
		return ItemSalaryDto.fromDomain(itemOpt.get());
	}

	public ItemSalaryRegistrationInformationDto findItemSalaryRegInfo(String itemCode) {
		String companyCode = AppContexts.user().companyCode();
		Optional<ItemSalary> itemSalary = this.itemSalaryRespo.find(companyCode, itemCode);
		Optional<ItemMaster> itemMaster = this.itemMasterRepo.find(companyCode,0,
				itemCode);
		List<ItemSalaryBD> itemSalaryBDList = this.itemSalaryBDRespo.findAll(companyCode,
				itemCode);
		if (!itemSalary.isPresent() || !itemMaster.isPresent()) {
			return null;
		} else {
			return ItemSalaryRegistrationInformationDto.fromDomain(itemSalary.get(), itemMaster.get(),itemSalaryBDList);
			 
		}
	}

}
