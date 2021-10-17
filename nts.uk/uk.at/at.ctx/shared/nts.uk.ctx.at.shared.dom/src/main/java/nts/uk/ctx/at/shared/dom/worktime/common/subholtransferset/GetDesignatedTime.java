package nts.uk.ctx.at.shared.dom.worktime.common.subholtransferset;

import java.util.Optional;

import lombok.val;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.getcommonset.GetCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.CompensatoryOccurrenceDivision;
//import nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTime;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSet;

/**
 * 指定時間を取得
 * @author shuichu_ishida
 */
public class GetDesignatedTime {

	/**
	 * 指定時間を取得
	 * @param companyId 会社ID
	 * @param workTimeCode 就業時間帯コード
	 * @return 指定時間設定
	 */
	public static Optional<SubHolTransferSet> get(RequireM2 require, String companyId, String workTimeCode) {
			
		// 共通設定の取得
		val workTimezoneCommonSetOpt = GetCommonSet.workTimezoneCommonSet(require, companyId, workTimeCode);
		if (!workTimezoneCommonSetOpt.isPresent()){
			return Optional.ofNullable(getCompanySet(require, companyId));
		}
		
		// 代休振替設定を取得
		val subHolTimeSets = workTimezoneCommonSetOpt.get().getSubHolTimeSet();
		for (val subHolTimeSet : subHolTimeSets){
			if (subHolTimeSet.getOriginAtr() != CompensatoryOccurrenceDivision.WorkDayOffTime) continue;
			val subHolTransferSet = subHolTimeSet.getSubHolTimeSet();
			
			// 代休振替設定．使用区分を取得する
			if (!subHolTransferSet.isUseDivision()) break;
			
			return Optional.ofNullable(subHolTransferSet);
		}
		
		return Optional.ofNullable(getCompanySet(require, companyId));
	}
	
	/**
	 * 会社別代休時間設定から指定時間を取得する
	 * @param companyId 会社ID
	 * @return 指定時間
	 */
	private static SubHolTransferSet getCompanySet(RequireM1 requirey, String companyId){
		
		val cmpLeaComSet = requirey.compensatoryLeaveComSetting(companyId).get();
		if (cmpLeaComSet == null) return null;
		for (val cmpOccSet : cmpLeaComSet.getCompensatoryOccurrenceSetting()){
			
			// 使用区分を確認
			if (!cmpOccSet.getTransferSetting().isUseDivision()) continue;
			
			// 「休出から代休発生」以外は、対象外
			if (cmpOccSet.getOccurrenceType() !=
					nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceDivision.WorkDayOffTime){
				continue;
			}
			
			return cmpOccSet.getTransferSetting();
		}
		return null;
	}

	public static interface RequireM2 extends GetCommonSet.RequireM3, RequireM1 {}
	
	public static interface RequireM1 extends CompensatoryLeaveComSetting.Require {}
}
