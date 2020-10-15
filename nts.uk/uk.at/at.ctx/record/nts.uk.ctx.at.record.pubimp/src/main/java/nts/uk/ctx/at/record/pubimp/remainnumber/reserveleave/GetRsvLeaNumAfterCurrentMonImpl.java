package nts.uk.ctx.at.record.pubimp.remainnumber.reserveleave;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.gul.util.value.MutableValue;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.ClosurePeriod;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.GetClosurePeriod;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.pub.remainnumber.reserveleave.GetRsvLeaNumAfterCurrentMon;
import nts.uk.ctx.at.record.pub.remainnumber.reserveleave.RsvLeaUsedCurrentMonExport;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.GetAnnAndRsvRemNumWithinPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.export.param.AggrResultOfAnnualLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.export.param.AggrResultOfReserveLeave;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagementRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;

/**
 * 実装：当月以降の積立年休使用数・残数を取得する
 * @author shuichi_ishida
 */
@Stateless
public class GetRsvLeaNumAfterCurrentMonImpl implements GetRsvLeaNumAfterCurrentMon {

	@Inject
	private ManagedParallelWithContext parallel;
	@Inject
	private ClosureStatusManagementRepository clsSttMngRepo;
	@Inject
	private RecordDomRequireService requireService;
	
	/** 当月以降の積立年休使用数・残数を取得する */
	@Override
	public List<RsvLeaUsedCurrentMonExport> algorithm(String employeeId, YearMonthPeriod period) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
		// 社員に対応する処理締めを取得する
		val closure = ClosureService.getClosureDataByEmployee(require, cacheCarrier, employeeId, GeneralDate.today());
		if (closure == null) return new ArrayList<>();
		
