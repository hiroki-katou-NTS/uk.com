package nts.uk.ctx.at.shared.dom.remainingnumber.export;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.arc.time.calendar.period.DatePeriod;

@Stateless
public class RemainManagementExportImpl implements RemainManagementExport{
	
	@Inject
	private ClosureRepository closureRepo;
	@Inject
	private ClosureEmploymentRepository closureEmpRepo;
	@Inject
	private ShareEmploymentAdapter shrEmpAdapter;
	
	@Override
	public ClosureRemainPeriodOutputData getClosureRemainPeriod(String employeeId, GeneralDate baseDate, YearMonth startMonth, YearMonth endMonth) {
		ClosureRemainPeriodOutputData outputData = new ClosureRemainPeriodOutputData(null, null, null);
		CacheCarrier cacheCarrier = new CacheCarrier();
		// アルゴリズム「社員に対応する処理締めを取得する」を実行する
		Closure closureData = ClosureService.getClosureDataByEmployee(createRequireM3(), cacheCarrier, employeeId, baseDate);
		if(closureData == null) {
			return null;
		}
		//当月と指定開始年月をチェックする
		if(startMonth.greaterThanOrEqualTo(closureData.getClosureMonth().getProcessingYm())) {
			outputData.setStartMonth(startMonth);
		} else {
			outputData.setStartMonth(closureData.getClosureMonth().getProcessingYm());
		}		
		outputData.setEndMonth(endMonth);
		if(outputData.getStartMonth().greaterThan(outputData.getEndMonth())) {
			return null;
		}
		
		outputData.setClosure(closureData);
		return outputData;
	}
	@Override
	public DatePeriod getClosureOfMonthDesignation(Closure closureData, YearMonth ym) {
		// 「締め」のアルゴリズム「指定した年月の期間をすべて取得する」を実行する
		List<DatePeriod> lstDatePeriod = closureData.getPeriodByYearMonth(ym);
		//残数算出期間を設定する
		if(lstDatePeriod.isEmpty()) {
			return null;
		}
		if(lstDatePeriod.size() == 1) {
			DatePeriod dateData = lstDatePeriod.get(0);
			return new DatePeriod(dateData.start(), dateData.end());
		}
		DatePeriod dateData1 = lstDatePeriod.get(0);
		DatePeriod dateData2 = lstDatePeriod.get(1);
		
		return new DatePeriod(dateData1.start(), dateData2.end());
	}
	@Override
	public DatePeriod periodCovered(String sid, GeneralDate baseDate) {
		CacheCarrier cacheCarrier = new CacheCarrier();
		
		DatePeriod closureBySid = ClosureService.findClosurePeriod(createRequireM3(), cacheCarrier, sid, baseDate);
		if(closureBySid == null) {
			return null;
		}
		GeneralDate endDate = closureBySid.end().addYears(1).addDays(-1);  
		DatePeriod adjustDate = new DatePeriod(closureBySid.start(), endDate);
		return adjustDate;
	}

	private ClosureService.RequireM3 createRequireM3 () {
		
		return new ClosureService.RequireM3() {
			
			@Override
			public Optional<Closure> closure(String companyId, int closureId) {
				return closureRepo.findById(companyId, closureId);
			}
			
			@Override
			public Optional<ClosureEmployment> employmentClosure(String companyID, String employmentCD) {
				return closureEmpRepo.findByEmploymentCD(companyID, employmentCD);
			}
			
			@Override
			public Optional<BsEmploymentHistoryImport> employmentHistory(CacheCarrier cacheCarrier, String companyId,
					String employeeId, GeneralDate baseDate) {
				return shrEmpAdapter.findEmploymentHistoryRequire(cacheCarrier, companyId, employeeId, baseDate);
			}

			@Override
			public List<ClosureEmployment> employmentClosureClones(String companyID, List<String> employmentCD) {
				return closureEmpRepo.findListEmployment(companyID, employmentCD);
			}

			@Override
			public List<Closure> closureClones(String companyId, List<Integer> closureId) {
				return closureRepo.findByListId(companyId, closureId);
			}

			@Override
			public Map<String, BsEmploymentHistoryImport> employmentHistoryClones(String companyId, List<String> employeeId,
					GeneralDate baseDate) {
				return shrEmpAdapter.findEmpHistoryVer2(companyId, employeeId, baseDate);
			}
		};
	}
}
