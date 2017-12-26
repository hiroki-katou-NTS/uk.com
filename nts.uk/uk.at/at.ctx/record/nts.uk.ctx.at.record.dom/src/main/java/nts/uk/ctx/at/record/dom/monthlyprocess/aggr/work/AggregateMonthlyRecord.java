package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.EmploymentContractHistory;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.EmploymentContractHistoryAdopter;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * ドメインサービス：月別実績を集計する
 * @author shuichi_ishida
 */
@Stateless
public class AggregateMonthlyRecord implements AggregateMonthlyRecordService {

	/** 労働契約履歴 */
//	@Inject
//	private EmploymentContractHistoryAdopter employmentContractHistoryAdopter;
	
	/** 社員 */
	@Inject
	private EmpEmployeeAdapter empEmployeeAdapter;
	
	/**
	 * 集計処理
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param datePeriod 期間
	 * @return 集計結果
	 */
	public AggregateMonthlyRecordValue aggregate(String companyId, String employeeId, YearMonth yearMonth, DatePeriod datePeriod){
		
		return this.aggregateProcess(companyId, employeeId, yearMonth, datePeriod);
	}
	
	/**
	 * 集計処理　（アルゴリズム）
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param datePeriod 期間
	 * @return 集計結果
	 */
	private AggregateMonthlyRecordValue aggregateProcess(String companyId, String employeeId, YearMonth yearMonth, DatePeriod datePeriod){
		
		// 戻り値初期化
		AggregateMonthlyRecordValue value = new AggregateMonthlyRecordValue();
		
		// ドメインモデル「労働契約履歴．労働制」を取得する
		//*****（未）　期間で取れるメソッドが必要
		//List<EmploymentContractHistory> employmentContracts =
		//		this.employmentContractHistoryAdopter.findByEmployeeIdAndDatePeriod(employeeID, datePeriod);
		//*****（未）　固定勤務で取れた事に
		List<EmploymentContractHistory> employmentContracts = new ArrayList<>();
		employmentContracts.add(new EmploymentContractHistory(employeeId, WorkingSystem.RegularWork));
		DatePeriod term = new DatePeriod(GeneralDate.ymd(2002, 6, 10), GeneralDate.ymd(2017, 11, 12));
		//*****（未）　締日もここで取れるはず？
		ClosureId closureId = ClosureId.RegularEmployee;
		ClosureDate closureDate = new ClosureDate(0, true);
		
		// 履歴の数だけループ
		for (EmploymentContractHistory employmentContract : employmentContracts){
			
			// 処理期間を計算　（処理期間と契約履歴の重複を確認する）
			DatePeriod procPeriod = this.confirmProcPeriod(datePeriod, term);
			if (procPeriod == null) {
				// 履歴の期間と重複がない時
				continue;
			}
			
			// 入社前、退職後を期間から除く
			procPeriod = this.confirmProcPeriodInOffice(procPeriod, employeeId);
			if (procPeriod == null) {
				// 処理期間全体が、入社前または退職後の時
				continue;
			}
			
			// 月別実績の勤怠時間　初期データ作成
			AttendanceTimeOfMonthly attendanceTime = new AttendanceTimeOfMonthly(employeeId, yearMonth, closureId, closureDate);
			
			// 月の計算
			attendanceTime.aggregate(companyId, employmentContract.getWorkingSystem());
			
			// 縦計
			
			// 時間外超過

			// 計算結果を戻り値に蓄積
			value.addAttendanceTime(attendanceTime);
		}
		
		return value;
	}
	
	/**
	 * 処理期間との重複を確認する　（重複期間を取り出す）
	 * @param target 処理期間
	 * @param comparison 比較対象期間
	 * @return 重複期間　（null = 重複なし）
	 */
	private DatePeriod confirmProcPeriod(DatePeriod target, DatePeriod comparison){

		DatePeriod overlap = null;		// 重複期間
		
		// 開始前
		if (target.isBefore(comparison)) return overlap;
		
		// 終了後
		if (target.isAfter(comparison)) return overlap;
		
		// 重複あり
		overlap = target;
		
		// 開始日より前を除外
		if (overlap.contains(comparison.start())){
			overlap = overlap.cutOffWithNewStart(comparison.start());
		}
		
		// 終了日より後を除外
		if (overlap.contains(comparison.end())){
			overlap = overlap.cutOffWithNewEnd(comparison.end());
		}

		return overlap;
	}
	
	/**
	 * 期間に入退職があるか確認する　（処理期間の内、入社～退職の期間と重複する期間を取り出す）
	 * @param target 処理期間
	 * @param employeeID 社員ID
	 * @return 重複期間　（null = 重複なし）
	 */
	private DatePeriod confirmProcPeriodInOffice(DatePeriod target, String employeeID){
		
		// 社員を取得する
		EmployeeImport employee = null;
		employee = this.empEmployeeAdapter.findByEmpId(employeeID);
		if (employee == null){
			return target;
		}
		//*****（未）　仮データ
		/*
		employee = new EmployeeImport(GeneralDate.ymd(2001, 4, 1),
				"TESTEMP", "TESTEMPCD", new MailAddress("test@mail.jp"), "TESTNAME",
				GeneralDate.ymd(2017, 12, 15));
		*/
		DatePeriod term = new DatePeriod(employee.getEntryDate(), employee.getRetiredDate());	// 在職期間
		
		// 入社前・退職後を除外
		return this.confirmProcPeriod(target, term);
	}
}
