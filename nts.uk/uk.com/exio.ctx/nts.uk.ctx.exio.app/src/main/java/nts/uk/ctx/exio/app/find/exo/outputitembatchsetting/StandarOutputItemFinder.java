package nts.uk.ctx.exio.app.find.exo.outputitembatchsetting;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionExOutSetting;
import nts.uk.ctx.exio.dom.exo.condset.StandardAtr;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItem;

@Stateless
public class StandarOutputItemFinder {

	@Inject
	AcquisitionExOutSetting acquisitionExOutSetting;

	public List<StandarOutputItemDTO> getListStandarOutputItem(String condSetCd) {
		List<StandardOutputItem> standardOutputItem = acquisitionExOutSetting.getExOutItemList(condSetCd, "", null, StandardAtr.STANDARD,
				true);
		return standardOutputItem.stream().map(x -> StandarOutputItemDTO.fromdomain(x)).collect(Collectors.toList());
	}
}
