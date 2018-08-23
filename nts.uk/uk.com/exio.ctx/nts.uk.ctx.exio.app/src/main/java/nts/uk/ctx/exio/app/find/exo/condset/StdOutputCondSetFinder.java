package nts.uk.ctx.exio.app.find.exo.condset;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.exio.app.find.exo.category.ExOutCtgDto;
import nts.uk.ctx.exio.app.find.exo.item.StdOutItemDto;
import nts.uk.ctx.exio.dom.exo.category.ExOutCtg;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemDataRepository;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionSettingList;
import nts.uk.ctx.exio.dom.exo.condset.StandardAtr;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetRepository;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetService;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItem;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;
import nts.uk.ctx.exio.dom.exo.outputitemorder.StandardOutputItemOrder;
import nts.uk.ctx.exio.dom.exo.outputitemorder.StandardOutputItemOrderRepository;
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
	private StdOutputCondSetService stdOutputCondSetService;

	@Inject
	private StandardOutputItemRepository standardOutputItemRepository;
	
	@Inject
    private StandardOutputItemOrderRepository standardOutputItemOrderRepository;

	@Inject
	private CtgItemDataRepository ctgItemDataRepository;

	@Inject
	private StdOutputCondSetService mStdOutputCondSetService;

	public List<StdOutputCondSetDto> getAllStdOutputCondSet() {
		return finder.getAllStdOutputCondSet().stream().map(item -> StdOutputCondSetDto.fromDomain(item))
				.collect(Collectors.toList());
	}

	public List<CondSetDto> getCndSet() {
		String cId = AppContexts.user().companyId();
		String userId = AppContexts.user().userId();
		return acquisitionSettingList.getAcquisitionSettingList(cId, userId, StandardAtr.STANDARD, Optional.empty())
				.stream().map(item -> CondSetDto.fromDomain(item)).collect(Collectors.toList());
	}

	public StdOutItemDto getByKey(String cndSetCd, String outItemCode) {
		String cId = AppContexts.user().companyId();
		Optional<StandardOutputItem> stdOutItemOpt = standardOutputItemRepository.getStdOutItemById(cId, outItemCode,
				cndSetCd);
		if (!stdOutItemOpt.isPresent()) {
			return null;
		}
		return StdOutItemDto.fromDomain(stdOutItemOpt.get(), ctgItemDataRepository.getAllCtgItemData());
	}

	public List<StdOutputCondSetDto> getConditionSetting(String modeScreen, String cndSetCd) {
		return mStdOutputCondSetService.getListStandardOutputItem(cndSetCd).stream()
				.map(item -> StdOutputCondSetDto.fromDomain(item)).collect(Collectors.toList());
	}

	public ExOutCtgDto getExOutCtgDto(int cateloryId) {
		Optional<ExOutCtg> exOutCtg = mStdOutputCondSetService.getExOutCtg(cateloryId);
		if (!exOutCtg.isPresent()) {
			return null;
		}
		return ExOutCtgDto.fromDomain(exOutCtg.get());
	}
	
	public List<StdOutItemDto> getOutItem(String condSetCd ,int standType) {
        String userId = AppContexts.user().userId();
        String cid = AppContexts.user().companyId();
        List<StandardOutputItemOrder> standardOutputItemOrder = standardOutputItemOrderRepository.getStandardOutputItemOrderByCidAndSetCd(cid, condSetCd);
        String outItemCd = new String ();
        StandardAtr standardAtr =  EnumAdaptor.valueOf(standType, StandardAtr.class);
        return stdOutputCondSetService.outputAcquisitionItemList(condSetCd, userId, outItemCd, standardAtr, false).stream()
                .map(item -> StdOutItemDto.fromDomain(standardOutputItemOrder, item))
                .collect(Collectors.toList());
    }

}
