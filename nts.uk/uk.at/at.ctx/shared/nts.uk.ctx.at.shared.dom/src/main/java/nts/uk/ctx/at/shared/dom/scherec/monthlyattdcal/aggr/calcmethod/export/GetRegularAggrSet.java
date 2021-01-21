package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.export;

import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.RegularWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSetting;

/**
 * 実装：集計設定の取得（通常勤務）
 * @author shuichu_ishida
 */
public class GetRegularAggrSet {
	
	/**
	 * 取得共通処理
	 * @param companyId 会社ID
	 * @param employmentCd 雇用コード
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @param usageUnitSet 労働時間と日数の設定の利用単位の設定
	 * @param shaRegSetOpt 通常勤務社員別月別実績集計設定
	 * @param comRegSetOpt 通常勤務会社別月別実績集計設定
	 * @return 通常勤務の法定内集計設定
	 */
	public static Optional<RegularWorkTimeAggrSet> regularWorkTimeAggrSet(RequireM1 require, CacheCarrier cacheCarrier,
			String companyId, String employmentCd, String employeeId, GeneralDate criteriaDate,
			UsageUnitSetting usageUnitSet, Optional<ShaRegulaMonthActCalSet> shaRegSetOpt,
			Optional<ComRegulaMonthActCalSet> comRegSetOpt){
		
		// 社員別設定　確認
		if (usageUnitSet.isEmployee()){
			if (shaRegSetOpt.isPresent()) return Optional.of(shaRegSetOpt.get());
		}
		
		// 職場別設定　確認
		if (usageUnitSet.isWorkPlace()){
			
			// 所属職場を含む上位階層の職場IDを取得
			
			val workplaceIds = require.getCanUseWorkplaceForEmp(cacheCarrier, companyId, employeeId, criteriaDate);
			
			for (val workplaceId : workplaceIds){
				val wkpSetOpt = require.monthRegularCalcSetByWorkplace(companyId, workplaceId);
				
				if (wkpSetOpt.isPresent()) return Optional.of(wkpSetOpt.get());
			}
		}
		
		// 雇用別設定　確認
		if (usageUnitSet.isEmployment()){
			val empSetOpt = require.monthRegularCalcSetByEmployment(companyId, employmentCd);
			
			if (empSetOpt.isPresent()) return Optional.of(empSetOpt.get());
		}
		
		// 会社別設定　確認
		if (comRegSetOpt.isPresent()) return Optional.of(comRegSetOpt.get());
		
		return Optional.empty();
	}
	
	public static interface RequireM1 {
		
		List<String> getCanUseWorkplaceForEmp(CacheCarrier cacheCarrier, String companyId, 
				String employeeId, GeneralDate baseDate);
		
		Optional<WkpRegulaMonthActCalSet> monthRegularCalcSetByWorkplace(String cid, String wkpId);

		Optional<EmpRegulaMonthActCalSet> monthRegularCalcSetByEmployment(String cid, String empCode);
	}
}
