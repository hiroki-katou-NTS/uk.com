package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.CalcNextAnnualLeaveGrantDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.RepositoriesRequiredByRemNum;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;
import nts.arc.time.calendar.period.DatePeriod;
@Stateless
public class GetPeriodFromPreviousToNextGrantDateImpl implements GetPeriodFromPreviousToNextGrantDate{
	@Inject
	private ClosureService closureService;
	@Inject
	private AnnLeaEmpBasicInfoRepository annLeaEmpBasicInfoRepository;
	@Inject
	private CalcNextAnnualLeaveGrantDate calcNextAnnGrantDate;
	@Inject
	private EmpEmployeeAdapter empEmployee;
	@Override
	public Optional<DatePeriod> getPeriodGrantDate(String cid, String sid, YearMonth ym, GeneralDate ymd) {
		// 社員に対応する処理締めを取得する
		Closure closureInfor = closureService.getClosureDataByEmployee(sid, ymd);
		if(closureInfor == null) {
			return Optional.empty();
		}
		//指定した年月の期間を算出する
		DatePeriod datePeriodClosure = closureService.getClosurePeriod(closureInfor.getClosureId().value, ym);
		//指定した年月日を基準に、前回付与日から次回付与日までの期間を取得
		 Optional<DatePeriod> periodGrant = this.getPeriodYMDGrant(cid, sid, datePeriodClosure.start().addDays(1));
		return periodGrant;
	}
	@Override
	public Optional<DatePeriod> getPeriodYMDGrant(String cid, String sid, GeneralDate ymd) {
		Optional<RepositoriesRequiredByRemNum> repositoriesRequiredByRemNumOpt
			= Optional.of(new RepositoriesRequiredByRemNum()); // ooooo
		
		//ドメインモデル「年休社員基本情報」を取得する
		Optional<AnnualLeaveEmpBasicInfo> annualLeaveEmpBasicInfoOpt = annLeaEmpBasicInfoRepository.get(sid);
		if(!annualLeaveEmpBasicInfoOpt.isPresent()) {
			return Optional.empty();
		}
		EmployeeImport employeeInfor = empEmployee.findByEmpId(sid);
		//次回年休付与を計算
		List<NextAnnualLeaveGrant> lstAnnGrantNotDate = calcNextAnnGrantDate.algorithm(
				repositoriesRequiredByRemNumOpt, cid, sid, Optional.empty());
		List<NextAnnualLeaveGrant> lstAnnGrantDate = new ArrayList<>();
		if(!lstAnnGrantNotDate.isEmpty()) {
			DatePeriod period = new DatePeriod(employeeInfor.getEntryDate(), lstAnnGrantNotDate.get(0).getGrantDate().addYears(1));
			//入社年月日～次回年休付与日までの年休付与日を全て取得
			lstAnnGrantDate = calcNextAnnGrantDate.algorithm(
					repositoriesRequiredByRemNumOpt, 
					cid, 
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
	public Optional<DatePeriod> getPeriodAfterOneYear(String cid, String sid, GeneralDate ymd) {
		//指定した年月日を基準に、前回付与日から次回付与日までの期間を取得
		Optional<DatePeriod> periodOpt = this.getPeriodYMDGrant(cid, sid, ymd);
		if (!periodOpt.isPresent()) return Optional.empty();
		DatePeriod period = periodOpt.get();
		//終了日を開始日の１年後に更新
		DatePeriod result = new DatePeriod(period.start(), period.start().addYears(1).addDays(-1));
		//更新した期間を返す
		return Optional.of(result);
	}
}
