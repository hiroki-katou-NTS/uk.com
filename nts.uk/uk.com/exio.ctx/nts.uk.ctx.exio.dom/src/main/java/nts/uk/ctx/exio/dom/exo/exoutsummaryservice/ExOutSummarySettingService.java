package nts.uk.ctx.exio.dom.exo.exoutsummaryservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.i18n.I18NText;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemDataRepository;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.DataType;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionExOutSetting;
import nts.uk.ctx.exio.dom.exo.condset.StandardAtr;
import nts.uk.ctx.exio.dom.exo.outcnddetail.ConditionSymbol;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItem;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItemRepository;
import nts.uk.ctx.exio.dom.exo.outcnddetail.SearchCodeList;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItem;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ExOutSummarySettingService {

	@Inject
	private OutCndDetailItemRepository outCndDetailItemRepo;

	@Inject
	private CtgItemDataRepository ctgItemDataRepo;

	@Inject
	private AcquisitionExOutSetting acquisitionExOutSetting;

	// 外部出力サマリー設定
	public ExOutSummarySetting getExOutSummarySetting(String conditionSetCd) {
		List<StandardOutputItem> stdOutItemList = acquisitionExOutSetting.getExOutItemList(conditionSetCd, null, "",
				StandardAtr.STANDARD, false);
		List<CtgItemDataCustom> ctgItemDataCustomList = getExOutCond(conditionSetCd);

		return new ExOutSummarySetting(stdOutItemList, ctgItemDataCustomList);
	}

	private List<CtgItemDataCustom> getExOutCond(String code) {
		String cid = AppContexts.user().companyId();
		List<OutCndDetailItem> outCndDetailItemList = outCndDetailItemRepo.getOutCndDetailItemByCidAndCode(cid, code);
		List<CtgItemDataCustom> ctgItemDataCustomList = new ArrayList<CtgItemDataCustom>();
		List<SearchCodeList> searchCodeList;
		Optional<CtgItemData> ctgItemData;
		StringBuilder cond = new StringBuilder();

		for (OutCndDetailItem outCndDetailItem : outCndDetailItemList) {
			searchCodeList = outCndDetailItem.getListSearchCodeList();
			ctgItemData = ctgItemDataRepo.getCtgItemDataById(outCndDetailItem.getCategoryId().v(),
					outCndDetailItem.getCategoryItemNo().v());
			cond.setLength(0);

			if (!ctgItemData.isPresent()) {
				continue;
			}

			// 数値型 NumericType
			if (ctgItemData.get().getDataType() == DataType.NUMERIC) {
				if (outCndDetailItem.getConditionSymbol() == ConditionSymbol.BETWEEN) {
					cond.append(outCndDetailItem.getSearchNumStartVal().isPresent()
							? outCndDetailItem.getSearchNumStartVal().get() : "");
					cond.append(I18NText.getText("CMF002_235"));
					cond.append(outCndDetailItem.getSearchNumEndVal().isPresent()
							? outCndDetailItem.getSearchNumEndVal().get() : "");
				} else if(outCndDetailItem.getSearchNum().isPresent()){
					cond.append(outCndDetailItem.getSearchNum().get().v());
					cond.append(outCndDetailItem.getConditionSymbol().nameId);
				}
			}
			// 文字型 CharacterType
			else if (ctgItemData.get().getDataType() == DataType.CHARACTER) {
				if (outCndDetailItem.getConditionSymbol() == ConditionSymbol.BETWEEN) {
					cond.append(outCndDetailItem.getSearchCharStartVal().isPresent()
							? outCndDetailItem.getSearchCharStartVal().get() : "");
					cond.append(I18NText.getText("CMF002_235"));
					cond.append(outCndDetailItem.getSearchCharEndVal().isPresent()
							? outCndDetailItem.getSearchCharEndVal().get() : "");
				} else if(outCndDetailItem.getSearchChar().isPresent()) {
					cond.append(outCndDetailItem.getSearchChar().get().v());
					cond.append(outCndDetailItem.getConditionSymbol().nameId);
				}
			}
			// 日付型 DateType
			else if (ctgItemData.get().getDataType() == DataType.DATE) {
				if (outCndDetailItem.getConditionSymbol() == ConditionSymbol.BETWEEN) {
					cond.append(outCndDetailItem.getSearchDateStart().isPresent()
							? outCndDetailItem.getSearchDateStart().get() : "");
					cond.append(I18NText.getText("CMF002_235"));
					cond.append(outCndDetailItem.getSearchDateEnd().isPresent()
							? outCndDetailItem.getSearchDateEnd().get() : "");
				} else if(outCndDetailItem.getSearchDate().isPresent()) {
					cond.append(outCndDetailItem.getSearchDate().get());
					cond.append(outCndDetailItem.getConditionSymbol().nameId);
				}
			}
			// 時間型 TimeType
			else if (ctgItemData.get().getDataType() == DataType.TIME) {
				if (outCndDetailItem.getConditionSymbol() == ConditionSymbol.BETWEEN) {
					cond.append(outCndDetailItem.getSearchTimeStartVal().isPresent()
							? outCndDetailItem.getSearchTimeStartVal().get() : "");
					cond.append(I18NText.getText("CMF002_235"));
					cond.append(outCndDetailItem.getSearchTimeEndVal().isPresent()
							? outCndDetailItem.getSearchTimeEndVal().get() : "");
				} else if(outCndDetailItem.getSearchTime().isPresent()) {
					cond.append(outCndDetailItem.getSearchTime().get());
					cond.append(outCndDetailItem.getConditionSymbol().nameId);
				}
			}
			// 時刻型 TimeClockType
			else if (ctgItemData.get().getDataType() == DataType.INS_TIME) {
				if (outCndDetailItem.getConditionSymbol() == ConditionSymbol.BETWEEN) {
					cond.append(outCndDetailItem.getSearchClockStartVal().isPresent()
							? outCndDetailItem.getSearchClockStartVal().get() : "");
					cond.append(I18NText.getText("CMF002_235"));
					cond.append(outCndDetailItem.getSearchClockEndVal().isPresent()
							? outCndDetailItem.getSearchClockEndVal().get() : "");
				} else if(outCndDetailItem.getSearchClock().isPresent()) {
					cond.append(outCndDetailItem.getSearchClock().get());
					cond.append(outCndDetailItem.getConditionSymbol().nameId);
				}
			}

			if (ctgItemData.get().getSearchValueCd().isPresent()
					&& !ctgItemData.get().getSearchValueCd().get().isEmpty()) {
				cond.setLength(0);
				cond.append(String.join(", ",
						searchCodeList.stream().map(item -> item.getSearchCode().v()).collect(Collectors.toList())));
				cond.append(outCndDetailItem.getConditionSymbol().nameId);
			}

			ctgItemDataCustomList.add(new CtgItemDataCustom(outCndDetailItem.getSeriNum(), ctgItemData.get().getItemName().v(), cond.toString()));
		}

		return ctgItemDataCustomList;
	}
}
