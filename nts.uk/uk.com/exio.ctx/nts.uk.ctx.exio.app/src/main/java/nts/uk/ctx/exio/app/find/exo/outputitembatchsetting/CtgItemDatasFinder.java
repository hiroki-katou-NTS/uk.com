package nts.uk.ctx.exio.app.find.exo.outputitembatchsetting;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemDataRepository;

@Stateless
public class CtgItemDatasFinder {
	@Inject
	private CtgItemDataRepository mCtgItemDataRepository;

	public List<CtgItemDataDto> getListExOutCtgItemData(int categoryId) {
		int displayClassfication = 1;
		List<CtgItemData> listData = mCtgItemDataRepository.getAllByCategoryId(categoryId, displayClassfication);
		return listData.stream().map(x -> CtgItemDataDto.fromdomain(x)).collect(Collectors.toList());
	}
}
