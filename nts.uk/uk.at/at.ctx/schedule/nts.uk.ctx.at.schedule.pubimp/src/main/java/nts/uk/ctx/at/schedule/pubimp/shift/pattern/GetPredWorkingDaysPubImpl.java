package nts.uk.ctx.at.schedule.pubimp.shift.pattern;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.pattern.export.GetPredWorkingDays;
import nts.uk.ctx.at.schedule.dom.shift.pattern.export.GetPredWorkingDaysImpl;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingRepository;
import nts.uk.ctx.at.schedule.pub.shift.pattern.GetPredWorkindDaysPub;
import nts.uk.ctx.at.shared.app.cache.worktype.WorkTypeCache;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

/**
 * 実装：所定労働日数を取得する
 * @author shuichi_ishida
 */
@Stateless
public class GetPredWorkingDaysPubImpl implements GetPredWorkindDaysPub {

	/** 所定労働日数を取得する */
	@Inject
	private GetPredWorkingDays getPredWorkingDays;
	
	@Inject
	private WorkMonthlySettingRepository workMonthlySetRepo;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	/** 指定期間の所定労働日数を取得する(大塚用) */
	@Override
	public double byPeriod(DatePeriod period) {
		return this.getPredWorkingDays.byPeriod(period).v();
	}
	
	/** 指定期間の所定労働日数を取得する(大塚用) */
	@Override
	public double byPeriod(CacheCarrier cacheCarrier, DatePeriod period, Map<String, Object> workTypeMap) {
		Map<String, WorkType> _workTypeMap = new HashMap<>();
		for (Map.Entry<String, Object> worktype : workTypeMap.entrySet()) {
			_workTypeMap.put(worktype.getKey(), (WorkType)worktype.getValue());
		}
		val require = new RequireImpl(cacheCarrier);
		return this.getPredWorkingDays.byPeriodRequire(require, period, _workTypeMap).v();
	}
	
	@RequiredArgsConstructor
	class RequireImpl implements GetPredWorkingDaysImpl.Require{
		
		private final CacheCarrier cacheCarrier;

		@Override
		public List<WorkMonthlySetting> findByStartEndDate(String companyId, String monthlyPatternCode,
				GeneralDate startDate, GeneralDate endDate) {
//			WorkMonthlySettingCache cache = cacheCarrier.get( WorkMonthlySettingCache.DOMAIN_NAME);
//			return cache.get();
			return workMonthlySetRepo.findByStartEndDate(companyId, "001", startDate, endDate.addDays(1));
		}

		@Override
		public Optional<WorkType> findByPK(String companyId, String workTypeCd) {
//			WorkTypeCache cache = cacheCarrier.get( WorkTypeCache.DOMAIN_NAME);
//			return cache.get(workTypeCd);
			return workTypeRepository.findByPK(companyId, workTypeCd);
		}
	}
}
