package nts.uk.ctx.at.record.pubimp.remainnumber.annualleave;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.AnnLeaGrantNumberExport;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.GetAnnLeaGrantNumOfCurrentMon;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.CalcNextAnnualLeaveGrantDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantDays;

/**
 * 実装：社員の当月の年休付与数を取得する
 * @author shuichu_ishida
 */
@Stateless
public class GetAnnLeaGrantNumOfCurrentMonImpl implements GetAnnLeaGrantNumOfCurrentMon {
	@Inject 
	private RecordDomRequireService requireService;
	
	/** 社員の当月の年休付与数を取得する */
	@Override
	public AnnLeaGrantNumberExport algorithm(String employeeId) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
		
		AnnLeaGrantNumberExport result = new AnnLeaGrantNumberExport(new GrantDays(0.0));
		
		//　社員に対応する締め開始日を取得する
		val closureStartOpt = GetClosureStartForEmployee.algorithm(require, cacheCarrier, employeeId);
		if (!closureStartOpt.isPresent()) return result;
		val closureStart = closureStartOpt.get();
		
		// 社員に対応する処理締めを取得する
		val closure = ClosureService.getClosureDataByEmployee(require, cacheCarrier, employeeId, closureStart);
		if (closure == null) return result;

		// 当月の年月を取得する
		val currentMonth = closure.getClosureMonth().getProcessingYm();
		
		// 当月の期間を算出する　→　締め期間
		val closurePeriod = ClosureService.getClosurePeriod(require, closure.getClosureId().value, currentMonth);
	
		// 次回年休付与を計算
		val nextAnnualLeaveGrantList = CalcNextAnnualLeaveGrantDate.algorithm(
				require, cacheCarrier,
				closure.getCompanyId().v(), employeeId, Optional.of(closurePeriod));
		
		// 全ての「次回年休付与．付与日数」を合計する
		double grantDays = 0.0;
		for (val nextAnnualLeaveGrant : nextAnnualLeaveGrantList){
			grantDays += nextAnnualLeaveGrant.getGrantDays().v().doubleValue();
		}
		
		// 合計した「付与日数」を返す
		return new AnnLeaGrantNumberExport(new GrantDays(grantDays));
	}
}
