package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.export;

import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSetting;

/**
 * 実装：集計設定の取得（変形労働）
 * @author shuichu_ishida
 */
public class GetDeforAggrSet {
	
	/**
	 * 取得共通処理
	 * @param companyId 会社ID
	 * @param employmentCd 雇用コード
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @param usageUnitSet 労働時間と日数の設定の利用単位の設定
	 * @param shaIrgSetOpt 変形労働社員別月別実績集計設定
	 * @param comIrgSetOpt 変形労働会社別月別実績集計設定
	 * @return 変形労働の法定内集計設定
	 */
	public static Optional<DeforWorkTimeAggrSet> deforWorkTimeAggrSet(RequireM1 require, CacheCarrier cachecarrier, 
			String companyId, String employmentCd, String employeeId, GeneralDate criteriaDate,
			UsageUnitSetting usageUnitSet, Optional<ShaDeforLaborMonthActCalSet> shaIrgSetOpt,
			Optional<ComDeforLaborMonthActCalSet> comIrgSetOpt){

		// 社員別設定　確認
		if (usageUnitSet.isEmployee()){
			if (shaIrgSetOpt.isPresent()) return Optional.of(shaIrgSetOpt.get());
		}
		
		// 職場別設定　確認
		if (usageUnitSet.isWorkPlace()){
			
			// 所属職場を含む上位階層の職場IDを取得
			val workplaceIds = require.getCanUseWorkplaceForEmp(
					cachecarrier, companyId, employeeId, criteriaDate);
			
			for (val workplaceId : workplaceIds){
				val wkpSetOpt = require.monthDeforCalcSetByWorkplace(companyId, workplaceId);
				if (wkpSetOpt.isPresent()) return Optional.of(wkpSetOpt.get());
			}
		}
		
		// 雇用別設定　確認
		if (usageUnitSet.isEmployment()){
			val empSetOpt = require.monthDeforCalcSetByEmployment(companyId, employmentCd);
			if (empSetOpt.isPresent()) return Optional.of(empSetOpt.get());
		}
		
		// 会社別設定　確認
		if (comIrgSetOpt.isPresent()) return Optional.of(comIrgSetOpt.get());
		
		return Optional.empty();
	}
	
	public static interface RequireM1 {
		
		List<String> getCanUseWorkplaceForEmp(CacheCarrier cacheCarrier, String companyId, 
				String employeeId, GeneralDate baseDate);
		
		Optional<WkpDeforLaborMonthActCalSet> monthDeforCalcSetByWorkplace(String cid, String wkpId);

		Optional<EmpDeforLaborMonthActCalSet> monthDeforCalcSetByEmployment(String cid, String empCode);
	}
}
