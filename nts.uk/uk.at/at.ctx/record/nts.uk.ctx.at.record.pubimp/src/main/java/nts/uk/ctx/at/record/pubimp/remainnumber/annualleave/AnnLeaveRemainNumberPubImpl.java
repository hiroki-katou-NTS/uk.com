package nts.uk.ctx.at.record.pubimp.remainnumber.annualleave;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveMaxRemainingTime;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.HalfDayAnnualLeave;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.RealAnnualLeave;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.ClosurePeriod;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.GetClosurePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoDomService;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo.CalcNextAnnualLeaveGrantDate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo.valueobject.AnnLeaRemNumValueObject;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnLeaRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.TempAnnualLeaveManagement;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.TempAnnualLeaveMngMode;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.TempAnnualLeaveMngRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnualLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AnnualLeaveGrantRemaining;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AnnualLeaveInfo;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.CheckShortageFlex;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.AggrResultOfAnnualLeaveEachMonth;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.AnnLeaveOfThisMonth;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.AnnLeaveRemainNumberPub;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.ClosurePeriodEachYear;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.export.AnnualLeaveGrantExport;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.export.AnnualLeaveManageInforExport;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.export.AnnualLeaveRemainingNumberExport;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.export.AttendanceRateCalPeriod;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.export.CalYearOffWorkAttendRateExport;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.export.ReNumAnnLeaReferenceDateExport;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.StandardCalculation;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AnnLeaveRemainNumberPubImpl implements AnnLeaveRemainNumberPub {

	@Inject
	private AnnLeaEmpBasicInfoDomService annLeaService;

	@Inject
	private GetClosureStartForEmployee closureStartService;

	@Inject
	private CheckShortageFlex checkShortageFlex;

	@Inject
	private GetAnnLeaRemNumWithinPeriod getAnnLeaRemNumWithinPeriod;

	@Inject
	private AnnLeaEmpBasicInfoRepository annLeaBasicInfoRepo;

	@Inject
	private CalcNextAnnualLeaveGrantDate calcNextAnnualLeaveGrantDate;

	@Inject
	private GetClosurePeriod getClosurePeriod;
	
	@Inject
	private TempAnnualLeaveMngRepository tempAnnualLeaveMngRepository;
	
	/** 年休付与テーブル設定 */
	@Inject
	private YearHolidayRepository yearHolidayRepo;

	@Override
	public AnnLeaveOfThisMonth getAnnLeaveOfThisMonth(String employeeId) {
		AnnLeaveOfThisMonth result = new AnnLeaveOfThisMonth();
		try {

			String companyId = AppContexts.user().companyId();
			// 月初の年休残数を取得
			AnnLeaRemNumValueObject remainNumber = annLeaService.getAnnLeaveNumber(companyId, employeeId);
			result.setFirstMonthRemNumDays(remainNumber.getDays());
			result.setFirstMonthRemNumMinutes(remainNumber.getMinutes());
			// 計算した年休残数を出力用クラスにコピー
			Optional<GeneralDate> startDate = closureStartService.algorithm(employeeId);
			if (!startDate.isPresent())
				return null;
			// 社員に対応する締め期間を取得する
			DatePeriod datePeriod = checkShortageFlex.findClosurePeriod(employeeId, startDate.get());
			// 期間中の年休残数を取得
			Optional<AggrResultOfAnnualLeave> aggrResult = getAnnLeaRemNumWithinPeriod.algorithm(companyId, employeeId,
					datePeriod, TempAnnualLeaveMngMode.OTHER, datePeriod.end(), false, false, Optional.empty(),
					Optional.empty(), Optional.empty());
			if (!aggrResult.isPresent())
				return null;
			result.setUsedDays(aggrResult.get().getAsOfPeriodEnd().getRemainingNumber().getAnnualLeaveWithMinus()
					.getUsedNumber().getUsedDays().getUsedDays());

			result.setUsedMinutes(
					aggrResult.get().getAsOfPeriodEnd().getRemainingNumber().getAnnualLeaveWithMinus().getUsedNumber()
							.getUsedTime()
							.isPresent()
									? Optional.of(aggrResult.get().getAsOfPeriodEnd().getRemainingNumber()
											.getAnnualLeaveWithMinus().getUsedNumber().getUsedTime().get().getUsedTime()
											.v())
									: Optional.empty());

			result.setRemainDays(aggrResult.get().getAsOfPeriodEnd().getRemainingNumber().getAnnualLeaveWithMinus()
					.getRemainingNumber().getTotalRemainingDays());

			result.setRemainMinutes(
					aggrResult.get().getAsOfPeriodEnd().getRemainingNumber().getAnnualLeaveWithMinus()
							.getRemainingNumber().getTotalRemainingTime()
							.isPresent()
									? Optional.of(aggrResult.get().getAsOfPeriodEnd().getRemainingNumber()
											.getAnnualLeaveWithMinus().getRemainingNumber().getTotalRemainingTime()
											.get().v())
									: Optional.empty());

			// ドメインモデル「年休社員基本情報」を取得   
			Optional<AnnualLeaveEmpBasicInfo> basicInfo = annLeaBasicInfoRepo.get(employeeId);
			// 次回年休付与を計算
			List<NextAnnualLeaveGrant> annualLeaveGrant = calcNextAnnualLeaveGrantDate.algorithm(companyId, employeeId,
					Optional.empty());
			if (annualLeaveGrant!= null && annualLeaveGrant.size() > 0){
				result.setGrantDate(annualLeaveGrant.get(0).getGrantDate());
				result.setGrantDays(annualLeaveGrant.get(0).getGrantDays().v());
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<AggrResultOfAnnualLeaveEachMonth> getAnnLeaveRemainAfterThisMonth(String employeeId,
			DatePeriod datePeriod) {
		try {

			String companyId = AppContexts.user().companyId();
			GeneralDate baseDate = GeneralDate.today();
			// 社員に対応する処理締めを取得する
			Optional<Closure> closure = checkShortageFlex.findClosureByEmployee(employeeId, baseDate);
			if (!closure.isPresent())
				return null;
			// 指定した年月の期間をすべて取得する
			List<DatePeriod> periodByYearMonth = closure.get().getPeriodByYearMonth(datePeriod.end().yearMonth());
			if (periodByYearMonth == null || periodByYearMonth.size() == 0)
				return null;
			// 集計期間を計算する
			List<ClosurePeriod> listClosurePeriod = getClosurePeriod.get(companyId, employeeId,
					periodByYearMonth.get(periodByYearMonth.size() - 1).end(), Optional.empty(), Optional.empty(),
					Optional.empty());
			// 締め処理期間のうち、同じ年月の期間をまとめる
			Map<YearMonth, List<ClosurePeriod>> listMap = listClosurePeriod.stream()
					.collect(Collectors.groupingBy(ClosurePeriod::getYearMonth));

			List<ClosurePeriodEachYear> listClosurePeriodEachYear = new ArrayList<ClosurePeriodEachYear>();

			for (Map.Entry<YearMonth, List<ClosurePeriod>> item : listMap.entrySet()) {
				GeneralDate start = null, end = null;
				for (ClosurePeriod closurePeriodItem : item.getValue()) {
					for (AggrPeriodEachActualClosure actualClosureItem : closurePeriodItem.getAggrPeriods()) {
						if (start == null || start.compareTo(actualClosureItem.getPeriod().start()) > 0) {
							start = actualClosureItem.getPeriod().start();
						}
						if (end == null || end.compareTo(actualClosureItem.getPeriod().end()) < 0) {
							end = actualClosureItem.getPeriod().end();
						}
					}
				}

				listClosurePeriodEachYear.add(new ClosurePeriodEachYear(item.getKey(), new DatePeriod(start, end)));
			}

			List<AggrResultOfAnnualLeaveEachMonth> result = new ArrayList<AggrResultOfAnnualLeaveEachMonth>();

			Optional<AggrResultOfAnnualLeave> aggrResultOfAnnualLeave = Optional.empty();
			for (ClosurePeriodEachYear item : listClosurePeriodEachYear) {
				// 期間中の年休残数を取得
				aggrResultOfAnnualLeave = getAnnLeaRemNumWithinPeriod.algorithm(companyId, employeeId,
						item.getDatePeriod(), TempAnnualLeaveMngMode.OTHER, item.getDatePeriod().end(), false, false,
						Optional.empty(), Optional.empty(), aggrResultOfAnnualLeave);
				// 結果をListに追加
				if (aggrResultOfAnnualLeave.isPresent()) {
					result.add(
							new AggrResultOfAnnualLeaveEachMonth(item.getYearMonth(), aggrResultOfAnnualLeave.get()));
				}
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public ReNumAnnLeaReferenceDateExport getReferDateAnnualLeaveRemainNumber(String employeeID, GeneralDate date) {
		ReNumAnnLeaReferenceDateExport result = new ReNumAnnLeaReferenceDateExport();
		
		
		String companyId = AppContexts.user().companyId();
		// 社員に対応する締め開始日を取得する
		Optional<GeneralDate> startDate = closureStartService.algorithm(employeeID);
		if (!startDate.isPresent())
			return null;
		DatePeriod datePeriod = new DatePeriod(startDate.get(), date);
		// 期間中の年休残数を取得
		Optional<AggrResultOfAnnualLeave> aggrResult = getAnnLeaRemNumWithinPeriod.algorithm(companyId, employeeID,
				datePeriod, TempAnnualLeaveMngMode.OTHER, date, false, false, Optional.of(false), Optional.empty(),
				Optional.empty());
		if(aggrResult.isPresent()){
			AnnualLeaveInfo asOfPeriodEnd = aggrResult.get().getAsOfPeriodEnd();
			// 年休（マイナスあり）
			RealAnnualLeave  realAnnualLeave = asOfPeriodEnd.getRemainingNumber().getAnnualLeaveWithMinus();
			// 半日年休（マイナスあり）
			Optional<HalfDayAnnualLeave> halfDayAnnualLeaveWithMinus = asOfPeriodEnd.getRemainingNumber().getHalfDayAnnualLeaveWithMinus();
			// 時間年休（マイナスあり） 
			Optional<AnnualLeaveMaxRemainingTime> timeAnnualLeaveWithMinus = asOfPeriodEnd.getRemainingNumber().getTimeAnnualLeaveWithMinus();
			boolean flagNumber = false;
			if(halfDayAnnualLeaveWithMinus.isPresent()){
				flagNumber = true;
			}
			// 取得結果を出力用クラスに格納 
			AnnualLeaveRemainingNumberExport annualLeaveRemainingNumberExport = new AnnualLeaveRemainingNumberExport(realAnnualLeave.getRemainingNumber().getTotalRemainingDays().v(),
					realAnnualLeave.getRemainingNumber().getTotalRemainingTime().get().v(),
					flagNumber == true ? halfDayAnnualLeaveWithMinus.get().getRemainingNum().getTimes().v() : null,
					timeAnnualLeaveWithMinus.isPresent()? timeAnnualLeaveWithMinus.get().getTime().v(): null, 
					realAnnualLeave.getRemainingNumberAfterGrant().isPresent()?realAnnualLeave.getRemainingNumberAfterGrant().get().getTotalRemainingDays().v() : null , 
					realAnnualLeave.getRemainingNumberAfterGrant().isPresent()? realAnnualLeave.getRemainingNumberAfterGrant().get().getTotalRemainingTime().get().v(): null, 
					flagNumber == true ? halfDayAnnualLeaveWithMinus.get().getRemainingNum().getTimesAfterGrant().get().v() : null,
					timeAnnualLeaveWithMinus.isPresent()? timeAnnualLeaveWithMinus.get().getTimeAfterGrant().get().v() : null,
					1.00,
					2.00);
			result.setAnnualLeaveRemainNumberExport(annualLeaveRemainingNumberExport);
			List<AnnualLeaveGrantExport> annualLeaveGrantExports = new ArrayList<>();
			// add 年休付与情報(仮)
			if(!CollectionUtil.isEmpty(asOfPeriodEnd.getGrantRemainingList())){
				for(AnnualLeaveGrantRemaining annualLeave : asOfPeriodEnd.getGrantRemainingList()){
					AnnualLeaveGrantExport annualLeaveGrantExport = new AnnualLeaveGrantExport(annualLeave.getGrantDate(), 
							annualLeave.getDetails().getGrantNumber().getDays().v(),
							annualLeave.getDetails().getUsedNumber().getDays().v(),
							annualLeave.getDetails().getUsedNumber().getMinutes().get().v(),
							annualLeave.getDetails().getRemainingNumber().getDays().v(), 
							annualLeave.getDetails().getRemainingNumber().getMinutes().get().v(), 
							annualLeave.getDeadline());
					annualLeaveGrantExports.add(annualLeaveGrantExport);
				}
				result.setAnnualLeaveGrantExports(annualLeaveGrantExports);
			}
		}
		List<TempAnnualLeaveManagement> tempAnnualLeaveManagements = this.tempAnnualLeaveMngRepository.findByEmployeeID(employeeID);
		// add 年休管理情報(仮)
		if(!CollectionUtil.isEmpty(tempAnnualLeaveManagements)){
			List<AnnualLeaveManageInforExport> annualLeaveManageInforExports = new ArrayList<>();
			for(TempAnnualLeaveManagement temp : tempAnnualLeaveManagements){
				AnnualLeaveManageInforExport annualLeaveManageInforExport = new AnnualLeaveManageInforExport(temp.getYmd(),
						temp.getAnnualLeaveUse().v(),
						temp.getTimeAnnualLeaveUse().v(), 
						temp.getScheduleRecordAtr().value);
				annualLeaveManageInforExports.add(annualLeaveManageInforExport);
			}
			result.setAnnualLeaveManageInforExports(annualLeaveManageInforExports);
		}
		// 年休出勤率を計算する:TODO: Trong EA ghi chưa làm được
		
		if(result.getAnnualLeaveRemainNumberExport() != null){
			result.getAnnualLeaveRemainNumberExport().setAttendanceRate(1.00);
			result.getAnnualLeaveRemainNumberExport().setWorkingDays(2.0);
		}
		return result;
	}
}
