package nts.uk.ctx.exio.dom.exo.exoutsummaryservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemDataRepository;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetRepository;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItem;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItemRepository;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailRepository;
import nts.uk.ctx.exio.dom.exo.outcnddetail.SearchCodeList;
import nts.uk.ctx.exio.dom.exo.outcnddetail.SearchCodeListRepository;
import nts.uk.ctx.exio.dom.exo.outitemsortorder.StdOutItemOrder;
import nts.uk.ctx.exio.dom.exo.outitemsortorder.StdOutItemOrderRepository;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItem;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ExOutSummarySettingService {

	@Inject
	StdOutputCondSetRepository stdOutputCondSetRepo;

	@Inject
	OutCndDetailRepository outCndDetailRepo;

	@Inject
	OutCndDetailItemRepository outCndDetailItemRepo;

	@Inject
	StandardOutputItemRepository stdOutItemRepo;

	@Inject
	StdOutItemOrderRepository stdOutItemOrderRepo;

	@Inject
	SearchCodeListRepository searchCodeListRepo;

	@Inject
	CtgItemDataRepository ctgItemDataRepo;

	// アルゴリズム「外部出力サマリー設定」を実行する
	public int getExOutSummarySetting(String conditionSetCd) {
		return 1;
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
		List<StdOutItemOrder> stdOutItemOrder = new ArrayList<StdOutItemOrder>();

		if (outItemCd == null || outItemCd.equals("")) {
			stdOutItemList = stdOutItemRepo.getStdOutItemByCidAndSetCd(cid, condSetCd);
			stdOutItemOrder = stdOutItemOrderRepo.getStdOutItemOrderByCidAndSetCd(cid, condSetCd);
		} else {
			if (stdOutItemRepo.getStdOutItemById(cid, outItemCd, condSetCd).isPresent()) {
				stdOutItemList.add(stdOutItemRepo.getStdOutItemById(cid, outItemCd, condSetCd).get());
			}

			if (stdOutItemOrderRepo.getStdOutItemOrderById(cid, outItemCd, condSetCd).isPresent()) {
				stdOutItemOrder.add(stdOutItemOrderRepo.getStdOutItemOrderById(cid, outItemCd, condSetCd).get());
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
	private String getExOutCond(String code) {
		List<OutCndDetailItem> outCndDetailItemList = outCndDetailItemRepo.getOutCndDetailItemByCode(code);
		List<SearchCodeList> searchCodeList;
		Optional<CtgItemData> ctgItemData;
		StringBuilder cond = new StringBuilder("");

		for (int i = 0; i < outCndDetailItemList.size(); i++) {
			searchCodeList = searchCodeListRepo.getSearchCodeByCateIdAndCateNo(
					outCndDetailItemList.get(i).getCategoryId(), outCndDetailItemList.get(i).getCategoryItemNo().v());
			for (int j = 0; j < searchCodeList.size(); j++) {
				ctgItemData = ctgItemDataRepo.getCtgItemDataById(outCndDetailItemList.get(i).getCategoryId(),
						outCndDetailItemList.get(i).getCategoryItemNo().v());
				if ((i != 0) && (j != 0)) {
					cond.append(", ");
				}

				// TODO Chờ domain sửa thì sửa lại số thành enum
				if (ctgItemData.isPresent() && ((ctgItemData.get().getDataType() == 1)
						|| (ctgItemData.get().getDataType() == 2) || (ctgItemData.get().getDataType() == 3))) {
					cond.append("'");
				}

				cond.append(searchCodeList.get(j).getSearchCode());

				// TODO Chờ domain sửa thì sửa lại số thành enum
				if (ctgItemData.isPresent() && ((ctgItemData.get().getDataType() == 1)
						|| (ctgItemData.get().getDataType() == 2) || (ctgItemData.get().getDataType() == 3))) {
					cond.append("'");
				}
			}
		}

		return cond.toString();
	}
}
