package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.io.IOException;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.AttendanceLeavingWorkOfDaily;
import nts.uk.ctx.at.record.dom.daily.WorkInformationOfDaily;
import nts.uk.ctx.at.record.dom.daily.WorkInformationOfDailyRepository;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.EmploymentContractHistoryAdopter;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.WorkingSystem;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.GetOfStatutoryWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeRepository;
import nts.uk.ctx.at.shared.dom.worktimeset.WorkTimeSetRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class CalculateDailyRecord {
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private EmploymentContractHistoryAdopter employmentContractHistoryAdopter;
	
	@Inject
	private WorkTimeSetRepository workTimeSetRepository;

	@Inject
	private WorkTimeRepository workTimeRepository;
	
	@Inject
	private GetOfStatutoryWorkTime getOfStatutoryWorkTime;
	
	@Inject
	private WorkInformationOfDailyRepository workInformationOfDailyRepository;
	
	
	public IntegrationOfDaily calculate(String companyId, String employeeId, GeneralDate targetDate, IntegrationOfDaily integrationOfDaily) {
		// 実績データの計算
		
		return this.calculateRecord(companyId, employeeId, targetDate, integrationOfDaily);
	}
	

	/**
	 * 実績データの計算
	 * @param companyId 会社コード
	 * @param employeeId 社員コード
	 * @param targetDate 対象日
	 * @param integrationOfDaily 
	 */
	private IntegrationOfDaily calculateRecord(String companyId, String employeeId, GeneralDate targetDate, IntegrationOfDaily integrationOfDaily) {
		/*日別実績(Work)の退避*/
		val copyIntegrationOfDaily = integrationOfDaily;
		/*1日の計算範囲クラスを作成*/
		val oneRange = createOneDayRange(companyId,employeeId,targetDate,integrationOfDaily);
		/*勤務種類の取得*/
		val workInfo = integrationOfDaily.getWorkInformation();
		val workType = this.workTypeRepository.findByPK(companyId, workInfo.getRecordWorkInformation().getWorkTypeCode().v())
				.get(); // 要確認：勤務種類マスタが削除されている場合は考慮しない？
		
		/*就業時間帯勤務区分*/
		Optional<WorkTime> workTime = workTimeRepository.findByCode(companyId, integrationOfDaily.getWorkInformation().getScheduleWorkInformation().getSiftCode().toString());
		/*労働制*/
		DailyCalculationPersonalInformation workingSystem = getPersonInfomation(employeeId,targetDate);
		if(/*1日休日系ではない(勤務種類ドメインのアルゴリズムの結果*/) {
			val calcRangeOfOneDay = oneRange.decisionWorkClassification(workTime.get().getWorkTimeDivision(), workingSystem);
		}
		else {
			/*1日休暇時の時間帯作成*/
			/*出勤日の時間帯作成*/
			//val calcRangeOfOneDay =　/*現在作業分の対応範囲外のため保留 2017.10.16*/;
		}
		/*時間の計算*/
		integrationOfDaily.calcDailyRecord(calcRangeOfOneDay);
		
		/*手修正、申請範囲された項目を元に戻す(ベトナムが作成している可能性があるため、確認後)*/
		/*日別実績への項目移送*/
		
	}
	
	/**
	 * １日の範囲クラス作成
	 * @param companyId 会社コード
	 * @param employeeId 社員ID
	 * @param targetDate 対象日
	 * @param integrationOfDaily 
	 * @return
	 */
	private CalculationRangeOfOneDay createOneDayRange(String companyId, String employeeId, GeneralDate targetDate,IntegrationOfDaily integrationOfDaily) {
		/*所定時間設定取得*/
		val predetermineTimeSet = workTimeSetRepository.findByCode(companyId, integrationOfDaily.getWorkInformation().getSyainID());
		/*1日の計算範囲取得*/
		val calcRangeOfOneDay = new TimeSpanForCalc(new TimeWithDayAttr(predetermineTimeSet.get().getStartDateClock())
												   ,new TimeWithDayAttr(predetermineTimeSet.get().getStartDateClock() + predetermineTimeSet.get().getRangeTimeDay()));
		
		
		/*ジャストタイムの判断するための設定取得*/
//		boolean justLate        = /*就業時間帯から固定・流動・フレックスの設定を取得してくるロジック*/;
//		boolean justEarlyLeave  = /*就業時間帯から固定・流動・フレックスの設定を取得してくるロジック*/;
//		/*日別実績の出退勤時刻セット*/
//		AttendanceLeavingWorkOfDaily attendanceLeavingOfDaily = integrationOfDaily.getAttendanceLeave().calcJustTime(justLate,justEarlyLeave);
		
		/*前日の勤務情報取得  (2017.10.16 VNで詳細設計中のため一時保留)*/
		WorkInformationOfDaily yestarDayWorkInfo = workInformationOfDailyRepository.find(companyId, employeeId, targetDate.addDays(-1)).orElse(workInformationOfDailyRepository.find(companyId, employeeId, targetDate).get());
		/*翌日の勤務情報取得  (2017.10.16 VNで詳細設計中のため一時保留)*/
		WorkInformationOfDaily tomorrowDayWorkInfo = workInformationOfDailyRepository.find(companyId, employeeId, targetDate.addDays(1)).orElse(workInformationOfDailyRepository.find(companyId, employeeId, targetDate).get());
		return new CalculationRangeOfOneDay(/*ここで１日の範囲を作成して返す*/);
	}
	
	/**
	 * 労働制を取得する
	 * @return 日別計算用の個人情報
	 */
	private DailyCalculationPersonalInformation getPersonInfomation(String employeeId, GeneralDate targetDate) {	
		// 労働制
		return new DailyCalculationPersonalInformation(
			this.employmentContractHistoryAdopter.findByEmployeeIdAndBaseDate(employeeId, targetDate)
			,this.getOfStatutoryWorkTime./*フレ、固定、変動に分岐するヘッダーとなるメソッドの呼び出しと、*/);
	}
}
