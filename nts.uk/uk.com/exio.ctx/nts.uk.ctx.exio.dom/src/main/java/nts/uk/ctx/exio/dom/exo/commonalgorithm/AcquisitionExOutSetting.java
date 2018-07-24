package nts.uk.ctx.exio.dom.exo.commonalgorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemDataRepository;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.DataType;
import nts.uk.ctx.exio.dom.exo.condset.StandardAtr;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetRepository;
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
public class AcquisitionExOutSetting {

	@Inject
	private OutCndDetailItemRepository outCndDetailItemRepo;

	@Inject
	private SearchCodeListRepository searchCodeListRepo;

	@Inject
	private CtgItemDataRepository ctgItemDataRepo;

	@Inject
	private StdOutputCondSetRepository stdOutputCondSetRepo;

	@Inject
	private StandardOutputItemRepository stdOutItemRepo;

	@Inject
	private StandardOutputItemOrderRepository stdOutItemOrderRepo;

	// 外部出力取得設定一覧
	public List<StdOutputCondSet> getExOutSetting(String UserId, StandardAtr standardType, String conditionSetCd) {
		String cid = AppContexts.user().companyId();
		List<StdOutputCondSet> stdOutputCondSetList = new ArrayList<StdOutputCondSet>();

		if (standardType == StandardAtr.STANDARD) {
			if (StringUtils.isEmpty(conditionSetCd)) {
				stdOutputCondSetList.addAll(stdOutputCondSetRepo.getStdOutCondSetByCid(cid));
			} else {
				stdOutputCondSetRepo.getStdOutputCondSetById(cid, conditionSetCd)
						.ifPresent(item -> stdOutputCondSetList.add(item));
			}
		} else {
			// type user pending
		}

		return stdOutputCondSetList;
	}

	// 外部出力取得項目一覧
	public List<StandardOutputItem> getExOutItemList(String condSetCd, String userID, String outItemCd,
			StandardAtr standardType, boolean isAcquisitionMode) {
		String cid = AppContexts.user().companyId();
		List<StandardOutputItem> stdOutItemList = new ArrayList<StandardOutputItem>();
		List<StandardOutputItemOrder> stdOutItemOrder = new ArrayList<StandardOutputItemOrder>();

		if (standardType == StandardAtr.STANDARD) {
			if (StringUtils.isEmpty(outItemCd)) {
				stdOutItemList.addAll(stdOutItemRepo.getStdOutItemByCidAndSetCd(cid, condSetCd));
				stdOutItemOrder.addAll(stdOutItemOrderRepo.getStandardOutputItemOrderByCidAndSetCd(cid, condSetCd));
			} else {
				stdOutItemRepo.getStdOutItemById(cid, outItemCd, condSetCd).ifPresent(item -> stdOutItemRepo.add(item));
				stdOutItemOrderRepo.getStandardOutputItemOrderById(cid, outItemCd, condSetCd)
						.ifPresent(item -> stdOutItemOrder.add(item));
			}
		} else {
			// type user pending
		}

		// 出力項目(リスト)を出力項目並び順(リスト)に従って並び替える
		stdOutItemList.sort(new Comparator<StandardOutputItem>() {
			@Override
			public int compare(StandardOutputItem outputItem1, StandardOutputItem outputItem2) {
				List<StandardOutputItemOrder> order1 = stdOutItemOrder.stream()
						.filter(order -> order.getCid().equals(outputItem1.getCid())
								&& order.getConditionSettingCode().equals(outputItem1.getConditionSettingCode())
								&& order.getOutputItemCode().equals(outputItem1.getOutputItemCode()))
						.collect(Collectors.toList());

				List<StandardOutputItemOrder> order2 = stdOutItemOrder.stream()
						.filter(order -> order.getCid().equals(outputItem2.getCid())
								&& order.getConditionSettingCode().equals(outputItem2.getConditionSettingCode())
								&& order.getOutputItemCode().equals(outputItem2.getOutputItemCode()))
						.collect(Collectors.toList());

				if (order1.size() == 0) {
					if (order2.size() == 0)
						return 0;
					return -1;
				} else {
					if (order2.size() == 0)
						return 1;
					return order1.get(0).getDisplayOrder() > order1.get(0).getDisplayOrder() ? 1 : -1;
				}
			}
		});

		return stdOutItemList;
	}

	// 外部出力取得条件一覧
	public List<OutCndDetailItem> getExOutCond(String code, boolean forSQL) {
		List<OutCndDetailItem> outCndDetailItemList = outCndDetailItemRepo.getOutCndDetailItemByCode(code);
		List<SearchCodeList> searchCodeList;
		Optional<CtgItemData> ctgItemData;
		StringBuilder cond = new StringBuilder();

		for (OutCndDetailItem outCndDetailItem : outCndDetailItemList) {
			searchCodeList = searchCodeListRepo.getSearchCodeByCateIdAndCateNo(outCndDetailItem.getCategoryId(),
					outCndDetailItem.getCategoryItemNo().v());
			ctgItemData = ctgItemDataRepo.getCtgItemDataById(outCndDetailItem.getCategoryId(),
					outCndDetailItem.getCategoryItemNo().v());
			cond.setLength(0);

			for (SearchCodeList searchCodeItem : searchCodeList) {
				if (forSQL && ctgItemData.isPresent()
						&& ((ctgItemData.get().getDataType() == DataType.CHARACTER)
								|| (ctgItemData.get().getDataType() == DataType.DATE)
								|| (ctgItemData.get().getDataType() == DataType.TIME))) {
					cond.append("'");
					cond.append(searchCodeItem.getSearchCode());
					cond.append("'");
				} else {
					cond.append(searchCodeItem.getSearchCode());
				}

				cond.append(", ");
			}

			cond.setLength(cond.length() - 2);
			outCndDetailItem.setJoinedSearchCodeList(cond.toString());
		}

		return outCndDetailItemList;
	}
}