		// 指定した年月の期間をすべて取得する
		val endYMPeriods = closure.getPeriodByYearMonth(period.end());
		List<ClosurePeriod> aggrTmp = Collections.synchronizedList(new ArrayList<>());
		parallel.forEach(endYMPeriods, endYMPeriod -> {
			// 集計期間を取得する
			aggrTmp.addAll(GetClosurePeriod.get(require, closure.getCompanyId().v(), employeeId,
					endYMPeriod.end(), Optional.empty(), Optional.empty(), Optional.empty()));
		});
		List<ClosurePeriod> aggrPeriods = new ArrayList<>();
		aggrPeriods.addAll(aggrTmp);
		// 締め処理期間のうち、同じ年月の期間をまとめる
		Map<YearMonth, DatePeriod> closurePeriods = new HashMap<>();
		for (val aggrPeriod : aggrPeriods){
			YearMonth calcYearMonth = aggrPeriod.getYearMonth();
			for (val detailPeriod : aggrPeriod.getAggrPeriods()){
				DatePeriod calcPeriod = detailPeriod.getPeriod();
				if (closurePeriods.containsKey(calcYearMonth)){
					DatePeriod oldPeriod = closurePeriods.get(calcYearMonth);
					GeneralDate startDate = calcPeriod.start();
					GeneralDate endDate = calcPeriod.end();
					if (startDate.after(oldPeriod.start())) startDate = oldPeriod.start();
					if (endDate.before(oldPeriod.end())) endDate = oldPeriod.end();
					calcPeriod = new DatePeriod(startDate, endDate);
				}
				closurePeriods.put(calcYearMonth, calcPeriod);
			}
		}
		List<YearMonth> keys = closurePeriods.keySet().stream().collect(Collectors.toList());
		keys.sort((a, b) -> a.compareTo(b));
		MutableValue<AggrResultOfAnnualLeave> prevAnnualLeave = new MutableValue<>();
		MutableValue<AggrResultOfReserveLeave> prevReserveLeave = new MutableValue<>();
		List<RsvLeaUsedCurrentMonExport> tmp = Collections.synchronizedList(new ArrayList<>());
		parallel.forEach (keys, key -> {
			val closurePeriod = closurePeriods.get(key);
			// 期間中の年休積休残数を取得
			AggrResultOfAnnAndRsvLeave aggrResult = GetAnnAndRsvRemNumWithinPeriod.algorithm(require,
					cacheCarrier, closure.getCompanyId().v(),
					employeeId,
					closurePeriod,
					InterimRemainMngMode.OTHER,
					closurePeriod.end(),
					false,
					false,
					Optional.empty(),
					Optional.empty(),
					Optional.empty(),
					Optional.empty(),
					Optional.empty(),
					prevAnnualLeave.optional(),
					prevReserveLeave.optional());
			prevAnnualLeave.set(aggrResult.getAnnualLeave().isPresent() ? aggrResult.getAnnualLeave().get() : null);
			prevReserveLeave.set(aggrResult.getReserveLeave().isPresent() ? aggrResult.getReserveLeave().get() : null);
			
			// 結果をListに追加
			val aggrResultOfReserveOpt = aggrResult.getReserveLeave();
			if (aggrResultOfReserveOpt.isPresent()){
				val aggrResultOfReserve = aggrResultOfReserveOpt.get();
				val withMinus =
						aggrResultOfReserve.getAsOfPeriodEnd().getRemainingNumber().getReserveLeaveWithMinus();
				
				tmp.add(new RsvLeaUsedCurrentMonExport(
						key,
						withMinus.getUsedNumber().getUsedDays(),
						withMinus.getRemainingNumber().getTotalRemainingDays()));
			}
		});
		List<RsvLeaUsedCurrentMonExport> results = new ArrayList<>();
		results.addAll(tmp);
		// 年月毎積立年休の集計結果を返す
		return results;
	}

	//RequestList364 - ver2
	@Override
	public List<RsvLeaUsedCurrentMonExport> getRemainRsvAnnAfCurMonV2(String employeeId, 
			YearMonthPeriod period, MonAggrCompanySettings companySets, 
			MonAggrEmployeeSettings employeeSets) {
		
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
		
		// 社員に対応する処理締めを取得する
		val closure = ClosureService.getClosureDataByEmployee(require, cacheCarrier, employeeId, GeneralDate.today());
		if (closure == null) return new ArrayList<>();
		
		// 指定した年月の期間をすべて取得する
		List<DatePeriod> endYMPeriods = closure.getPeriodByYearMonth(period.end());
		List<ClosurePeriod> aggrPeriods = new ArrayList<>();
		for(DatePeriod endYMPeriod : endYMPeriods){
			// 集計期間を取得する
			aggrPeriods.addAll(GetClosurePeriod.get(require, closure.getCompanyId().v(), employeeId,
					endYMPeriod.end(), Optional.empty(), Optional.empty(), Optional.empty()));
		}
		// 締め処理期間のうち、同じ年月の期間をまとめる
		Map<YearMonth, DatePeriod> closurePeriods = new HashMap<>();
		for (val aggrPeriod : aggrPeriods){
			YearMonth calcYearMonth = aggrPeriod.getYearMonth();
			for (val detailPeriod : aggrPeriod.getAggrPeriods()){
				DatePeriod calcPeriod = detailPeriod.getPeriod();
				if (closurePeriods.containsKey(calcYearMonth)){
					DatePeriod oldPeriod = closurePeriods.get(calcYearMonth);
					GeneralDate startDate = calcPeriod.start();
					GeneralDate endDate = calcPeriod.end();
					if (startDate.after(oldPeriod.start())) startDate = oldPeriod.start();
					if (endDate.before(oldPeriod.end())) endDate = oldPeriod.end();
					calcPeriod = new DatePeriod(startDate, endDate);
				}
				closurePeriods.put(calcYearMonth, calcPeriod);
			}
		}
		List<YearMonth> keys = closurePeriods.keySet().stream().collect(Collectors.toList());
		keys.sort((a, b) -> a.compareTo(b));
		MutableValue<AggrResultOfAnnualLeave> prevAnnLea = new MutableValue<>();
		MutableValue<AggrResultOfReserveLeave> prevRsvLeave = new MutableValue<>();
		Optional<ClosureStatusManagement> sttMng = clsSttMngRepo.getLatestByEmpId(employeeId);
		Optional<GeneralDate> closureStartOpt = GetClosureStartForEmployee.algorithm(require, cacheCarrier, employeeId);
		List<RsvLeaUsedCurrentMonExport> results = new ArrayList<>();
		for(YearMonth key : keys) {
			DatePeriod clsPeriod = closurePeriods.get(key);
			// 期間中の年休積休残数を取得
			AggrResultOfAnnAndRsvLeave aggrResult = GetAnnAndRsvRemNumWithinPeriod.getRemainAnnRscByPeriod(
					require, cacheCarrier,
					closure.getCompanyId().v(), employeeId, clsPeriod, InterimRemainMngMode.OTHER,
					clsPeriod.end(), false, false, Optional.empty(), Optional.empty(), Optional.empty(),
					Optional.empty(), Optional.empty(), prevAnnLea.optional(), prevRsvLeave.optional(),
					companySets == null ?  Optional.empty() : Optional.of(companySets),
					employeeSets == null ? Optional.empty() : Optional.of(employeeSets),
					Optional.empty(),sttMng, closureStartOpt);
			
			prevAnnLea.set(aggrResult.getAnnualLeave().isPresent() ? aggrResult.getAnnualLeave().get() : null);
			prevRsvLeave.set(aggrResult.getReserveLeave().isPresent() ? aggrResult.getReserveLeave().get() : null);
			
			// 結果をListに追加
			val aggrResultOfReserveOpt = aggrResult.getReserveLeave();
			if (aggrResultOfReserveOpt.isPresent()){
				val aggrResultOfReserve = aggrResultOfReserveOpt.get();
				val withMinus =
						aggrResultOfReserve.getAsOfPeriodEnd().getRemainingNumber().getReserveLeaveWithMinus();
				
				results.add(new RsvLeaUsedCurrentMonExport(
						key,
						withMinus.getUsedNumber().getUsedDays(),
						withMinus.getRemainingNumber().getTotalRemainingDays()));
			}
		}
		// 年月毎積立年休の集計結果を返す
		return results;
	}
}
