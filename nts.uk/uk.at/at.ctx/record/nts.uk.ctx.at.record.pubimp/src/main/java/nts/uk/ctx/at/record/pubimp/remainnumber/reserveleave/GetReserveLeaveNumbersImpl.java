package nts.uk.ctx.at.record.pubimp.remainnumber.reserveleave;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnAndRsvRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagementRepository;
import nts.uk.ctx.at.record.pub.remainnumber.reserveleave.GetReserveLeaveNumbers;
import nts.uk.ctx.at.record.pub.remainnumber.reserveleave.ReserveLeaveNowExport;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RervLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.shr.com.context.AppContexts;

/**
 * 実装：社員の積立年休の月初残・使用・残数・未消化を取得する
 * @author shuichi_ishida
 */
@Stateless
public class GetReserveLeaveNumbersImpl implements GetReserveLeaveNumbers {

	/** 積立年休付与残数データ */
	@Inject
	private RervLeaGrantRemDataRepository rsvLeaGrantRemDataRepo;
	/** 社員に対応する締め開始日を取得する */
	@Inject
	private GetClosureStartForEmployee getClosureStartForEmployee;
	/** 当月の期間を算出する */
	@Inject
	private ClosureService closureService;
	/** 期間中の年休積休残数を取得 */
	@Inject
	private GetAnnAndRsvRemNumWithinPeriod getAnnAndRsvRemNumWithinPeriod;
	@Inject
	private ClosureStatusManagementRepository clsSttMngRepo;
	@Inject
	private GetClosureStartForEmployee clsStrForEmp;
	
