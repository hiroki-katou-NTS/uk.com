package nts.uk.ctx.pr.core.app.find.itemmaster.itemattend;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.app.find.itemmaster.dto.itemattend.ItemAttendDto;
import nts.uk.ctx.pr.core.dom.itemmaster.itemattend.ItemAttend;
import nts.uk.ctx.pr.core.dom.itemmaster.itemattend.ItemAttendRespository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
public class ItemAttendFinder {

	@Inject
	ItemAttendRespository itemAttendRespo;

	public ItemAttendDto find(String itemCode) {

		Optional<ItemAttend> itemOpt = this.itemAttendRespo.find(AppContexts.user().companyCode(), itemCode);
		if (!itemOpt.isPresent())
			return null;
		return ItemAttendDto.fromDomain(itemOpt.get());
	}

}
