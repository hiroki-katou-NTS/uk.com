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
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetail;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItem;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailRepository;
import nts.uk.ctx.exio.dom.exo.outcnddetail.SearchCodeList;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItem;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;
import nts.uk.ctx.exio.dom.exo.outputitemorder.StandardOutputItemOrder;
import nts.uk.ctx.exio.dom.exo.outputitemorder.StandardOutputItemOrderRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AcquisitionExOutSetting {
	
	@Inject
	private OutCndDetailRepository OutCndDetailRepo;

	@Inject
	private CtgItemDataRepository ctgItemDataRepo;

	@Inject
	private StdOutputCondSetRepository stdOutputCondSetRepo;

	@Inject
	private StandardOutputItemRepository stdOutItemRepo;

	@Inject
	private StandardOutputItemOrderRepository stdOutItemOrderRepo;

	// 外部出力取得設定一覧
	public List<StdOutputCondSet> getExOutSetting(String userId, StandardAtr standardType, String conditionSetCd) {
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
				List<StandardOutputItem> stdOutItemLists = stdOutItemRepo.getStdOutItemByCidAndSetCd(cid, condSetCd);
				stdOutItemList.addAll(stdOutItemLists);
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
						.filter(order -> order.getOutputItemCode().v().equals(outputItem1.getOutputItemCode().v()))
						.collect(Collectors.toList());

				List<StandardOutputItemOrder> order2 = stdOutItemOrder.stream()
						.filter(order -> order.getOutputItemCode().v().equals(outputItem2.getOutputItemCode().v()))
						.collect(Collectors.toList());

				if((order1.size() > 0) && (order2.size() > 0)) {
					return order1.get(0).getDisplayOrder() > order2.get(0).getDisplayOrder() ? 1 : -1;
				}
					
				return order1.size() - order2.size();
			}
		});

		return stdOutItemList;
	}

	/**
	 * 外部出力取得条件一覧
	 * @param conditionSettingCd
	 * @param userId
	 * @param standardType
	 * @param forSQL
	 * @param type
	 * @return
	 */
	public Optional<OutCndDetail> getExOutCond(String conditionSettingCd, String userId, StandardAtr standardType, boolean forSQL, String type) {
		String cid = AppContexts.user().companyId();
		Optional<OutCndDetail> cndDetailOtp = OutCndDetailRepo.getOutCndDetailById(cid, conditionSettingCd);
		
		if(!cndDetailOtp.isPresent()) {
			return cndDetailOtp;
		}
		
		OutCndDetail cndDetail = cndDetailOtp.get();		
		List<OutCndDetailItem> outCndDetailItemList = cndDetail.getListOutCndDetailItem();
		List<OutCndDetailItem> outCndDetailItemCustomList = new ArrayList<OutCndDetailItem>();
		List<SearchCodeList> searchCodeList;
		Optional<CtgItemData> ctgItemData;
		StringBuilder cond = new StringBuilder();

		for (OutCndDetailItem outCndDetailItem : outCndDetailItemList) {
			OutCndDetailItemCustom outCndDetailItemCustom = new OutCndDetailItemCustom(outCndDetailItem);
			searchCodeList = outCndDetailItem.getListSearchCodeList();
			ctgItemData = ctgItemDataRepo.getCtgItemDataById(outCndDetailItem.getCategoryId().v(),
					outCndDetailItem.getCategoryItemNo().v());
			
			outCndDetailItemCustom.setCtgItemData(ctgItemData);
			outCndDetailItemCustomList.add(outCndDetailItemCustom);
			cond.setLength(0);

			if(searchCodeList.isEmpty()) continue;
			
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
			outCndDetailItemCustom.setJoinedSearchCodeList(cond.toString());
		}
		
		cndDetailOtp.get().setListOutCndDetailItem(outCndDetailItemCustomList);

		return Optional.of(cndDetail);
	}
}
