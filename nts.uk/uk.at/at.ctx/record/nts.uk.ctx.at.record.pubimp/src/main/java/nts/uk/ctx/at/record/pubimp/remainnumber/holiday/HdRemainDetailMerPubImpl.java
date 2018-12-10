package nts.uk.ctx.at.record.pubimp.remainnumber.holiday;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.AggrResultOfAnnualLeaveEachMonth;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.AnnLeaveOfThisMonth;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.AnnLeaveRemainNumberPub;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.NextHolidayGrantDate;
import nts.uk.ctx.at.record.pub.remainnumber.holiday.CheckCallRQ;
import nts.uk.ctx.at.record.pub.remainnumber.holiday.HdRemainDetailMer;
import nts.uk.ctx.at.record.pub.remainnumber.holiday.HdRemainDetailMerPub;
import nts.uk.ctx.at.record.pub.remainnumber.reserveleave.GetReserveLeaveNumbers;
import nts.uk.ctx.at.record.pub.remainnumber.reserveleave.GetRsvLeaNumAfterCurrentMon;
import nts.uk.ctx.at.record.pub.remainnumber.reserveleave.ReserveLeaveNowExport;
import nts.uk.ctx.at.record.pub.remainnumber.reserveleave.RsvLeaUsedCurrentMonExport;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffManagementQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.InterimRemainAggregateOutputData;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;
@Stateless
public class HdRemainDetailMerPubImpl implements HdRemainDetailMerPub{

	@Inject
	private GetClosureStartForEmployee closure;
	@Inject
	private AnnLeaveRemainNumberPub rq265;
	@Inject
	private GetReserveLeaveNumbers rq268;
	@Inject
	private BreakDayOffManagementQuery rq269;
	@Inject
	private GetRsvLeaNumAfterCurrentMon rq364;
	/** 月別集計が必要とするリポジトリ */
	@Inject
	private RepositoriesRequiredByMonthlyAggr repoRqMonAgg;
	
	/**
	 * Mer RQ265,268,269,363,364,369
	 * @param employeeId
	 * @param currentMonth
	 * @param baseDate
	 * @param period
	 * @return
	 */
	@Override
	public HdRemainDetailMer getHdRemainDetailMer(String employeeId, YearMonth currentMonth, GeneralDate baseDate,
			DatePeriod period, CheckCallRQ checkCall) {
		
		Optional<GeneralDate> closureDate = Optional.empty();
		if(checkCall.isCall268() || checkCall.isCall369()){
			closureDate = closure.algorithm(employeeId);
		}
		String companyId = AppContexts.user().companyId();
		MonAggrCompanySettings companySets = null;
		if(checkCall.isCall265() || checkCall.isCall268() || checkCall.isCall363() || checkCall.isCall364()){
			companySets = MonAggrCompanySettings.loadSettings(companyId, repoRqMonAgg);
		}
//		MonAggrEmployeeSettings employeeSets = MonAggrEmployeeSettings.loadSettings(
//				companyId, employeeId, period, repoRqMonAgg);
		MonAggrEmployeeSettings employeeSets = null;
		//265
		AnnLeaveOfThisMonth result265 = null;
		if(checkCall.isCall265()){
			result265 = rq265.getAnnLeaOfThisMonVer2(employeeId, companySets, employeeSets);
		}
		//268
		ReserveLeaveNowExport result268 = null;
		if(checkCall.isCall268()){
			result268 = rq268.getRsvRemainVer2(employeeId, closureDate, companySets, employeeSets);
		}
		//269
		List<InterimRemainAggregateOutputData> result269 = new ArrayList<>();
		if(checkCall.isCall269()){
			result269 = rq269.getInterimRemainAggregate(employeeId, baseDate, period.start().yearMonth(), period.end().yearMonth());
		}
		//363
		List<AggrResultOfAnnualLeaveEachMonth> result363 = new ArrayList<>();
		if(checkCall.isCall363()){
			result363 = rq265.getAnnLeaRemainAfThisMonVer2(employeeId, new DatePeriod(GeneralDate.ymd(currentMonth.year(), currentMonth.month(), 1), period.end()), companySets, employeeSets);
		}
		//364
		List<RsvLeaUsedCurrentMonExport> result364 = new ArrayList<>();
		if(checkCall.isCall364()){
			result364 = rq364.getRemainRsvAnnAfCurMonV2(employeeId, new YearMonthPeriod(currentMonth, period.end().yearMonth()), companySets, employeeSets);
		}
		//369
		NextHolidayGrantDate result369 = null;
		if(checkCall.isCall369()){
			result369 = rq265.getNextHdGrantDateVer2(companyId, employeeId, closureDate);
		}
		
		return new HdRemainDetailMer(result265, result268, result269, result363, result364, result369);
	}

}