	/** 社員の積立年休の月初残・使用・残数・未消化を取得する */
	@Override
	public ReserveLeaveNowExport algorithm(String employeeId) {
	
		ReserveLeaveNowExport result = new ReserveLeaveNowExport();
		
		// 「積立年休付与残数データ」を取得
		val grantRemDatas = this.rsvLeaGrantRemDataRepo.findNotExp(employeeId, AppContexts.user().companyId());
		
		// 年休残数を集計
		double totalDays = 0.0;
		for (val grantRemData : grantRemDatas){
			totalDays += grantRemData.getDetails().getRemainingNumber().v();
		}
		ReserveLeaveRemainingDayNumber startMonthRemain = new ReserveLeaveRemainingDayNumber(totalDays);
		
		//　社員に対応する締め開始日を取得する
		val closureStartOpt = this.getClosureStartForEmployee.algorithm(employeeId);
		if (!closureStartOpt.isPresent()) return result;
		val closureStart = closureStartOpt.get();

		// 社員に対応する処理締めを取得する
		val closure = this.closureService.getClosureDataByEmployee(employeeId, closureStart);
		if (closure == null) return result;

		// 当月の年月を取得する
		val currentMonth = closure.getClosureMonth().getProcessingYm();
		
		// 当月の期間を算出する　→　締め期間
		val closurePeriod = this.closureService.getClosurePeriod(closure.getClosureId().value, currentMonth);
		
		// 期間中の年休積休残数を取得
		val aggrResult = this.getAnnAndRsvRemNumWithinPeriod.algorithm(
				closure.getCompanyId().v(),
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
				Optional.empty(),
				Optional.empty());
		val aggrResultOfReserveOpt = aggrResult.getReserveLeave();
		if (!aggrResultOfReserveOpt.isPresent()) return result;
		val aggrResultOfReserve = aggrResultOfReserveOpt.get();
		
		// 積立年休の集計結果を出力用クラスにコピー
		val asOfPeriodEnd = aggrResultOfReserve.getAsOfPeriodEnd();
		ReserveLeaveGrantDayNumber grantDays = new ReserveLeaveGrantDayNumber(0.0);
		if (asOfPeriodEnd.getGrantInfo().isPresent()) grantDays = asOfPeriodEnd.getGrantInfo().get().getGrantDays();
		result = new ReserveLeaveNowExport(
				startMonthRemain,
				grantDays,
				asOfPeriodEnd.getRemainingNumber().getReserveLeaveWithMinus().getUsedNumber().getUsedDays(),
				asOfPeriodEnd.getRemainingNumber().getReserveLeaveWithMinus().getRemainingNumber().getTotalRemainingDays(),
				asOfPeriodEnd.getRemainingNumber().getReserveLeaveNoMinus().getUndigestedNumber().getUndigestedDays());

		// 当月積立年休を返す
		return result;
	}
	/**
	 * @author hoatt
	 * 社員の積立年休の月初残・使用・残数・未消化を取得する
	 * RequestList268 ver2
	 * @param employeeId 社員ID
	 * @return 積立年休現在状況
	 */
	@Override
	public ReserveLeaveNowExport getRsvRemainVer2(String employeeId, Optional<GeneralDate> closureDate, MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets) {
		//　社員に対応する締め開始日を取得する
		if (!closureDate.isPresent()){
			return null;
		}
		
		// 「積立年休付与残数データ」を取得
		val grantRemDatas = this.rsvLeaGrantRemDataRepo.findNotExp(employeeId, AppContexts.user().companyId());
		
		// 年休残数を集計
		double totalDays = 0.0;
		for (val grantRemData : grantRemDatas){
			totalDays += grantRemData.getDetails().getRemainingNumber().v();
		}
		ReserveLeaveRemainingDayNumber startMonthRemain = new ReserveLeaveRemainingDayNumber(totalDays);
		Closure closure = closureService.getClosureDataByEmployee(employeeId, closureDate.get());
		if(closure == null){
			return null;
		}
		// 当月の年月を取得する
		val currentMonth = closure.getClosureMonth().getProcessingYm();
		
		// 当月の期間を算出する　→　締め期間
		val closurePeriod = closureService.getClosurePeriod(closure.getClosureId().value, currentMonth);
		Optional<ClosureStatusManagement> sttMng = clsSttMngRepo.getLatestByEmpId(employeeId);
		Optional<GeneralDate> closureStartOpt = clsStrForEmp.algorithm(employeeId);
		// 期間中の年休積休残数を取得
		AggrResultOfAnnAndRsvLeave aggrResult = getAnnAndRsvRemNumWithinPeriod.getRemainAnnRscByPeriod(
				closure.getCompanyId().v(), employeeId, closurePeriod, InterimRemainMngMode.OTHER,
				closurePeriod.end(), false, false, Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
				companySets == null ?  Optional.empty() : Optional.of(companySets),
				employeeSets == null ? Optional.empty() : Optional.of(employeeSets), 
				Optional.empty(), sttMng, closureStartOpt);
		val aggrResultOfReserveOpt = aggrResult.getReserveLeave();
		if (!aggrResultOfReserveOpt.isPresent()){
			return null;
		}
		val aggrResultOfReserve = aggrResultOfReserveOpt.get();
		
		// 積立年休の集計結果を出力用クラスにコピー
		val asOfPeriodEnd = aggrResultOfReserve.getAsOfPeriodEnd();
		ReserveLeaveGrantDayNumber grantDays = new ReserveLeaveGrantDayNumber(0.0);
		if (asOfPeriodEnd.getGrantInfo().isPresent()) grantDays = asOfPeriodEnd.getGrantInfo().get().getGrantDays();
		// 当月積立年休を返す
		return new ReserveLeaveNowExport(startMonthRemain, grantDays,
				asOfPeriodEnd.getRemainingNumber().getReserveLeaveWithMinus().getUsedNumber().getUsedDays(),
				asOfPeriodEnd.getRemainingNumber().getReserveLeaveWithMinus().getRemainingNumber().getTotalRemainingDays(),
				asOfPeriodEnd.getRemainingNumber().getReserveLeaveNoMinus().getUndigestedNumber().getUndigestedDays());
	}
}
