package nts.uk.ctx.at.record.pubimp.remainingnumber.annualbreakmanage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordAdapter;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordImport;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnLeaRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnualLeave;
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
		boolean isNull = false;
		List<AnnualBreakManageExport> annualBreakManageExport = new ArrayList<>();
		for (String emp : employeeId) {
			// ○Imported(就業)「社員」を取得する
			EmployeeRecordImport employeeRecordImport = pmployeeRecordAdapter.getPersonInfor(emp);
			// ○パラメータ「期間」をチェック
			Optional<GeneralDate> start_date = null;
			if (startDate == null && endDate == null) {
				isNull = true;
				start_date = getClosureStartForEmployee.algorithm(emp);
			}
			
			// ドメインモデル「年休付与テーブル設定」を取得する (Lấy domain 「年休付与テーブル設定」)
			// ログインしている会社ID　取得
			LoginUserContext loginUserContext = AppContexts.user();
			String companyId = loginUserContext.companyId();
			Optional<AnnualLeaveEmpBasicInfo> annualLeaveEmpBasicInfo = annLeaEmpBasicInfoRepository.get(emp);
			if (!annualLeaveEmpBasicInfo.isPresent()) {
				return annualBreakManageExport;
			}
			val grantHdTblSetOpt = this.yearHolidayRepo.findByCode(
					companyId, annualLeaveEmpBasicInfo.get().getGrantRule().getGrantTableCode().toString() );
			if (!grantHdTblSetOpt.isPresent()){
				return annualBreakManageExport;
			}
			val grantHdTblSet = grantHdTblSetOpt.get();
			
			List<NextAnnualLeaveGrant> nextAnnualLeaveGrant = getNextAnnualLeaveGrant
					.algorithm(companyId, 
							annualLeaveEmpBasicInfo.get().getGrantRule().getGrantTableCode().toString(), 
							employeeRecordImport.getEntryDate(), 
							annualLeaveEmpBasicInfo.get().getGrantRule().getGrantStandardDate(), 
							isNull == true ? new DatePeriod(start_date.get().addDays(1), GeneralDate.fromString("9999/12/31", "yyyy/mm/dd")) : new DatePeriod(startDate, endDate), 
							isNull == true ? true: false);
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
		GeneralDate enDate = designatedDate;
		// ドメインモデル「年休社員基本情報」を取得
		Optional<AnnualLeaveEmpBasicInfo> annualLeaveEmpBasicInfo = annLeaEmpBasicInfoRepository.get(employeeId);
		if (!annualLeaveEmpBasicInfo.isPresent()) {
			return yearlyHolidaysTimeRemainingExport;
		}
		// TODO 次回年休付与を計算
		// 期間中の年休残数を取得
		// ログインしている会社ID　取得
		LoginUserContext loginUserContext = AppContexts.user();
		String companyId = loginUserContext.companyId();
		
		
		return yearlyHolidaysTimeRemainingExport;
	}
	
}
