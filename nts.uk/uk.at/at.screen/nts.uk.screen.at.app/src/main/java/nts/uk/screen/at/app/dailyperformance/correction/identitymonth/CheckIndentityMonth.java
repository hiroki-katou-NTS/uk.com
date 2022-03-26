package nts.uk.screen.at.app.dailyperformance.correction.identitymonth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.company.AffComHistItemImport;
import nts.uk.ctx.at.record.dom.adapter.company.AffCompanyHistImport;
import nts.uk.ctx.at.record.dom.adapter.company.SyCompanyRecordAdapter;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.ClosurePeriod;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.GetClosurePeriod;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.confirmstatusmonthly.AvailabilityAtr;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.confirmstatusmonthly.ConfirmStatusMonthly;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.confirmstatusmonthly.ConfirmStatusResult;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.confirmstatusmonthly.ReleasedAtr;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.confirmstatusmonthly.StatusConfirmMonthDto;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.identificationstatus.export.CheckIndentityDayConfirm;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month.ConfirmationMonth;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.ConfirmationMonthRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DisplayFormat;
import nts.uk.screen.at.app.dailyperformance.correction.dto.cache.DPCorrectionStateParam;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author thanhnx
 *
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CheckIndentityMonth {

	@Inject
	private ConfirmationMonthRepository confirmationMonthRepository;

	@Inject
	private SyCompanyRecordAdapter syCompanyRecordAdapter;

	@Inject
	private CheckIndentityDayConfirm checkIndentityDayConfirm;
	
	@Inject
	private ClosureRepository closureRepository;
	
	@Inject
	private ConfirmStatusMonthly confirmStatusMonthly;
	
	@Inject
	private RecordDomRequireService requireService;

	// 月の本人確認をチェックする
	public IndentityMonthResult checkIndenityMonth(IndentityMonthParam param) {

		// 集計期間
		List<ClosurePeriod> closurePeriods = GetClosurePeriod.get(
				requireService.createRequire(),
				param.companyId, param.employeeId, param.dateRefer,
				Optional.empty(), Optional.empty(), Optional.empty());
		//closurePeriods  = closurePeriods
		if (closurePeriods.isEmpty())
			return new IndentityMonthResult(false, false, false);

		Pair<DatePeriod, ClosurePeriod> pairClosure = getDatePeriodOldest(closurePeriods);
		ClosurePeriod closurePeriodOldest = pairClosure.getRight();
		DatePeriod datePeriodOldest = pairClosure.getLeft();
		// 月の本人確認を取得する
		Optional<ConfirmationMonth> optCMonth = confirmationMonthRepository.findByKey(param.companyId, param.employeeId,
				closurePeriodOldest.getClosureId(),
				closurePeriodOldest.getClosureDate(),
				closurePeriodOldest.getYearMonth());
		// 取得できたかチェックする
		if (optCMonth.isPresent()) {
			// A2_6 hide, A2_7 show
			return new IndentityMonthResult(false, true, false);
		}

		DatePeriod period = datePeriodOldest;
		// 社員ID（List）と指定期間から所属会社履歴項目を取得
		List<AffCompanyHistImport> affCompanyHis = syCompanyRecordAdapter
				.getAffCompanyHistByEmployee(Arrays.asList(param.employeeId), period);

		List<DatePeriod> datePeriods = new ArrayList<>();
		if (!affCompanyHis.isEmpty()) {
			for (AffComHistItemImport his : affCompanyHis.get(0).getLstAffComHistItem()) {
				DatePeriod datePeriodHis = his.getDatePeriod();
				GeneralDate endDateB = datePeriodHis.end();
				GeneralDate startDateA = datePeriodHis.start();
				// 「所属会社履歴項目．退職日」がパラメータ「当月の期間」に含まれているかチェックする
				if (endDateB != null
						&& (endDateB.afterOrEquals(period.start()) && startDateA.beforeOrEquals(period.end()))) {
					datePeriods
							.add(new DatePeriod(period.start().afterOrEquals(startDateA) ? period.start() : startDateA,
									period.end().beforeOrEquals(endDateB) ? period.end() : endDateB));
				}
			}
		}
		if (datePeriods.isEmpty())
			return new IndentityMonthResult(true, true, false);
		List<GeneralDate> lstDate = datePeriods.stream().flatMap(x -> x.datesBetween().stream())
				.collect(Collectors.toList());

		boolean checkIndentityDay = checkIndentityDayConfirm.checkIndentityDay(param.employeeId, lstDate);
		// A2_6 show, A2_7 hide
		return new IndentityMonthResult(true, checkIndentityDay, false);
	}
	
	//月の本人確認をチェックする
	public IndentityMonthResult checkIndenityMonth(IndentityMonthParam param, DPCorrectionStateParam stateParam) {
		//取得した「本人確認処理利用設定．月の本人確認を利用する」
		if(!param.getIdentityUseSetDto().isPresent()|| !param.getIdentityUseSetDto().get().isUseIdentityOfMonth()) {
			return new IndentityMonthResult(false, false, true);
		}
		
		if(param.getDisplayFormat() != DisplayFormat.Individual.value) {
			return new IndentityMonthResult(false, false, true);
		}
		
		if(!param.getEmployeeId().equals(AppContexts.user().employeeId())) {
			return new IndentityMonthResult(false, false, true);
		}
		
		if(stateParam.getDateInfo() == null) {
			return new IndentityMonthResult(false, false, true);
		}
		
		Optional<Closure> closureIdOpt = closureRepository.findById(param.getCompanyId(), stateParam.getDateInfo().getClosureId());
		if (!closureIdOpt.isPresent()) {
			return new IndentityMonthResult(false, false, true);
		}
		
		YearMonth yearMonthCurrent = closureIdOpt.get().getClosureMonth().getProcessingYm();
		
		int yearMonthTarget = stateParam.getDateInfo().getYearMonth();
		if(yearMonthTarget != yearMonthCurrent.v()) {
			 return new IndentityMonthResult(false, false, true);
		}
		
		//TODO: [No.586]月の実績の確認状況を取得する（NEW）
		Optional<StatusConfirmMonthDto> statusOpt = confirmStatusMonthly.getConfirmStatusMonthly(param.getCompanyId(),
				Arrays.asList(param.employeeId), YearMonth.of(stateParam.getDateInfo().getYearMonth()), closureIdOpt.get().getClosureId().value);
		if(!statusOpt.isPresent())  return new IndentityMonthResult(false, false, true);
		List<ConfirmStatusResult> listConfirmStatus = statusOpt.get().getListConfirmStatus();
		ConfirmStatusResult confirmResult = listConfirmStatus.stream()
				.filter(x -> x.getEmployeeId().equals(param.getEmployeeId()) && x.getYearMonth().v() == yearMonthTarget)
				.findFirst().orElse(null);
		Boolean show26 = true;		
		Boolean enableButton = false;
		if(confirmResult.isConfirmStatus()) {
			show26 = false;
			enableButton = confirmResult.getWhetherToRelease().value == ReleasedAtr.CAN_RELEASE.value ? true : false;
		}else {
			show26 = true;
			enableButton = confirmResult.getImplementaPropriety().value == AvailabilityAtr.CAN_RELEASE.value ? true : false;
		}
		
		return new IndentityMonthResult(show26, enableButton, false);
	}
	
	private Pair<DatePeriod, ClosurePeriod> getDatePeriodOldest(List<ClosurePeriod>  lstClosurePeriod) {
		ClosurePeriod closurePeriodResult = new ClosurePeriod();
		DatePeriod dateMax = new DatePeriod(GeneralDate.max(), GeneralDate.max());
		for(ClosurePeriod closurePeriod : lstClosurePeriod) {
			val lstPeriod = closurePeriod.getAggrPeriods().stream().sorted((x, y) -> x.getPeriod().start().compareTo(y.getPeriod().start())).collect(Collectors.toList());
			if(!lstPeriod.isEmpty()) {
				if(dateMax.start().after(lstPeriod.get(0).getPeriod().start())) {
					dateMax = lstPeriod.get(0).getPeriod();
					closurePeriodResult = closurePeriod;
				}
			}
		}
		return Pair.of(dateMax, closurePeriodResult);
	}
	
}
