package nts.uk.ctx.at.record.pubimp.remainnumber.annualleave;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.GetNextAnnLeaGrantDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.CalcNextAnnualLeaveGrantDate;

/**
 * 実装：次回年休付与年月日を取得する
 * @author shuichu_ishida
 */
@Stateless
public class GetNextAnnLeaGrantDateImpl implements GetNextAnnLeaGrantDate {

	@Inject 
	private RecordDomRequireService requireService;

	/** 次回年休付与年月日を取得する */
	@Override
	public Optional<GeneralDate> algorithm(String companyId, String employeeId) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
		
		// 次回年休付与を計算
		val nextAnnualLeaveGrantList = CalcNextAnnualLeaveGrantDate.algorithm(
				require, cacheCarrier, 
				companyId, employeeId, Optional.empty());
		
		// 次回年休付与．付与年月日を返す
		if (nextAnnualLeaveGrantList.size() == 0) return Optional.empty();
		return Optional.of(nextAnnualLeaveGrantList.get(0).getGrantDate());
	}
}
