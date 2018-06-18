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
		
		// 共通設定の取得
		val workTimeCommonSetOpt = this.getCommonSet.get(companyId, workTimeCode);
		
		return this.getCommon(companyId, reverse, workTimeCommonSetOpt);
	}
	
	/** 残業・振替の処理順序を取得する */
	@Override
	public List<OverTimeAndTransferAtr> get(String companyId, String workTimeCode, boolean reverse,
			Map<String, WorkTimezoneCommonSet> workTimeCommonSetMap) {

		return this.getCommon(companyId, reverse,
				Optional.ofNullable(workTimeCommonSetMap.get(workTimeCode)));
	}
	
	/**
	 * 取得共通処理
	 * @param companyId 会社ID
	 * @param reverse 逆時系列にする
	 * @param workTimeCommonSetOpt 就業時間帯：共通設定
	 * @return 残業振替区分リスト　（処理順）
	 */
	private List<OverTimeAndTransferAtr> getCommon(String companyId, boolean reverse,
			Optional<WorkTimezoneCommonSet> workTimeCommonSetOpt) {
		
		List<OverTimeAndTransferAtr> results = new ArrayList<>();
		
		// 共通設定の取得
		if (!workTimeCommonSetOpt.isPresent()){
			results.add(OverTimeAndTransferAtr.OVER_TIME);
			return results;
		}
		
		// 代休振替設定を取得する
		val subHolTimeSets = workTimeCommonSetOpt.get().getSubHolTimeSet();
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
					results.add(OverTimeAndTransferAtr.OVER_TIME);
					results.add(OverTimeAndTransferAtr.TRANSFER);
				}
				else {
					results.add(OverTimeAndTransferAtr.TRANSFER);
					results.add(OverTimeAndTransferAtr.OVER_TIME);
				}
				return results;
			}
			else {
				// 一定時間を超えたら代休とする時
				if (reverse){
					results.add(OverTimeAndTransferAtr.TRANSFER);
					results.add(OverTimeAndTransferAtr.OVER_TIME);
				}
				else {
					results.add(OverTimeAndTransferAtr.OVER_TIME);
					results.add(OverTimeAndTransferAtr.TRANSFER);
				}
				return results;
			}
		}
		results.add(OverTimeAndTransferAtr.OVER_TIME);
		return results;
	}
}
