package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.attendanceitem.util.AttendanceItemConvertFactory;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriodRepository;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfMonthly;
import nts.uk.ctx.at.record.dom.standardtime.AgreementMonthSetting;
import nts.uk.ctx.at.record.dom.standardtime.AgreementUnitSetting;
import nts.uk.ctx.at.record.dom.standardtime.AgreementYearSetting;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmOneYear;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorOneYear;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementDomainService;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementMonthSettingRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementUnitSettingRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementYearSettingRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.BasicAgreementSettingRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@Stateless
public class Checking36AgreementConditionImpl implements Checking36AgreementCondition{

	@Inject
	private AgreementUnitSettingRepository agreementUnitSettingRepo;
	
	@Inject
	private AgreementDomainService agreementDomainService;
	
	@Inject
	private WorkingConditionItemRepository workingConditionItem;
	
	@Inject
	private AgreementYearSettingRepository agreementYearSettingRepo;
	
	@Inject
	private BasicAgreementSettingRepository basicAgreementSettingRepo;
	
	@Inject 
	private AgreementMonthSettingRepository agreementMonthSettingRepo;
	
	@Inject
	private AgreementTimeOfManagePeriodRepository agreementTimeOfManagePeriodRepo;
	
	//
	@Inject
	private AttendanceTimeOfMonthlyRepository attdTimeOfMonthlyRepo;
	
	@Inject
	private AttendanceItemConvertFactory attendanceItemConvertFactory;
	
	@Inject
	private AttendanceTimeOfMonthlyRepository attendanceTimeOfMonthlyRepo;

	
	@Override
	public Check36AgreementValue check36AgreementCondition(String employeeId,YearMonth yearMonth,int closureID,ClosureDate closureDate,AgreementCheckCon36 agreementCheckCon36) {
		
		Check36AgreementValue check36AgreementCon = new Check36AgreementValue(false,0,0);
		//実績データに埋まっている「36協定エラー状態」を取得する : 202,203,204,205,206
		//
		Optional<AttendanceTimeOfMonthly> attdTimeOfMonthly = attendanceTimeOfMonthlyRepo.find(employeeId,yearMonth,
				EnumAdaptor.valueOf(closureID, ClosureId.class),
				closureDate);
		if(!attdTimeOfMonthly.isPresent()) {
			return check36AgreementCon;
		}
		
		AgreementTimeOfMonthly agreementTimeOfMonthly = attdTimeOfMonthly.get().getMonthlyCalculation().getAgreementTime();
		int valueError = 0;
		int valueAlarm = 0;
		
		//エラー時間( ID:204 or ID:206)　-　36協定エラーアラーム前時間
		if(agreementCheckCon36.getClassification() == ErrorAlarmRecord.ERROR) {
			if(agreementTimeOfMonthly.getExceptionLimitErrorTime().isPresent()) {
				if(agreementTimeOfMonthly.getLimitErrorTime().v()<agreementTimeOfMonthly.getExceptionLimitErrorTime().get().v()) {
					valueError = agreementTimeOfMonthly.getExceptionLimitErrorTime().get().v() - agreementCheckCon36.getEralBeforeTime().intValue();
				}else {
					valueError = agreementTimeOfMonthly.getLimitErrorTime().v() - agreementCheckCon36.getEralBeforeTime().intValue() ;
				}
			}else {
				valueError = agreementTimeOfMonthly.getLimitErrorTime().v() - agreementCheckCon36.getEralBeforeTime().intValue() ;
			}
		//アラーム時間(ID:203 or ID:205)　-　36協定エラーアラーム前時間
		}else {
			if(agreementTimeOfMonthly.getExceptionLimitAlarmTime().isPresent()) {
				if(agreementTimeOfMonthly.getLimitAlarmTime().v()<agreementTimeOfMonthly.getExceptionLimitAlarmTime().get().v()) {
					valueAlarm = agreementTimeOfMonthly.getExceptionLimitAlarmTime().get().v() - agreementCheckCon36.getEralBeforeTime().intValue() ;
				}else {
					valueAlarm = agreementTimeOfMonthly.getLimitAlarmTime().v() - agreementCheckCon36.getEralBeforeTime().intValue() ;
				}
			}else {
				valueAlarm = agreementTimeOfMonthly.getLimitAlarmTime().v() - agreementCheckCon36.getEralBeforeTime().intValue() ;
			}
			
		}
		
		//agreementTime : 202
		int valueAgreementTime = agreementTimeOfMonthly.getAgreementTime().v();
		boolean check= false;
		//error
		if(agreementCheckCon36.getClassification() == ErrorAlarmRecord.ERROR) {
			check = compareOperator(agreementCheckCon36.getCompareOperator().value,valueAgreementTime,valueError);
		}else {//alarm
			check = compareOperator(agreementCheckCon36.getCompareOperator().value,valueAgreementTime,valueAlarm);
		}
		check36AgreementCon.setCheck36AgreementCon(check);
		check36AgreementCon.setErrorValue(valueError);
		check36AgreementCon.setAlarmValue(valueAlarm);
		
	//		//1名の36上限時間を取得
	//		BasicAgreementSetting basicAgreementSet = this.getBasicAgreementSet(companyId, employeeId, date, yearMonth, year);
	//		
	//		//取得できない　or　エラー時間、アラーム時間=null
	//		if(basicAgreementSet==null) {
	//			return false;
	//		}
	//		//「36協定チェック条件」を取得する : input agreementCheckCon36 
	//		//チェック値を計算する
	//		int value = 0;
	//		if(agreementCheckCon36.getClassification() == ErrorAlarmRecord.ALARM ) {
	//			value = basicAgreementSet.getAlarmOneMonth().v() - agreementCheckCon36.getEralBeforeTime().intValue();
	//		}else if(agreementCheckCon36.getClassification() == ErrorAlarmRecord.ERROR) {
	//			value = basicAgreementSet.getErrorOneMonth().v() - agreementCheckCon36.getEralBeforeTime().intValue();
	//		}
	//		
	//		//月別実績を取得するa
	//		//get closureId and closureDate
	//		ClosureId closureId = EnumAdaptor.valueOf(1, ClosureId.class);
	//		
	//		List<AttendanceTimeOfMonthly> attdTime =  attdTimeOfMonthlyRepo.findByYMAndClosureIdOrderByStartYmd(employeeId, yearMonth, closureId);
	//		MonthlyRecordToAttendanceItemConverter monthlyRecord = attendanceItemConvertFactory.createMonthlyConverter();
	//		Optional<ItemValue> itemValue = monthlyRecord.withAttendanceTime(attdTime.get(0)).convert(202);
			
		return check36AgreementCon;
	}
	
