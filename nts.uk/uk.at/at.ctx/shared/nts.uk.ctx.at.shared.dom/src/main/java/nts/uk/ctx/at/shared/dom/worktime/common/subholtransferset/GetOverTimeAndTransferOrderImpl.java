package nts.uk.ctx.at.shared.dom.worktime.common.subholtransferset;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.getcommonset.GetCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.CompensatoryOccurrenceDivision;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetAtr;

/**
 * 残業・振替の処理順序を取得する
 * @author shuichu_ishida
 */
@Stateless
public class GetOverTimeAndTransferOrderImpl implements GetOverTimeAndTransferOrder {

	/** 就業時間帯：共通設定の取得 */
	@Inject
	private GetCommonSet getCommonSet;

	/** 残業・振替の処理順序を取得する */
	@Override
	public List<OverTimeAndTransferAtr> get(String companyId, String workTimeCode, boolean reverse) {
		
		List<OverTimeAndTransferAtr> returnOrder = new ArrayList<>();
		
		// 共通設定の取得
		val workTimezoneCommonSetOpt = this.getCommonSet.get(companyId, workTimeCode);
		if (!workTimezoneCommonSetOpt.isPresent()){
			returnOrder.add(OverTimeAndTransferAtr.OVER_TIME);
			return returnOrder;
		}
		
		// 代休振替設定を取得する
		val subHolTimeSets = workTimezoneCommonSetOpt.get().getSubHolTimeSet();
		for (val subHolTimeSet : subHolTimeSets){
			if (subHolTimeSet.getOriginAtr() != CompensatoryOccurrenceDivision.FromOverTime) continue;
			val subHolTransferSet = subHolTimeSet.getSubHolTimeSet();
			
			// 代休振替設定．使用区分を取得する
			if (!subHolTransferSet.isUseDivision()) break;
			
			// 代休振替設定区分を取得する
			val transferSetAtr = subHolTransferSet.getSubHolTransferSetAtr();
			if (transferSetAtr == SubHolTransferSetAtr.SPECIFIED_TIME_SUB_HOL) {
				// 指定した時間を代休とする時
				if (reverse){
					returnOrder.add(OverTimeAndTransferAtr.OVER_TIME);
					returnOrder.add(OverTimeAndTransferAtr.TRANSFER);
				}
				else {
					returnOrder.add(OverTimeAndTransferAtr.TRANSFER);
					returnOrder.add(OverTimeAndTransferAtr.OVER_TIME);
				}
				return returnOrder;
			}
			else {
				// 一定時間を超えたら代休とする時
				if (reverse){
					returnOrder.add(OverTimeAndTransferAtr.TRANSFER);
					returnOrder.add(OverTimeAndTransferAtr.OVER_TIME);
				}
				else {
					returnOrder.add(OverTimeAndTransferAtr.OVER_TIME);
					returnOrder.add(OverTimeAndTransferAtr.TRANSFER);
				}
				return returnOrder;
			}
		}
		returnOrder.add(OverTimeAndTransferAtr.OVER_TIME);
		return returnOrder;
	}
}
