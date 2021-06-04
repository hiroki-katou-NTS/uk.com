package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.CalcNextAnnualLeaveGrantDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;
@Stateless
public class GetPeriodFromPreviousToNextGrantDateImpl implements GetPeriodFromPreviousToNextGrantDate{

	// 現在
	private static final int CURRENT = 0;
	// 1年経過時点
	private static final int AFTER_1_YEAR = 1;
	// 1年以上前（過去） - morethan a year ago
	private static final int PAST = 2;
	
	@Inject
	private AnnLeaEmpBasicInfoRepository annLeaEmpBasicInfoRepository;
	@Inject
	private EmpEmployeeAdapter empEmployee;
	@Inject 
	private RecordDomRequireService requireService;
	
	
	/**
	 * 処理概要： 指定した年月を基準に、前回付与日から次回付与日までの期間を取得 
	 * Processing outline: Obtain the period from the previous grant date to the next grant date based on the specified date
	 * @param 会社ID - cid
	 * @param 社員ID - sid
	 * @param 指定年月（対象期間区分が１年経過時点の場合、NULL） - ym (NULL when the target period classification is after 1 year) : specified date
	 * @param 基準日- ymd Base date - Reference date
	 * @param 対象期間区分（現在/１年経過時点/過去） - Target period classification (current / one year passed / past)
	 * @param １年経過用期間(From-To) - One year elapsed period (From-To)
	 * @return 期間 - period
	 * ※年休社員基本情報が取得できない場合はnullを返す - Returns null if basic information on annual leave cannot be obtained
	 */
	@Override
	public Optional<GrantPeriodDto> getPeriodGrantDate(String cid, String sid, YearMonth ym, GeneralDate ymd,
			Integer periodOutput, Optional<DatePeriod> fromTo) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
		Optional<GrantPeriodDto> periodGrant = Optional.empty();
		// 対象期間区分をチェックする - Check the target period classification
		// 対象期間区分=null or 現在 or 過去の場合 - CURRENT: 0 , PAST: 2
		if(periodOutput == null || periodOutput == CURRENT || periodOutput == PAST)  {
			// 社員に対応する処理締めを取得する  - 1
			Closure closureInfor = ClosureService.getClosureDataByEmployee(require, cacheCarrier, sid, ymd);
			if(closureInfor == null) {
				return Optional.empty();
			}
			// 指定した年月の期間を算出する - 2
			DatePeriod datePeriodClosure = ClosureService.getClosurePeriod(require, closureInfor.getClosureId().value, ym);
			if (datePeriodClosure == null) {
				return Optional.empty();
			}
			//指定した年月日を基準に、前回付与日から次回付与日までの期間を取得 - 3
			periodGrant = this.getPeriodYMDGrant(cid, sid, datePeriodClosure.start().addDays(1), periodOutput, fromTo);
		}
		// 対象期間区分=１年経過時点の場合 ( AFTER_1_YEAR ) 
		if(periodOutput == AFTER_1_YEAR) {
			// 指定した期間を基準に、前回付与日から次回付与日までの期間を取得
			Optional<DatePeriod> dateRange = fromTo
					.map(data -> new DatePeriod(data.start().addYears(-1), data.end().addYears(-1)));
			periodGrant = this.getPeriodYMDGrant(cid, sid, null, periodOutput, dateRange);
		}
		
