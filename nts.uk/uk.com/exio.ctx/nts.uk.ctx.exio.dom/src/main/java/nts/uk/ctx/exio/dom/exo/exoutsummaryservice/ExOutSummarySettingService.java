package nts.uk.ctx.exio.dom.exo.exoutsummaryservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.i18n.I18NText;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemDataRepository;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetRepository;
import nts.uk.ctx.exio.dom.exo.outcnddetail.ConditionSymbol;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItem;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItemRepository;
import nts.uk.ctx.exio.dom.exo.outcnddetail.SearchCodeList;
import nts.uk.ctx.exio.dom.exo.outcnddetail.SearchCodeListRepository;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItem;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;
import nts.uk.ctx.exio.dom.exo.outputitemorder.StandardOutputItemOrder;
import nts.uk.ctx.exio.dom.exo.outputitemorder.StandardOutputItemOrderRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ExOutSummarySettingService {

	@Inject
	private StdOutputCondSetRepository stdOutputCondSetRepo;

	@Inject
	private OutCndDetailItemRepository outCndDetailItemRepo;

	@Inject
	private StandardOutputItemRepository stdOutItemRepo;

	@Inject
	private StandardOutputItemOrderRepository stdOutItemOrderRepo;

	@Inject
	private SearchCodeListRepository searchCodeListRepo;

	@Inject
	private CtgItemDataRepository ctgItemDataRepo;

	// アルゴリズム「外部出力サマリー設定」を実行する
	public ExOutSummarySetting getExOutSummarySetting(String conditionSetCd) {
		List<StandardOutputItem> stdOutItemList = getExOutItemList("", conditionSetCd);
		List<CtgItemDataCustom> ctgItemDataCustomList = getExOutCond(conditionSetCd);
		
		return new ExOutSummarySetting(stdOutItemList, ctgItemDataCustomList);
	}

	// アルゴリズム「外部出力取得設定一覧」を実行する with type = fixed form (standard)
	private List<StdOutputCondSet> getExOutSetting(String conditionSetCd) {
		String cid = AppContexts.user().companyId();
		List<StdOutputCondSet> stdOutputCondSetList = new ArrayList<StdOutputCondSet>();

		if (conditionSetCd == null || conditionSetCd.equals("")) {
			stdOutputCondSetList = stdOutputCondSetRepo.getStdOutCondSetByCid(cid);
		} else {
			Optional<StdOutputCondSet> stdOutputCondSet = stdOutputCondSetRepo.getStdOutputCondSetById(cid,
					conditionSetCd);
			if (stdOutputCondSet.isPresent()) {
				stdOutputCondSetList.add(stdOutputCondSet.get());
			}
		}

		return stdOutputCondSetList;
	}

	// アルゴリズム「外部出力取得項目一覧」を実行する with type = fixed form (standard)
	private List<StandardOutputItem> getExOutItemList(String outItemCd, String condSetCd) {
		String cid = AppContexts.user().companyId();
		List<StandardOutputItem> stdOutItemList = new ArrayList<StandardOutputItem>();
		List<StandardOutputItemOrder> stdOutItemOrder = new ArrayList<StandardOutputItemOrder>();

		if (outItemCd == null || outItemCd.equals("")) {
			stdOutItemList = stdOutItemRepo.getStdOutItemByCidAndSetCd(cid, condSetCd);
			stdOutItemOrder = stdOutItemOrderRepo.getStandardOutputItemOrderByCidAndSetCd(cid, condSetCd);
		} else {
			if (stdOutItemRepo.getStdOutItemById(cid, outItemCd, condSetCd).isPresent()) {
				stdOutItemList.add(stdOutItemRepo.getStdOutItemById(cid, outItemCd, condSetCd).get());
			}

			if (stdOutItemOrderRepo.getStandardOutputItemOrderById(cid, outItemCd, condSetCd).isPresent()) {
				stdOutItemOrder.add(stdOutItemOrderRepo.getStandardOutputItemOrderById(cid, outItemCd, condSetCd).get());
			}
		}

		for (StandardOutputItem stdOutItem : stdOutItemList) {
			if (stdOutItem.getItemType() != null) {

			}
		}

		// TODO: Chờ QA
		return stdOutItemList;
	}

	// アルゴリズム「外部出力取得条件一覧」を実行する with type = fixed form (standard)
	private List<CtgItemDataCustom> getExOutCond(String code) {
		List<OutCndDetailItem> outCndDetailItemList = outCndDetailItemRepo.getOutCndDetailItemByCode(code);
		List<CtgItemDataCustom> ctgItemDataCustomList = new ArrayList<CtgItemDataCustom>();
		List<SearchCodeList> searchCodeList;
		Optional<CtgItemData> ctgItemData;
		StringBuilder cond = new StringBuilder();

		for (OutCndDetailItem outCndDetailItem : outCndDetailItemList) {
			searchCodeList = searchCodeListRepo.getSearchCodeByCateIdAndCateNo(
					outCndDetailItem.getCategoryId(), outCndDetailItem.getCategoryItemNo().v());
			ctgItemData = ctgItemDataRepo.getCtgItemDataById(outCndDetailItem.getCategoryId(),
					outCndDetailItem.getCategoryItemNo().v());
			cond.setLength(0);
			
			if(ctgItemData.isPresent()) {
				continue;
			}
			
			// 数値型 NumericType
			// TODO Chờ domain sửa thì sửa lại số thành enum
			if(ctgItemData.get().getDataType() == 0) {
				if(outCndDetailItem.getConditionSymbol() == ConditionSymbol.BETWEEN) {
					cond.append(outCndDetailItem.getSearchNumStartVal().isPresent() ? outCndDetailItem.getSearchNumStartVal().get() : "");
					cond.append(I18NText.getText("#CMF002_235"));
					cond.append(outCndDetailItem.getSearchNumEndVal().isPresent() ? outCndDetailItem.getSearchNumEndVal().get() : "");
				} else {
					cond.append(outCndDetailItem.getSearchNum().isPresent() ? outCndDetailItem.getSearchNum().get().v() : "");
					cond.append(outCndDetailItem.getConditionSymbol());
				}
			}
			//文字型 CharacterType
			else if(ctgItemData.get().getDataType() == 1) {
				if(outCndDetailItem.getConditionSymbol() == ConditionSymbol.BETWEEN) {
					cond.append(outCndDetailItem.getSearchCharStartVal().isPresent() ? outCndDetailItem.getSearchCharStartVal().get() : "");
					cond.append(I18NText.getText("#CMF002_235"));
					cond.append(outCndDetailItem.getSearchCharEndVal().isPresent() ? outCndDetailItem.getSearchCharEndVal().get() : "");
				} else {
					cond.append(outCndDetailItem.getSearchChar().isPresent() ? outCndDetailItem.getSearchChar().get().v() : "");
					cond.append(outCndDetailItem.getConditionSymbol());
				}
			}
			//日付型 DateType
			else if(ctgItemData.get().getDataType() == 2) {
				if(outCndDetailItem.getConditionSymbol() == ConditionSymbol.BETWEEN) {
					cond.append(outCndDetailItem.getSearchDateStart().isPresent() ? outCndDetailItem.getSearchDateStart().get() : "");
					cond.append(I18NText.getText("#CMF002_235"));
					cond.append(outCndDetailItem.getSearchDateEnd().isPresent() ? outCndDetailItem.getSearchDateEnd().get() : "");
				} else {
					cond.append(outCndDetailItem.getSearchDate().isPresent() ? outCndDetailItem.getSearchDate().get() : "");
					cond.append(outCndDetailItem.getConditionSymbol());
				}
			}
			//時間型 TimeType
			else if(ctgItemData.get().getDataType() == 3) {
				if(outCndDetailItem.getConditionSymbol() == ConditionSymbol.BETWEEN) {
					cond.append(outCndDetailItem.getSearchTimeStartVal().isPresent() ? outCndDetailItem.getSearchTimeStartVal().get() : "");
					cond.append(I18NText.getText("#CMF002_235"));
					cond.append(outCndDetailItem.getSearchTimeEndVal().isPresent() ? outCndDetailItem.getSearchTimeEndVal().get() : "");
				} else {
					cond.append(outCndDetailItem.getSearchTime().isPresent() ? outCndDetailItem.getSearchTime().get() : "");
					cond.append(outCndDetailItem.getConditionSymbol());
				}
			}
			//時刻型 TimeClockType
			else if(ctgItemData.get().getDataType() == 4) {
				if(outCndDetailItem.getConditionSymbol() == ConditionSymbol.BETWEEN) {
					cond.append(outCndDetailItem.getSearchClockStartVal().isPresent() ? outCndDetailItem.getSearchClockStartVal().get() : "");
					cond.append(I18NText.getText("#CMF002_235"));
					cond.append(outCndDetailItem.getSearchClockEndVal().isPresent() ? outCndDetailItem.getSearchClockEndVal().get() : "");
				} else {
					cond.append(outCndDetailItem.getSearchClock().isPresent() ? outCndDetailItem.getSearchClock().get() : "");
					cond.append(outCndDetailItem.getConditionSymbol());
				}
			}
			
			if("with".equals(ctgItemData.get().getSearchValueCd().toLowerCase())) {
				for (SearchCodeList searchCodeItem: searchCodeList) {
					cond.append(", ");
	
					// TODO Chờ domain sửa thì sửa lại số thành enum
					if ((ctgItemData.get().getDataType() == 1) || (ctgItemData.get().getDataType() == 2) 
							|| (ctgItemData.get().getDataType() == 3)) {
						cond.append("'");
						cond.append(searchCodeItem.getSearchCode());
						cond.append("'");
					} else {
						cond.append(searchCodeItem.getSearchCode());
					}
				}
			}
			
			ctgItemDataCustomList.add(new CtgItemDataCustom(ctgItemData.get().getItemName(), cond.toString()));
		}

		return ctgItemDataCustomList;
	}
}
