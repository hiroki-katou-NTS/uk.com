package nts.uk.ctx.exio.dom.exo.commonalgorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetRepository;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItem;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;
import nts.uk.ctx.exio.dom.exo.outputitemorder.StandardOutputItemOrder;
import nts.uk.ctx.exio.dom.exo.outputitemorder.StandardOutputItemOrderRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AcquisitionExOutSetting {
	
	@Inject
	private StdOutputCondSetRepository stdOutputCondSetRepo;
	
	@Inject
	private StandardOutputItemRepository stdOutItemRepo;

	@Inject
	private StandardOutputItemOrderRepository stdOutItemOrderRepo;
	
	// アルゴリズム「外部出力取得設定一覧」を実行する
	public List<StdOutputCondSet> getExOutSetting(String UserId, boolean isStandardType, String conditionSetCd) {
		String cid = AppContexts.user().companyId();
		List<StdOutputCondSet> stdOutputCondSetList = new ArrayList<StdOutputCondSet>();

		if(isStandardType) {
			if (conditionSetCd == null || conditionSetCd.equals("")) {
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
	
	// アルゴリズム「外部出力取得項目一覧」を実行する
	public List<StandardOutputItem> getExOutItemList(String condSetCd, String userID, String outItemCd, boolean isStandardType, boolean  isAcquisitionMode) {
		String cid = AppContexts.user().companyId();
		List<StandardOutputItem> stdOutItemList = new ArrayList<StandardOutputItem>();
		List<StandardOutputItemOrder> stdOutItemOrder = new ArrayList<StandardOutputItemOrder>();

		if(isStandardType) {
			if (outItemCd == null || outItemCd.equals("")) {
				stdOutItemList.addAll(stdOutItemRepo.getStdOutItemByCidAndSetCd(cid, condSetCd));
				stdOutItemOrder.addAll(stdOutItemOrderRepo.getStandardOutputItemOrderByCidAndSetCd(cid, condSetCd));
			} else {
				stdOutItemRepo.getStdOutItemById(cid, outItemCd, condSetCd).ifPresent(item -> stdOutItemRepo.add(item));
				stdOutItemOrderRepo.getStandardOutputItemOrderById(cid, outItemCd, condSetCd).ifPresent(item -> stdOutItemOrder.add(item));
			}
		} else {
			// type user pending
		}

		// 出力項目(リスト)を出力項目並び順(リスト)に従って並び替える
		stdOutItemList.sort(new Comparator<StandardOutputItem>() {
			@Override
			public int compare(StandardOutputItem outputItem1, StandardOutputItem outputItem2) {
				List<StandardOutputItemOrder> order1 = stdOutItemOrder.stream().filter(order -> 
					order.getCid().equals(outputItem1.getCid()) &&  order.getConditionSettingCode().equals(outputItem1.getConditionSettingCode()) 
							&& order.getOutputItemCode().equals(outputItem1.getOutputItemCode())
				).collect(Collectors.toList());
				
				List<StandardOutputItemOrder> order2 = stdOutItemOrder.stream().filter(order -> 
					order.getCid().equals(outputItem2.getCid()) &&  order.getConditionSettingCode().equals(outputItem2.getConditionSettingCode()) 
							&& order.getOutputItemCode().equals(outputItem2.getOutputItemCode())
				).collect(Collectors.toList());
				
				if(order1.size() == 0) {
					if(order2.size() == 0) return 0;
					return -1;
				} else {
					if(order2.size() == 0) return 1;
					return order1.get(0).getOrder() > order1.get(0).getOrder() ? 1 : -1;
				}
			}
		});
		
		if(isAcquisitionMode) {
			//for (StandardOutputItem stdOutItem : stdOutItemList) {
				//TODO phần này chờ QA
			//}
		}

		return stdOutItemList;
	}
}
