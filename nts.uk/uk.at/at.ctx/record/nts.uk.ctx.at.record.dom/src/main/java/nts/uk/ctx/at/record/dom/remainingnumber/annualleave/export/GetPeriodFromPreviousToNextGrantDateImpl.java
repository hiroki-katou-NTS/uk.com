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
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.CalcNextAnnualLeaveGrantDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;
import nts.arc.time.calendar.period.DatePeriod;
@Stateless
public class GetPeriodFromPreviousToNextGrantDateImpl implements GetPeriodFromPreviousToNextGrantDate{

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
	public Optional<DatePeriod> getPeriodGrantDate(String cid, String sid, YearMonth ym, GeneralDate ymd,
			Integer periodOutput, Optional<DatePeriod> fromTo) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
		Optional<DatePeriod> periodGrant = Optional.empty();
		// 対象期間区分をチェックする - Check the target period classification
		// 対象期間区分=null or 現在 or 過去の場合 - CURRENT: 0 , PAST: 2
		if(periodOutput == 0 || periodOutput == 2)  {
			// 社員に対応する処理締めを取得する
			Closure closureInfor = ClosureService.getClosureDataByEmployee(require, cacheCarrier, sid, ymd);
			if(closureInfor == null) {
				return Optional.empty();
			}
			//指定した年月の期間を算出する
			DatePeriod datePeriodClosure = ClosureService.getClosurePeriod(require, closureInfor.getClosureId().value, ym);
			//指定した年月日を基準に、前回付与日から次回付与日までの期間を取得
			periodGrant = this.getPeriodYMDGrant(cid, sid, datePeriodClosure.start().addDays(1), periodOutput, fromTo);
		}
		// 対象期間区分=１年経過時点の場合 ( AFTER_1_YEAR ) 
		if(periodOutput == 1) {
			// 指定した期間を基準に、前回付与日から次回付与日までの期間を取得
			// TODO : sửa cái này là phải thêm input vào GetPeriodFromPreviousToNextGrantDate là sửa lại gần hết các file . confirm vs KH 
			periodGrant = this.getPeriodYMDGrant(cid, sid, null, periodOutput, fromTo);
		}
		
		return periodGrant;
	}
	@Override
	public Optional<DatePeriod> getPeriodYMDGrant(String cid, String sid, GeneralDate ymd, Integer periodOutput, Optional<DatePeriod> fromTo) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
	
		//ドメインモデル「年休社員基本情報」を取得する
		Optional<AnnualLeaveEmpBasicInfo> annualLeaveEmpBasicInfoOpt = annLeaEmpBasicInfoRepository.get(sid);
		if(!annualLeaveEmpBasicInfoOpt.isPresent()) {
			return Optional.empty();
		}
		EmployeeImport employeeInfor = empEmployee.findByEmpId(sid);
		//次回年休付与を計算
		List<NextAnnualLeaveGrant> lstAnnGrantNotDate = CalcNextAnnualLeaveGrantDate.algorithm(require, cacheCarrier,
				cid, sid, Optional.empty());
		List<NextAnnualLeaveGrant> lstAnnGrantDate = new ArrayList<>();
		if(!lstAnnGrantNotDate.isEmpty()) {
			DatePeriod period = new DatePeriod(employeeInfor.getEntryDate(), lstAnnGrantNotDate.get(0).getGrantDate().addYears(1));
			//入社年月日～次回年休付与日までの年休付与日を全て取得
			lstAnnGrantDate = CalcNextAnnualLeaveGrantDate.algorithm(require, cacheCarrier, cid, 
					sid, 
					Optional.of(period));
		}
		lstAnnGrantDate.addAll(lstAnnGrantNotDate);
		if(lstAnnGrantDate.isEmpty()) {
			return Optional.empty();
		}		
		//INPUT．指定年月日から一番近い付与日を取得
		GeneralDate nextDay = GeneralDate.today();
		int count = 0;
		lstAnnGrantDate = lstAnnGrantDate.stream().sorted((a,b) -> a.getGrantDate().compareTo(b.getGrantDate())).collect(Collectors.toList());
		for (NextAnnualLeaveGrant a : lstAnnGrantDate) {
			count += 1;
			if(a.getGrantDate().after(ymd)) {
				nextDay = a.getGrantDate();
				break;
			}
		}
		//取得した付与日の１つ前を取得
		GeneralDate preDay = employeeInfor.getEntryDate();
		if(count > 1) {
			NextAnnualLeaveGrant preInfor = lstAnnGrantDate.get(count - 2);
			preDay = preInfor.getGrantDate();
		}
		return Optional.of(new DatePeriod(preDay, nextDay.addDays(-1)));
	}
	@Override
	public Optional<DatePeriod> getPeriodAfterOneYear(String cid, String sid, GeneralDate ymd, Integer periodOutput, Optional<DatePeriod> fromTo) {
		//指定した年月日を基準に、前回付与日から次回付与日までの期間を取得
		Optional<DatePeriod> periodOpt = this.getPeriodYMDGrant(cid, sid, ymd, periodOutput, fromTo);
		if (!periodOpt.isPresent()) return Optional.empty();
		DatePeriod period = periodOpt.get();
		//終了日を開始日の１年後に更新
		DatePeriod result = new DatePeriod(period.start(), period.start().addYears(1).addDays(-1));
		//更新した期間を返す
		return Optional.of(result);
	}
}
