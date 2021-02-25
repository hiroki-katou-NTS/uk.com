package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.export;

import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.com.ComFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.emp.EmpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.sha.ShaFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.wkp.WkpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSetting;

/**
 * 実装：集計設定の取得（フレックス）
 * @author shuichu_ishida
 */
public class GetFlexAggrSet {
	
	/**
	 * 取得共通処理
	 * @param companyId 会社ID
	 * @param employmentCd 雇用コード
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @param usageUnitSet 労働時間と日数の設定の利用単位の設定
	 * @param shaFlexSetOpt フレックス社員別月別実績集計設定
	 * @param comFlexSetOpt フレックス会社別月別実績集計設定
	 * @return フレックスの法定内集計設定
	 */
	public static Optional<FlexMonthWorkTimeAggrSet> flexWorkTimeAggrSet(RequireM1 require, CacheCarrier cacheCarrier,
			String companyId, String employmentCd, String employeeId, GeneralDate criteriaDate,
			UsageUnitSetting usageUnitSet, Optional<ShaFlexMonthActCalSet> shaFlexSetOpt,
			Optional<ComFlexMonthActCalSet> comFlexSetOpt){
		
		// 社員別設定　確認
		if (usageUnitSet.isEmployee()){
			if (shaFlexSetOpt.isPresent()) return Optional.of(shaFlexSetOpt.get());
		}
		
		// 職場別設定　確認
		if (usageUnitSet.isWorkPlace()){
			
			// 所属職場を含む上位階層の職場IDを取得
			val workplaceIds = require.getCanUseWorkplaceForEmp(
					cacheCarrier, companyId, employeeId, criteriaDate);
			
			for (val workplaceId : workplaceIds){
				val wkpSetOpt = require.monthFlexCalcSetByWorkplace(companyId, workplaceId);
				if (wkpSetOpt.isPresent()) return Optional.of(wkpSetOpt.get());
			}
		}
		
		// 雇用別設定　確認
		if (usageUnitSet.isEmployment()){
			val empSetOpt = require.monthFlexCalcSetByEmployment(companyId, employmentCd);
			if (empSetOpt.isPresent()) return Optional.of(empSetOpt.get());
		}
		
		// 会社別設定　確認
		if (comFlexSetOpt.isPresent()) return Optional.of(comFlexSetOpt.get());
		
		return Optional.empty();
	}
	
	public static interface RequireM1 {
		
		List<String> getCanUseWorkplaceForEmp(CacheCarrier cacheCarrier, String companyId, 
				String employeeId, GeneralDate baseDate);

		Optional<WkpFlexMonthActCalSet> monthFlexCalcSetByWorkplace(String cid, String wkpId);

		Optional<EmpFlexMonthActCalSet> monthFlexCalcSetByEmployment(String cid, String empCode);
	}
}
