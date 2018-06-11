package nts.uk.ctx.at.shared.dom.worktime.common.subholtransferset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.getcommonset.GetCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.CompensatoryOccurrenceDivision;
import nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTime;

/**
 * 実装：指定時間を取得
 * @author shuichu_ishida
 */
@Stateless
public class GetDesignatedTimeImpl implements GetDesignatedTime {

	/** 就業時間帯：共通設定の取得 */
	@Inject
	private GetCommonSet getCommonSet;
	/** 会社別代休時間設定 */
	@Inject
	private CompensLeaveComSetRepository compensLeaveComSetRepository;
	
	/** 代休振替設定を取得 */
	@Override
	public Optional<DesignatedTime> get(String companyId, String workTimeCode) {
		
		// 共通設定の取得
		val workTimezoneCommonSetOpt = this.getCommonSet.get(companyId, workTimeCode);
		if (!workTimezoneCommonSetOpt.isPresent()){
			return Optional.ofNullable(this.getCompanySet(companyId));
		}
		
		// 代休振替設定を取得
		val subHolTimeSets = workTimezoneCommonSetOpt.get().getSubHolTimeSet();
		for (val subHolTimeSet : subHolTimeSets){
			if (subHolTimeSet.getOriginAtr() != CompensatoryOccurrenceDivision.WorkDayOffTime) continue;
			val subHolTransferSet = subHolTimeSet.getSubHolTimeSet();
			
			// 代休振替設定．使用区分を取得する
			if (!subHolTransferSet.isUseDivision()) break;
			
			return Optional.ofNullable(subHolTransferSet.getDesignatedTime());
		}
		
		return Optional.ofNullable(this.getCompanySet(companyId));
	}
	
	/**
	 * 会社別代休時間設定から指定時間を取得する
	 * @param companyId 会社ID
	 * @return 指定時間
	 */
	private DesignatedTime getCompanySet(String companyId){
		
		val cmpLeaComSet = this.compensLeaveComSetRepository.find(companyId);
		if (cmpLeaComSet == null) return null;
		for (val cmpOccSet : cmpLeaComSet.getCompensatoryOccurrenceSetting()){
			
			// 使用区分を確認
			if (!cmpOccSet.getTransferSetting().isUseDivision()) continue;
			
			// 「休出から代休発生」以外は、対象外
			if (cmpOccSet.getOccurrenceType() !=
					nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceDivision.WorkDayOffTime){
				continue;
			}
			
			return cmpOccSet.getTransferSetting().getDesignatedTime();
		}
		return null;
	}
}
