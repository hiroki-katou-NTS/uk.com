package nts.uk.ctx.exio.app.find.exo.item;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemDataRepository;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionExOutSetting;
import nts.uk.ctx.exio.dom.exo.condset.StandardAtr;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class StdOutputItemFinder {
	@Inject
	private AcquisitionExOutSetting acquisitionExOutSetting;
	@Inject
	private CtgItemDataRepository ctgItemDataRepository;

	public List<StdOutItemDto> getOutItems(String condSetCd) {
		String userID = AppContexts.user().userId();
		List<CtgItemData> listCtgItemData = ctgItemDataRepository.getAllCtgItemData();
		return acquisitionExOutSetting.getExOutItemList(condSetCd, userID, null, StandardAtr.STANDARD, false).stream()
				.map(item -> StdOutItemDto.fromDomain(item, listCtgItemData)).collect(Collectors.toList());
	}
}