	private boolean compareOperator(int valueAgreementTime,int value,int compareType) {
		boolean check = false;
		switch(compareType) {
		case 0 :/* 等しい（＝） */
			if(valueAgreementTime == value)
				check = true;
			break; 
		case 1 :/* 等しくない（≠） */
			if(valueAgreementTime != value)
				check = true;
			break; 
		case 2 :/* より大きい（＞） */
			if(valueAgreementTime > value)
				check = true;
			break;
		case 3 :/* 以上（≧） */
			if(valueAgreementTime >= value)
				check = true;
			break;
		case 4 :/* より小さい（＜） */
			if(valueAgreementTime < value)
				check = true;
			break;
		default :/* 以下（≦） */
			if(valueAgreementTime <= value)
				check = true;
			break; 
		}
		
		return check;
	}
	
//	//36協定実績(Work)の作成
//	private AgreementActualOutput creatAgreementRecord(String employeeId, nts.uk.ctx.at.shared.dom.common.Year year){
//		//ドメインモデル「管理期間の36協定時間」を取得
//		List<AgreementTimeOfManagePeriod> lstAgreementTimeOfManagePeriod = this.agreementTimeOfManagePeriodRepo.findByYearOrderByYearMonth(employeeId,year);
//		return new AgreementActualOutput(lstAgreementTimeOfManagePeriod,year,employeeId);
//	}
//	
	//1名の36上限時間を取得
	public BasicAgreementSetting getBasicAgreementSet(String companyId,String employeeId,GeneralDate date,YearMonth yearMonth, Year year ){
		return this.onePersonGet36MaxTime(companyId,employeeId, date, yearMonth, year);
	}
	//関数アルゴリズム「1名の36上限時間を取得」を実行する
	public BasicAgreementSetting onePersonGet36MaxTime(String companyId,String employeeId,GeneralDate date,YearMonth yearMonth, Year year){
		//ドメインモデル「３６協定単位設定」を取得する
		Optional<AgreementUnitSetting> optAgreementUnitSetting = this.agreementUnitSettingRepo.find(companyId);
		if(optAgreementUnitSetting.isPresent()){
//			AgreementUnitSetting agreementUnitSetting = optAgreementUnitSetting.get();
			//ドメインモデル「労働契約履歴」を取得する fixed -> 労働条件項目
			val workingConditionItemOpt =
					this.workingConditionItem.getBySidAndStandardDate(employeeId, date);
			if (workingConditionItemOpt.isPresent()){
				val workingSystem = workingConditionItemOpt.get().getLaborSystem();
				//36協定基本設定を取得する
				BasicAgreementSetting basicSet = this.agreementDomainService.getBasicSet(companyId, employeeId, date, workingSystem);
				
				this.acquire36AgreementExceptionSetting(companyId, employeeId, date, yearMonth, year, basicSet);
				return basicSet;
			}
		}
		return null;
	}
	@Override
	public void acquire36AgreementExceptionSetting(String companyId, String employeeId, GeneralDate criteriaDate,
			YearMonth yearMonth, Year year,BasicAgreementSetting basicSet) {
		
		if(year!=null){
			//ドメインモデル「36協定年度設定」を取得する
			List<AgreementYearSetting> lstAgreementYearSetting = this.agreementYearSettingRepo.find(employeeId);
			
			AgreementYearSetting newAgreementYearSetting = null;
			boolean isExist = false;
			int size = lstAgreementYearSetting.size();
			for (int i = 0; i < size; i++) {
				AgreementYearSetting agreementYearSetting = lstAgreementYearSetting.get(i);
				if(agreementYearSetting.getYearValue()==year.v().intValue()){
					newAgreementYearSetting=agreementYearSetting;
					isExist=true;
					break;
				}
			}
			if(isExist){
				//ドメインモデル「36協定基本設定」を更新する
				basicSet.setAlarmOneYear(new AlarmOneYear(newAgreementYearSetting.getAlarmOneYear().v()));
				basicSet.setErrorOneYear(new ErrorOneYear(newAgreementYearSetting.getErrorOneYear().v()));
				this.basicAgreementSettingRepo.update2(basicSet);
			}
			
			if(yearMonth!=null){
				//ドメインモデル「36協定年月設定」を取得する
				Optional<AgreementMonthSetting> optAgreementMonthSetting = this.agreementMonthSettingRepo.findByKey(employeeId, yearMonth);
				if(optAgreementMonthSetting.isPresent()){
					AgreementMonthSetting agreementMonthSetting = optAgreementMonthSetting.get();
					basicSet.setAlarmOneMonth(agreementMonthSetting.getAlarmOneMonth());
					basicSet.setErrorOneMonth(agreementMonthSetting.getErrorOneMonth());
					this.basicAgreementSettingRepo.update2(basicSet);
				}
			
			}
			
			
		}
		
	}
	
	

}
