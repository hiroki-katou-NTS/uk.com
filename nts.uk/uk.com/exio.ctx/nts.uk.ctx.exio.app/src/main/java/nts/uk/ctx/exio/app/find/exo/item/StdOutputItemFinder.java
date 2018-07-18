package nts.uk.ctx.exio.app.find.exo.item;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionExOutSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class StdOutputItemFinder {
	@Inject
	private AcquisitionExOutSetting acquisitionExOutSetting;

	public List<StdOutItemDto> getOutItems(String condSetCd) {
		String userID = AppContexts.user().userId();
		return acquisitionExOutSetting.getExOutItemList(condSetCd, userID, null, true, false).stream()
				.map(item -> StdOutItemDto.fromDomain(item)).collect(Collectors.toList());
	}
}
