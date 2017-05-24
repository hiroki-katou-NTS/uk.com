package nts.uk.ctx.pr.core.app.find.itemmaster.itemdeduct;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.app.find.itemmaster.dto.itemdeduct.ItemDeductDto;
import nts.uk.ctx.pr.core.app.find.itemmaster.dto.itemdeduct.ItemDeductRegistrationInformationDto;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMaster;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMasterRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeduct.ItemDeduct;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeduct.ItemDeductRespository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductbd.ItemDeductBD;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductbd.ItemDeductBDRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ItemDeductFinder {
	@Inject
	ItemDeductRespository itemDeductRespo;
	@Inject
	ItemMasterRepository itemMasterRespo;
	@Inject
	ItemDeductBDRepository itemDeductBDRespo;

	public ItemDeductDto find(String itemCode) {
		Optional<ItemDeduct> itemOpt = this.itemDeductRespo.find(AppContexts.user().companyCode(), itemCode);
		if (!itemOpt.isPresent())
			return null;
		return ItemDeductDto.fromDomain(itemOpt.get());
	}

	public ItemDeductRegistrationInformationDto findItemDeductRegInfo(String itemCode) {
		String companyCode = AppContexts.user().companyCode();
		Optional<ItemDeduct> itemDeduct = this.itemDeductRespo.find(companyCode, itemCode);
		Optional<ItemMaster> itemMaster = this.itemMasterRespo.find(companyCode, 1, itemCode);
		List<ItemDeductBD> itemDeductBDList = this.itemDeductBDRespo.findAll(companyCode, itemCode);
		if (!itemDeduct.isPresent() || !itemMaster.isPresent()) {
			return null;
		} else {
			return ItemDeductRegistrationInformationDto.fromDomain(itemDeduct.get(), itemMaster.get(),
					itemDeductBDList);

		}
	}
}
