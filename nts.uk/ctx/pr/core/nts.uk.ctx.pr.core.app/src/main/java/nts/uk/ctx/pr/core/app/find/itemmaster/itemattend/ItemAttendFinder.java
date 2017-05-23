package nts.uk.ctx.pr.core.app.find.itemmaster.itemattend;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.app.find.itemmaster.dto.itemattend.ItemAttendDto;
import nts.uk.ctx.pr.core.app.find.itemmaster.dto.itemattend.ItemAttendRegistrationInformationDto;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMaster;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMasterRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemattend.ItemAttend;
import nts.uk.ctx.pr.core.dom.itemmaster.itemattend.ItemAttendRespository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ItemAttendFinder {

	@Inject
	ItemAttendRespository itemAttendRespo;
	@Inject
	ItemMasterRepository itemMasterRespo;

	public ItemAttendDto find(String itemCode) {

		Optional<ItemAttend> itemOpt = this.itemAttendRespo.find(AppContexts.user().companyCode(), itemCode);
		if (!itemOpt.isPresent())
			return null;
		return ItemAttendDto.fromDomain(itemOpt.get());
	}

	public ItemAttendRegistrationInformationDto findItemAttendRegInfo(String itemCode) {
		String companyCode = AppContexts.user().companyCode();
		Optional<ItemAttend> itemAttend = this.itemAttendRespo.find(companyCode, itemCode);
		Optional<ItemMaster> itemMaster = this.itemMasterRespo.find(companyCode, 2, itemCode);

		if (!itemAttend.isPresent() || !itemMaster.isPresent()) {
			return null;
		} else {
			return ItemAttendRegistrationInformationDto.fromDomain(itemAttend.get(), itemMaster.get());

		}
	}

}