		return periodGrant;
	}
	
	@Override
	public Optional<GrantPeriodDto> getPeriodYMDGrant(String cid, String sid, GeneralDate ymd, Integer periodOutput, Optional<DatePeriod> fromTo) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
		//ドメインモデル「年休社員基本情報」を取得する
		Optional<AnnualLeaveEmpBasicInfo> annualLeaveEmpBasicInfoOpt = annLeaEmpBasicInfoRepository.get(sid);
		if(!annualLeaveEmpBasicInfoOpt.isPresent()) {
			return Optional.empty();
		}
		EmployeeImport employeeInfor = empEmployee.findByEmpId(sid);
		//次回年休付与を計算  - Calculate the next annual leave grant
		List<NextAnnualLeaveGrant> lstAnnGrantNotDate = CalcNextAnnualLeaveGrantDate.algorithm(require, cacheCarrier,
				cid, sid, Optional.empty());
		List<NextAnnualLeaveGrant> lstAnnGrantDate = new ArrayList<>();
		if(!lstAnnGrantNotDate.isEmpty()) {
			DatePeriod period = new DatePeriod(employeeInfor.getEntryDate(), lstAnnGrantNotDate.get(0).getGrantDate().addYears(1));
			//入社年月日～次回年休付与日までの年休付与日を全て取得
			lstAnnGrantDate = CalcNextAnnualLeaveGrantDate.algorithm(require, cacheCarrier, cid, 
					sid, Optional.of(period));
		}
		lstAnnGrantDate.addAll(lstAnnGrantNotDate);
		if(lstAnnGrantNotDate.isEmpty() || lstAnnGrantDate.isEmpty()) {
			return Optional.empty();
		}	
		lstAnnGrantDate = lstAnnGrantDate.stream().sorted((a,b) -> a.getGrantDate().compareTo(b.getGrantDate())).collect(Collectors.toList());
		// 対象期間区分をチェックする - Check the target period classification
		// 対象期間区分=null or 現在 or 過去の場合 - Target period classification = null or present or past . CURRENT: 0 , PAST: 2
		if(periodOutput == null || periodOutput == CURRENT || periodOutput == PAST)  {	
			//INPUT．指定年月日から一番近い付与日を取得
			Optional<GeneralDate> nextDay = lstAnnGrantDate.stream().map(NextAnnualLeaveGrant::getGrantDate)
					.filter(date -> date.after(ymd))
					.min(GeneralDate::compareTo);
			// 取得した付与日の１つ前を取得
			GeneralDate preDay = lstAnnGrantDate.stream().map(NextAnnualLeaveGrant::getGrantDate)
					.filter(date -> date.before(ymd))
					.max(GeneralDate::compareTo)
			// 取得できない場合
			// 前回付与日←入社日
					.orElse(employeeInfor.getEntryDate());
			return nextDay.map(end -> new GrantPeriodDto(new DatePeriod(preDay, end.addDays(-1)), nextDay));
		} else {
			// 対象期間区分=１年経過時点の場合 ( AFTER_1_YEAR )
			// １年経過用期間(From-To)内に存在する年休付与日を取得する（複数ある場合は、一番大きな付与日を取得する）
			// Acquire the annual leave grant date that exists within the one-year elapsed period (From-To) (if there are multiple, obtain the largest grant date)
			List<GeneralDate> grantDateList = lstAnnGrantDate.stream()
					.map(NextAnnualLeaveGrant::getGrantDate)
					.collect(Collectors.toList());
			List<GeneralDate> lstGrantDate = grantDateList.stream()
					.filter(date -> date.afterOrEquals(fromTo.get().start()) && date.beforeOrEquals(fromTo.get().end()))
					.collect(Collectors.toList());
			if(lstGrantDate.isEmpty()) {
				return Optional.empty();
			}
			// (if there are multiple, obtain the largest grant date)
			// 期間．終了日← 前回年休付与日＋１年 ー　1日
			Optional<GeneralDate> startDate = lstGrantDate.stream().max(GeneralDate::compareTo);
			// 取得した「前回付与日」(A)の次の年休付与日を取得する
			return startDate.map(start -> {
				Optional<GeneralDate> nextGrantDate = Optional.empty();
				int index = grantDateList.indexOf(start);
				if (index < grantDateList.size()) {
					nextGrantDate = Optional.of(grantDateList.get(++index));
				}
				return new GrantPeriodDto(new DatePeriod(start, start.addYears(1).addDays(-1)), nextGrantDate);
			}); 
		}
	}
	
	@Override
	public Optional<GrantPeriodDto> getPeriodAfterOneYear(String cid, String sid, GeneralDate ymd, Integer periodOutput, Optional<DatePeriod> fromTo) {
		//指定した年月日を基準に、前回付与日から次回付与日までの期間を取得
		Optional<GrantPeriodDto> periodOpt = this.getPeriodYMDGrant(cid, sid, ymd, periodOutput, fromTo);
		if (!periodOpt.isPresent()) return Optional.empty();
		DatePeriod period = periodOpt.get().getPeriod();
		//終了日を開始日の１年後に更新
		DatePeriod result = new DatePeriod(period.start(), period.start().addYears(1).addDays(-1));
		//更新した期間を返す
		return Optional.of(new GrantPeriodDto(result, periodOpt.get().getNextGrantDate()));
	}
}
