package nts.uk.ctx.at.record.pubimp.remainingnumber.annualbreakmanage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordAdapter;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordImport;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnLeaRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.TempAnnualLeaveMngMode;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnualLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AnnualLeaveInfo;
import nts.uk.ctx.at.record.pub.remainingnumber.annualbreakmanage.AnnualBreakManageExport;
import nts.uk.ctx.at.record.pub.remainingnumber.annualbreakmanage.AnnualBreakManagePub;
import nts.uk.ctx.at.record.pub.remainingnumber.annualbreakmanage.YearlyHolidaysTimeRemainingExport;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.GetNextAnnualLeaveGrant;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AnnualBreakManagePubImp implements AnnualBreakManagePub {
	@Inject
	private GetClosureStartForEmployee getClosureStartForEmployee;
	
	@Inject
	private GetAnnLeaRemNumWithinPeriod getAnnLeaRemNumWithinPeriod;
	
	@Inject
	private AnnLeaEmpBasicInfoRepository annLeaEmpBasicInfoRepository;
	
	@Inject
	private EmployeeRecordAdapter pmployeeRecordAdapter;
	
	@Inject
	private GetNextAnnualLeaveGrant getNextAnnualLeaveGrant;
	
	/** 年休付与テーブル設定 */
	@Inject
	private YearHolidayRepository yearHolidayRepo;
	
	@Override
	public List<AnnualBreakManageExport> getEmployeeId(List<String> employeeId, GeneralDate startDate,
			GeneralDate endDate) {
		List<AnnualBreakManageExport> annualBreakManageExport = new ArrayList<>();
		for (String emp : employeeId) {
			List<NextAnnualLeaveGrant> nextAnnualLeaveGrant = calculateNextHolidayGrant(emp, new DatePeriod(startDate, endDate));
			// 「年休付与がある社員IDList」に処理中の社員IDを追加
			if (!nextAnnualLeaveGrant.isEmpty() ) {
				annualBreakManageExport.add(new AnnualBreakManageExport(emp));
			}
		}
		return annualBreakManageExport;
	}

	@Override
	public List<YearlyHolidaysTimeRemainingExport> getYearHolidayTimeAnnualRemaining(String employeeId,
			GeneralDate confirmDay) {
		List<YearlyHolidaysTimeRemainingExport> yearlyHolidaysTimeRemainingExport = new ArrayList<>();
		// 全締めの当月と期間を取得する 
		Optional<GeneralDate> startDate = getClosureStartForEmployee.algorithm(employeeId);
		if (!startDate.isPresent()) {
			return yearlyHolidaysTimeRemainingExport;
		}
		// 指定日年の1/1を計算
		GeneralDate designatedDate = GeneralDate.ymd(startDate.get().year(), 1, 1);
		
		// 計算期間．終了日←パラメータ「指定日」
		GeneralDate endDate = confirmDay;
		// ドメインモデル「年休社員基本情報」を取得
		Optional<AnnualLeaveEmpBasicInfo> annualLeaveEmpBasicInfo = annLeaEmpBasicInfoRepository.get(employeeId);
		if (!annualLeaveEmpBasicInfo.isPresent()) {
			return yearlyHolidaysTimeRemainingExport;
		}
		// 次回年休付与を計算
		List<NextAnnualLeaveGrant> nextAnnualLeaveGrant = calculateNextHolidayGrant(employeeId, new DatePeriod(designatedDate, endDate));
		// ログインしている会社ID　取得
		LoginUserContext loginUserContext = AppContexts.user();
		String companyId = loginUserContext.companyId();
		Optional<AggrResultOfAnnualLeave> prevAnnualLeave = Optional.empty();
		int count = 0;
		for (NextAnnualLeaveGrant elementNextAnnualLeaveGrant : nextAnnualLeaveGrant) {
			// 期間中の年休残数を取得
			Optional<AggrResultOfAnnualLeave> aggrResultOfAnnualLeave = 		
					getAnnLeaRemNumWithinPeriod.
					algorithm(companyId, 
							employeeId, 
							new DatePeriod(startDate.get(), elementNextAnnualLeaveGrant.getGrantDate().addDays(-1)), 
							EnumAdaptor.valueOf(1, TempAnnualLeaveMngMode.class), 
							elementNextAnnualLeaveGrant.getGrantDate().addDays(-1), 
							false, 
							false, 
							null,
							null,
							prevAnnualLeave);
			if (aggrResultOfAnnualLeave.isPresent()){
				// 取得した年休の集計結果．年休情報(付与時点)でループ
				Optional<List<AnnualLeaveInfo>> optListAnnus = aggrResultOfAnnualLeave.get().getAsOfGrant();
				if (optListAnnus.isPresent() && !optListAnnus.get().isEmpty()) {
					for (AnnualLeaveInfo annualLeaveInfoe : optListAnnus.get()) {
						// 「年休の集計結果」で付与された年月日をチェック
						if (startDate.get().beforeOrEquals(annualLeaveInfoe.getYmd()) && endDate.afterOrEquals(annualLeaveInfoe.getYmd())) {
							//  計算期間．開始日<=年休情報．年月日<=計算期間．終了日
							YearlyHolidaysTimeRemainingExport yhtre = 
									new YearlyHolidaysTimeRemainingExport(annualLeaveInfoe.getYmd(), 
											null, 
											annualLeaveInfoe.getRemainingNumber().getAnnualLeaveWithMinus().getRemainingNumber().getTotalRemainingDays().v());
							yearlyHolidaysTimeRemainingExport.add(yhtre );
						}
					}
				}
				// 年休計算開始日←次回年休付与．付与年月日
				startDate = Optional.of(nextAnnualLeaveGrant.get(count++).getGrantDate());
				prevAnnualLeave = aggrResultOfAnnualLeave;
			}
			
			// 期間中の年休残数を取得
			Optional<AggrResultOfAnnualLeave> aggrResultOfAnnualLeavee = 		
					getAnnLeaRemNumWithinPeriod.
					algorithm(companyId, 
							employeeId, 
							new DatePeriod(startDate.get(), elementNextAnnualLeaveGrant.getGrantDate().addDays(-1)), 
							EnumAdaptor.valueOf(1, TempAnnualLeaveMngMode.class), 
							elementNextAnnualLeaveGrant.getGrantDate().addDays(-1), 
							false, 
							false, 
							null,
							null,
							prevAnnualLeave);
			// List<指定日時点の年休残数>の年休残数を全て更新
			for (YearlyHolidaysTimeRemainingExport yyearlyHolidaysTimeRemainingExport : yearlyHolidaysTimeRemainingExport) {
				yyearlyHolidaysTimeRemainingExport.setAnnualRemaining(aggrResultOfAnnualLeavee.get().getAsOfPeriodEnd().getRemainingNumber().getAnnualLeaveWithMinus().getRemainingNumber().getTotalRemainingDays().v());
			}
		}
		
		return yearlyHolidaysTimeRemainingExport;
	}

	@Override
	public List<NextAnnualLeaveGrant> calculateNextHolidayGrant(String employeeId, DatePeriod time) {
		List<NextAnnualLeaveGrant> nextAnnualLeaveGrant = new ArrayList<>();
		// ○Imported(就業)「社員」を取得する
		EmployeeRecordImport employeeRecordImport = pmployeeRecordAdapter.getPersonInfor(employeeId);
		// ○パラメータ「期間」をチェック
			Optional<GeneralDate> start_date = Optional.empty();
			if (time == null) {
				start_date = getClosureStartForEmployee.algorithm(employeeId);
			}
			
			// ドメインモデル「年休付与テーブル設定」を取得する (Lấy domain 「年休付与テーブル設定」)
			// ログインしている会社ID　取得
			LoginUserContext loginUserContext = AppContexts.user();
			String companyId = loginUserContext.companyId();
			Optional<AnnualLeaveEmpBasicInfo> annualLeaveEmpBasicInfo = annLeaEmpBasicInfoRepository.get(employeeId);
			if (!annualLeaveEmpBasicInfo.isPresent()) {
				return nextAnnualLeaveGrant;
			}
			val grantHdTblSetOpt = this.yearHolidayRepo.findByCode(
					companyId, annualLeaveEmpBasicInfo.get().getGrantRule().getGrantTableCode().toString() );
			if (!grantHdTblSetOpt.isPresent()){
				return nextAnnualLeaveGrant;
			}
			val grantHdTblSet = grantHdTblSetOpt.get();
			
			//○次回年休付与を取得する
			nextAnnualLeaveGrant = getNextAnnualLeaveGrant
					.algorithm(companyId, 
							annualLeaveEmpBasicInfo.get().getGrantRule().getGrantTableCode().toString(), 
							employeeRecordImport.getEntryDate(), 
							annualLeaveEmpBasicInfo.get().getGrantRule().getGrantStandardDate(), 
							time == null ? new DatePeriod(start_date.get().addDays(1), GeneralDate.fromString("9999/12/31", "yyyy/mm/dd")) : time, 
							time == null ? true: false);
		return nextAnnualLeaveGrant;
	}
	
}
