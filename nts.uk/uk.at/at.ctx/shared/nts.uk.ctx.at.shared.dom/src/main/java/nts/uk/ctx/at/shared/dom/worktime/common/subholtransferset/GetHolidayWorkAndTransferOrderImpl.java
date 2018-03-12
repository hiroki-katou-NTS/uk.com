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
 * 休出・振替の処理順序を取得する
 * @author shuichu_ishida
 */
@Stateless
public class GetHolidayWorkAndTransferOrderImpl implements GetHolidayWorkAndTransferOrder {

	/** 就業時間帯：共通設定の取得 */
	@Inject
	private GetCommonSet getCommonSet;
	
	/** 休出・振替の処理順序を取得する */
	@Override
	public List<HolidayWorkAndTransferAtr> get(String companyId, String workTimeCode, boolean reverse) {
		
		List<HolidayWorkAndTransferAtr> returnOrder = new ArrayList<>();
		
		// 共通設定の取得
		val workTimezoneCommonSetOpt = this.getCommonSet.get(companyId, workTimeCode);
		if (!workTimezoneCommonSetOpt.isPresent()){
			returnOrder.add(HolidayWorkAndTransferAtr.HOLIDAY_WORK);
			return returnOrder;
		}
		
		// 代休振替設定を取得
		val subHolTimeSets = workTimezoneCommonSetOpt.get().getSubHolTimeSet();
		for (val subHolTimeSet : subHolTimeSets){
			if (subHolTimeSet.getOriginAtr() != CompensatoryOccurrenceDivision.WorkDayOffTime) continue;
			val subHolTransferSet = subHolTimeSet.getSubHolTimeSet();
			
			// 代休振替設定．使用区分を取得する
			if (!subHolTransferSet.isUseDivision()) break;
			
			// 代休振替設定区分を取得する
			val transferSetAtr = subHolTransferSet.getSubHolTransferSetAtr();
			if (transferSetAtr == SubHolTransferSetAtr.SPECIFIED_TIME_SUB_HOL) {
				// 指定した時間を代休とする時
				if (reverse){
					returnOrder.add(HolidayWorkAndTransferAtr.HOLIDAY_WORK);
					returnOrder.add(HolidayWorkAndTransferAtr.TRANSFER);
				}
				else {
					returnOrder.add(HolidayWorkAndTransferAtr.TRANSFER);
					returnOrder.add(HolidayWorkAndTransferAtr.HOLIDAY_WORK);
				}
				return returnOrder;
			}
			else {
				// 一定時間を超えたら代休とする時
				if (reverse){
					returnOrder.add(HolidayWorkAndTransferAtr.TRANSFER);
					returnOrder.add(HolidayWorkAndTransferAtr.HOLIDAY_WORK);
				}
				else {
					returnOrder.add(HolidayWorkAndTransferAtr.HOLIDAY_WORK);
					returnOrder.add(HolidayWorkAndTransferAtr.TRANSFER);
				}
				return returnOrder;
			}
		}
		returnOrder.add(HolidayWorkAndTransferAtr.HOLIDAY_WORK);
		return returnOrder;
	}
}
