package nts.uk.ctx.at.record.dom.statutoryworkinghours.monthly;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.statutoryworkinghours.DailyStatutoryLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WeekStart;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

/**
 * 実装：週開始を取得する
 * @author shuichu_ishida
 */
@Stateless
public class GetWeekStartImpl implements GetWeekStart {

	@Inject 
	private RecordDomRequireService requireService;
	
	/** 週開始を取得する */
	@Override
	public Optional<WeekStart> algorithm(String companyId, String employmentCd, String employeeId,
				GeneralDate baseDate, WorkingSystem workingSystem) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
	
		// 時間設定を取得
		val workTimeSetOpt = DailyStatutoryLaborTime.getWorkingTimeSetting(
				require, cacheCarrier,
				companyId, employmentCd, employeeId, baseDate, workingSystem);
		if (!workTimeSetOpt.isPresent()) return Optional.empty();
		
		// 「週開始」を返す
		if (workTimeSetOpt.get().getWeeklyTime() == null) return Optional.empty();
		return Optional.ofNullable(workTimeSetOpt.get().getWeeklyTime().getStart());
	}
}
