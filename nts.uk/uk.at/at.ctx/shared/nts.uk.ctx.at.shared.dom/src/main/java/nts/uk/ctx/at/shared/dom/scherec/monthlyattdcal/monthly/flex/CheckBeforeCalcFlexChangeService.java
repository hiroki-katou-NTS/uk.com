package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flex;

import nts.arc.layer.app.cache.CacheCarrier;

public interface CheckBeforeCalcFlexChangeService {
	// 社員のフレックス繰越上限時間を求める
	ConditionCalcResult getConditionCalcFlex(String companyId, CalcFlexChangeDto calc);
	
	ConditionCalcResult getConditionCalcFlexRequire(CacheCarrier cacheCarrier, String companyId, CalcFlexChangeDto calc);
}
