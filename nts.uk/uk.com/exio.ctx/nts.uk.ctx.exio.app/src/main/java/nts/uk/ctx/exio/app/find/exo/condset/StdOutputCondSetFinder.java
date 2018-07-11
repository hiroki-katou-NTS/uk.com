package nts.uk.ctx.exio.app.find.exo.condset;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.app.find.exo.item.StdOutItemDto;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionSettingList;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetRepository;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItem;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
/**
 * 出力条件設定（定型）
 */
public class StdOutputCondSetFinder {

	@Inject
	private AcquisitionSettingList acquisitionSettingList;

	@Inject
	private StdOutputCondSetRepository finder;

	@Inject
	private StandardOutputItemRepository standardOutputItemRepository;

	public List<StdOutputCondSetDto> getAllStdOutputCondSet() {
		return finder.getAllStdOutputCondSet().stream().map(item -> StdOutputCondSetDto.fromDomain(item))
				.collect(Collectors.toList());
	}

	public List<StdOutputCondSet> getCndSet() {
		String cId = AppContexts.user().companyId();
		return acquisitionSettingList.getAcquisitionSettingList(cId, null);
	}

	public List<StdOutItemDto> getOutItem(String cndSetCd) {
		String cId = AppContexts.user().companyId();
		return standardOutputItemRepository.getStdOutItemByCidAndSetCd(cId, cndSetCd).stream()
				.map(item -> StdOutItemDto.fromDomain(item)).collect(Collectors.toList());
	}

	public StdOutItemDto getByKey(String cndSetCd, String outItemCode) {
		String cId = AppContexts.user().companyId();
		Optional<StandardOutputItem> stdOutItemOpt = standardOutputItemRepository.getStdOutItemById(cId, outItemCode,
				cndSetCd);
		return stdOutItemOpt.map(StdOutItemDto::fromDomain).orElse(null);
	}

}
