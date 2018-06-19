package nts.uk.ctx.at.shared.dom.worktime.common.subholtransferset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.getcommonset.GetCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.CompensatoryOccurrenceDivision;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;

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
		
		// 共通設定の取得
		val workTimeCommonSetOpt = this.getCommonSet.get(companyId, workTimeCode);
		
		return this.getCommon(companyId, reverse, workTimeCommonSetOpt);
	}
	
	/** 休出・振替の処理順序を取得する */
	@Override
	public List<HolidayWorkAndTransferAtr> get(String companyId, String workTimeCode, boolean reverse,
			Map<String, WorkTimezoneCommonSet> workTimeCommonSetMap) {

		return this.getCommon(companyId, reverse,
				Optional.ofNullable(workTimeCommonSetMap.get(workTimeCode)));
	}
	
	/**
	 * 取得共通処理
	 * @param companyId 会社ID
	 * @param reverse 逆時系列にする
	 * @param workTimeCommonSetOpt 就業時間帯：共通設定
	 * @return 休出振替区分リスト　（処理順）
	 */
	private List<HolidayWorkAndTransferAtr> getCommon(String companyId, boolean reverse,
			Optional<WorkTimezoneCommonSet> workTimeCommonSetOpt) {
		
		List<HolidayWorkAndTransferAtr> results = new ArrayList<>();
		
		// 共通設定の取得
		if (!workTimeCommonSetOpt.isPresent()){
			results.add(HolidayWorkAndTransferAtr.HOLIDAY_WORK);
			return results;
		}
		
		// 代休振替設定を取得
		val subHolTimeSets = workTimeCommonSetOpt.get().getSubHolTimeSet();
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
					results.add(HolidayWorkAndTransferAtr.HOLIDAY_WORK);
					results.add(HolidayWorkAndTransferAtr.TRANSFER);
				}
				else {
					results.add(HolidayWorkAndTransferAtr.TRANSFER);
					results.add(HolidayWorkAndTransferAtr.HOLIDAY_WORK);
				}
				return results;
			}
			else {
				// 一定時間を超えたら代休とする時
				if (reverse){
					results.add(HolidayWorkAndTransferAtr.TRANSFER);
					results.add(HolidayWorkAndTransferAtr.HOLIDAY_WORK);
				}
				else {
					results.add(HolidayWorkAndTransferAtr.HOLIDAY_WORK);
					results.add(HolidayWorkAndTransferAtr.TRANSFER);
				}
				return results;
			}
		}
		results.add(HolidayWorkAndTransferAtr.HOLIDAY_WORK);
		return results;
	}
}
