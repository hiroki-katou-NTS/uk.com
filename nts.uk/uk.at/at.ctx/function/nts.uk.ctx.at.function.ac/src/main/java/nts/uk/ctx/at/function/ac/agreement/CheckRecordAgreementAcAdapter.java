package nts.uk.ctx.at.function.ac.agreement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.adapter.agreement.CheckRecordAgreementAdapter;
import nts.uk.ctx.at.function.dom.adapter.agreement.CheckedAgreementImport;
import nts.uk.ctx.at.function.dom.adapter.agreement.CheckedAgreementResult;
import nts.uk.ctx.at.function.dom.adapter.agreement.CheckedOvertimeImport;
import nts.uk.ctx.at.function.dom.adapter.employment.EmploymentAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreementTimeByPeriodAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreementTimeByPeriodImport;
import nts.uk.ctx.at.function.dom.adapter.standardtime.AgreementOperationSettingImport;
import nts.uk.ctx.at.function.dom.adapter.standardtime.StartingMonthTypeImport;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeCondOt;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeConditionError;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.ErrorAlarm;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.Period;
import nts.uk.ctx.at.record.pub.monthly.agreement.AgreementTimeByPeriod;
import nts.uk.ctx.at.record.pub.monthly.agreement.AgreementTimeByPeriodPub;
import nts.uk.ctx.at.record.pub.monthly.agreement.AgreementTimeOfManagePeriodPub;
import nts.uk.ctx.at.record.pub.monthlyprocess.agreement.GetAgreementTimePub;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.PeriodAtrOfAgreement;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CheckRecordAgreementAcAdapter implements CheckRecordAgreementAdapter {

	@Inject
	private EmploymentAdapter employmentAdapter;

	@Inject
	private ClosureEmploymentRepository closureEmpRepo;

	@Inject
	private GetAgreementTimePub agreementTimePub;

	@Inject
	private AgreementTimeOfManagePeriodPub agreementTimeOfManagePub;

	@Inject
	private AgreementTimeByPeriodPub agreementTimeByPeriodPub;

	@Override
	public List<CheckedAgreementImport> checkArgreement(List<String> employeeIds, List<DatePeriod> periods,
			List<AgreeConditionError> listCondErrorAlarm) {

		List<CheckedAgreementImport> result = new ArrayList<CheckedAgreementImport>();

		if (listCondErrorAlarm.isEmpty() || employeeIds.isEmpty())
			return result;

		List<AgreeConditionError> listCondError = listCondErrorAlarm.stream()
				.filter(e -> e.getErrorAlarm() == ErrorAlarm.Error).collect(Collectors.toList());
		List<AgreeConditionError> listCondAlarm = listCondErrorAlarm.stream()
				.filter(e -> e.getErrorAlarm() == ErrorAlarm.Alarm).collect(Collectors.toList());

		for (DatePeriod period : periods) {
			String employmentCode = employmentAdapter.getClosure(AppContexts.user().employeeId(), period.end());

			Optional<ClosureEmployment> closureEmploymentOpt = closureEmpRepo
					.findByEmploymentCD(AppContexts.user().companyId(), employmentCode);
			if (!closureEmploymentOpt.isPresent())
				throw new RuntimeException(" Clousure not find!");
			if (closureEmploymentOpt.get().getClosureId() == null)
				throw new RuntimeException("Closure is null!");
			Integer closureId = closureEmploymentOpt.get().getClosureId();

			YearMonth yearMonth = period.end().yearMonth();

			/** TODO: 36協定時間対応により、コメントアウトされた */
//			List<AgreementTimeExport> agreementTimeExport = agreementTimePub.get(AppContexts.user().companyId(),
//					employeeIds, yearMonth, ClosureId.valueOf(closureId));

//			List<AgreementTimeExport> exportError = agreementTimeExport.stream()
//					.filter(e -> e.getConfirmed().isPresent()
//							&& (e.getConfirmed().get().getStatus() == AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR
//									|| e.getConfirmed().get()
//											.getStatus() == AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ERROR))
//					.collect(Collectors.toList());
//
//			List<AgreementTimeExport> exportAlarm = agreementTimeExport.stream()
//					.filter(e -> e.getConfirmed().isPresent()
//							&& e.getConfirmed().get().getStatus() == AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM)
//					.collect(Collectors.toList());

//			for (AgreementTimeExport error : exportError) {
//				for (AgreeConditionError condError : listCondError) {
//					result.add(CheckedAgreementImport.builder().employeeId(error.getEmployeeId()).datePeriod(period)
//							.alarmCheckId(condError.getId()).period(condError.getPeriod()).errorAlarm(ErrorAlarm.Error)
//							.messageDisp(condError.getMessageDisp()).build());
//				}
//			}
//
//			for (AgreementTimeExport alarm : exportAlarm) {
//				for (AgreeConditionError condAlarm : listCondAlarm) {
//					result.add(CheckedAgreementImport.builder().employeeId(alarm.getEmployeeId()).datePeriod(period)
//							.alarmCheckId(condAlarm.getId()).period(condAlarm.getPeriod()).errorAlarm(ErrorAlarm.Alarm)
//							.messageDisp(condAlarm.getMessageDisp()).build());
//				}
//			}
		}

		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<CheckedOvertimeImport> checkNumberOvertime(List<String> employeeIds, List<DatePeriod> periods,
			List<AgreeCondOt> listCondOt) {

		List<CheckedOvertimeImport> result = new ArrayList<CheckedOvertimeImport>();
		for (DatePeriod period : periods) {

			YearMonthPeriod yearMonthPeriod = new YearMonthPeriod(period.start().yearMonth(), period.end().yearMonth());
			/** TODO: 36協定時間対応により、コメントアウトされた */
//			Map<String, Map<YearMonth, AttendanceTimeMonth>> agreementMultipEmp = agreementTimeOfManagePub
//					.getTimeByPeriod(employeeIds, yearMonthPeriod);

//			List<String> employeeIdsError = new ArrayList<>(agreementMultipEmp.keySet());
//			for (String employeeId : employeeIdsError) {
//
//				List<AttendanceTimeMonth> agreementOneEmp = new ArrayList<>(
//						agreementMultipEmp.get(employeeId).values());
//
//				for (AgreeCondOt agreeCond : listCondOt) {
//
//					int count = 0;
//					for (int i = 0; i < agreementOneEmp.size(); i++) {
//						if (agreementOneEmp.get(i).valueAsMinutes() > agreeCond.getOt36().valueAsMinutes())
//							count++;
//					}
//					if (count >= agreeCond.getExcessNum().v()) {
//						result.add(CheckedOvertimeImport.builder().employeeId(employeeId).datePeriod(period)
//								.alarmCheckId(agreeCond.getId()).error(true).no(agreeCond.getNo())
//								.ot36(agreeCond.getOt36()).excessNum(agreeCond.getExcessNum())
//								.messageDisp(agreeCond.getMessageDisp()).countAgreementOneEmp(count).build());
//					}
//
//				}
//
//			}

		}
		return result;
	}
	@Inject
	private AgreementTimeByPeriodAdapter agreementTimeByPeriodAdapter;
	
//	@Inject
//	private CheckAgreementTimeStatusAdapter checkAgreementTimeStatusAdapter;

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<CheckedAgreementResult> checkArgreementResult(List<String> employeeIds, DatePeriod period,
			AgreeConditionError agreeConditionError, Optional<AgreementOperationSettingImport> agreementSetObj,
			List<Closure> closureList,Map<String,Integer> mapEmpIdClosureID,Object objCheckAgreement ) {

		String companyId = AppContexts.user().companyId();
		List<CheckedAgreementResult> checkedAgreementResults = new ArrayList<CheckedAgreementResult>();
		List<Integer> fiscalYears  = new ArrayList<>();
		YearMonthPeriod  yearMonthPeriod = null;
		Map<Integer,DatePeriod> mapClosureIDDatePeriod = new HashMap<>();
		
		if (agreementSetObj.isPresent()) {
			StartingMonthTypeImport startingMonthEnum = agreementSetObj.get().getStartingMonth();
			int startingMonth = startingMonthEnum.value + 1;
			// 期間をチェックする
			if (agreeConditionError.getPeriod() == Period.Yearly) {// 期間＝年間
				if (startingMonthEnum == StartingMonthTypeImport.JANUARY) { // case
																			// 1月
					YearMonth startYM = YearMonth.of(period.start().yearMonth().year(), 1);
					YearMonth endYM = YearMonth.of(period.end().yearMonth().year(), 12);
					yearMonthPeriod = new YearMonthPeriod(startYM, endYM);
				} else { // case # 1月
					YearMonth startYM = YearMonth.of(period.start().yearMonth().year(), startingMonth);
					YearMonth endYM = YearMonth.of(period.start().yearMonth().year() + 1, startingMonth - 1);
					yearMonthPeriod = new YearMonthPeriod(startYM, endYM);
				}
			} else {// 期間＝Nヶ月(1ヶ月, 2ヶ月, 3ヶ月)
				yearMonthPeriod = new YearMonthPeriod(period.start().yearMonth(), period.end().yearMonth());
			}
			// Get map Base date
			for (Closure closure : closureList) {
				List<DatePeriod> lstDatePeriod= closure.getPeriodByYearMonth(yearMonthPeriod.end());
				if(!CollectionUtil.isEmpty(lstDatePeriod)){
					DatePeriod datePeriod = lstDatePeriod.get(lstDatePeriod.size()-1);
					if(!mapClosureIDDatePeriod.containsKey(closure.getClosureId().value))
					mapClosureIDDatePeriod.put(closure.getClosureId().value, datePeriod);
				}
			}
		
			// 開始月と起算月から算出した取得対象年度を追加する
			Integer tagetYear = calculateTagetYear(yearMonthPeriod.start(), startingMonth);
			if (!fiscalYears.contains(tagetYear)) {
				fiscalYears.add(tagetYear);
			}
			// 終了月と起算月から算出した取得対象年度を追加する
			tagetYear = calculateTagetYear(yearMonthPeriod.end(), startingMonth);
			if (!fiscalYears.contains(tagetYear)) {
				fiscalYears.add(tagetYear);
			}
			
			/** TODO: need check period */
//			Object basicSetGetter = agreementTimeByPeriodPub.getCommonSetting(AppContexts.user().companyId(), employeeIds, period);

			/** TODO: 並列処理にしてみる　*/
			// 社員IDの件数分ループ
			for (String empId : employeeIds) {
				if (agreeConditionError.getErrorAlarm() == ErrorAlarm.Upper) {
					if(agreeConditionError.getPeriod() == Period.One_Month ) {
						for(YearMonth ym = yearMonthPeriod.start();ym.lessThanOrEqualTo(yearMonthPeriod.end()) && ym.greaterThanOrEqualTo(yearMonthPeriod.start());ym = ym.addMonths(1)) {
							//指定月36協定上限月間時間の取得(RQ 548 JAPAN)
							/** TODO: 36協定時間対応により、コメントアウトされた */
//							List<AgreMaxTimeOfMonthlyImport> agreMaxTimeOfMonthlyImport = agreementTimeByPeriodAdapter.maxTime(
//									companyId, 
//									empId, 
//									new YearMonthPeriod(ym,ym)); //start = end
//							if(agreMaxTimeOfMonthlyImport.isEmpty())
//								continue;
//							//36協定上限時間の状態チェック(RQ 540 JAPAN)
//							AgreMaxTimeStatusOfMonthly agreMaxTimeStatusOfMonthly =  checkAgreementTimeStatusAdapter.maxTime(
//									agreMaxTimeOfMonthlyImport.get(0).getAgreementTime(),
//									agreMaxTimeOfMonthlyImport.get(0).getMaxTime(), 
//									Optional.empty());
							//指定期間36協定時間を生成する
//							AgreementTimeByPeriodImport agreementTimeByPeriodImport = convertToAgreMaxTimeOfMonthly(agreMaxTimeOfMonthlyImport.get(0).getAgreementTime().v(), ym, ym);
							//36協定チェック結果を生成する
//							checkedAgreementResults.add(
//									CheckedAgreementResult.builder()
//									.checkResult(agreMaxTimeStatusOfMonthly==AgreMaxTimeStatusOfMonthly.NORMAL?false:true)
//									.upperLimit(agreMaxTimeOfMonthlyImport.get(0).getMaxTime().toString())
//									.agreementTimeByPeriod(agreementTimeByPeriodImport)
//									.empId(empId)
//									.errorAlarm(agreeConditionError.getErrorAlarm())
//									.build());
						}
					}else if( agreeConditionError.getPeriod() == Period.Months_Average) {
						int day = agreementSetObj.get().getClosingDateType().value+1;
						if(yearMonthPeriod.start().lastDateInMonth() <=agreementSetObj.get().getClosingDateType().value+1 ) {
							day = yearMonthPeriod.start().lastDateInMonth();
						}
							
						GeneralDate baseDate = GeneralDate.ymd(yearMonthPeriod.start().year(), yearMonthPeriod.start().month(), day);
						//指定期間36協定上限複数月平均時間の取得(RQ 547 JAPAN)
						Optional<AgreMaxAverageTimeMulti> agreMaxAverageTimeMulti =agreementTimeByPeriodAdapter.maxAverageTimeMulti(
								companyId, 
								empId,
								baseDate, 
								yearMonthPeriod.start());
						if(!agreMaxAverageTimeMulti.isPresent())
							continue;
						//36協定上限複数月平均時間の状態チェック(RQ 543 JAPAN)
						/** TODO: 36協定時間対応により、コメントアウトされた */
//						AgreMaxAverageTimeMulti data = checkAgreementTimeStatusAdapter.maxAverageTimeMulti(
//								companyId, agreMaxAverageTimeMulti.get(), Optional.empty(), Optional.empty());
//						
//						for(AgreMaxAverageTime agr : data.getAverageTimeList()) {
//							//指定期間36協定時間を生成する
//							AgreementTimeByPeriodImport agreementTimeByPeriodImport = convertToAgreMaxTimeOfMonthly(agr.getAverageTime().v(), agr.getPeriod().start(), agr.getPeriod().end());
//							//36協定チェック結果を生成する
//							checkedAgreementResults.add(
//									CheckedAgreementResult.builder()
//									.checkResult(agr.getStatus() == AgreMaxTimeStatusOfMonthly.NORMAL ? false : true)
//									.upperLimit(data.getMaxTime().toString())
//									.agreementTimeByPeriod(agreementTimeByPeriodImport)
//									.empId(empId)
//									.errorAlarm(agreeConditionError.getErrorAlarm())
//									.build());
//						}
					}
					continue;
				}
				
				List<AgreementTimeByPeriod> lstAgreementTimeByPeriod = new ArrayList<>();
				
				Integer closureIdCheck = mapEmpIdClosureID.get(empId);
				//Get base date 
				DatePeriod baseDate = mapClosureIDDatePeriod.get(closureIdCheck);
				if(baseDate ==null) continue;
				for (Integer fiscalYear : fiscalYears) {
					PeriodAtrOfAgreement periodAtr = mapPeriodWithPeriodAtrOfAgreement(agreeConditionError.getPeriod());
					if (periodAtr != null) {
						//RequestList No.453 指定期間36協定時間の取得
						/** TODO: 36協定時間対応により、コメントアウトされた */
//						List<AgreementTimeByPeriod> agreementTimeByPeriods = agreementTimeByPeriodPub.algorithm(
//								agreeConditionError.getCompanyId(), empId, baseDate.end(),
//								new Month(startingMonth), new Year(fiscalYear), periodAtr, objCheckAgreement);
//						if(!CollectionUtil.isEmpty(agreementTimeByPeriods)){
//							for (AgreementTimeByPeriod agreementTimeByPeriod : agreementTimeByPeriods) {
//								int checkEnd = agreementTimeByPeriod.getEndMonth().compareTo(yearMonthPeriod.start());
//								int checkStart = agreementTimeByPeriod.getStartMonth().compareTo(yearMonthPeriod.end());
//								if(checkStart <= 0 && checkEnd >= 0 ){
//									lstAgreementTimeByPeriod.add(agreementTimeByPeriod);
//								}
//							}
//						}
					}
				}
				// Check for list Error
				for (AgreementTimeByPeriod agreementTimeByPeriod : lstAgreementTimeByPeriod) {
					String upperLimit = "";
					String exceptionLimitAlarmTime = agreementTimeByPeriod.getExceptionLimitAlarmTime().isPresent() ? agreementTimeByPeriod.getExceptionLimitAlarmTime().get().toString() : "";
					String exceptionLimitErrorTime = agreementTimeByPeriod.getExceptionLimitErrorTime().isPresent() ? agreementTimeByPeriod.getExceptionLimitErrorTime().get().toString() : "";
					// Convert AgreementTimeByPeriod to AgreementTimeByPeriodImport
					AgreementTimeByPeriodImport agreementTimeByPeriodImport = new AgreementTimeByPeriodImport(
							agreementTimeByPeriod.getStartMonth(), agreementTimeByPeriod.getEndMonth(), agreementTimeByPeriod.getAgreementTime(),
							agreementTimeByPeriod.getLimitErrorTime().toString(), agreementTimeByPeriod.getLimitAlarmTime().toString(),
							exceptionLimitErrorTime,exceptionLimitAlarmTime, agreementTimeByPeriod.getStatus());
					
					AgreementTimeStatusOfMonthly checkLimitTime = agreementTimeByPeriod.getStatus();
					switch (checkLimitTime) {
					case EXCESS_LIMIT_ERROR:
						if (agreeConditionError.getErrorAlarm() == ErrorAlarm.Error) {
							upperLimit = agreementTimeByPeriod.getLimitErrorTime().toString();
							// All 36協定チェック結果 to list return
							checkedAgreementResults.add(CheckedAgreementResult.builder().checkResult(true).upperLimit(upperLimit)
									.agreementTimeByPeriod(agreementTimeByPeriodImport).empId(empId).errorAlarm(agreeConditionError.getErrorAlarm()).build());
						}
						break;
					case EXCESS_LIMIT_ALARM:
						if (agreeConditionError.getErrorAlarm() == ErrorAlarm.Alarm) {
							upperLimit = agreementTimeByPeriod.getLimitAlarmTime().toString();
							// All 36協定チェック結果 to list return
							checkedAgreementResults.add(CheckedAgreementResult.builder().checkResult(true).upperLimit(upperLimit)
									.agreementTimeByPeriod(agreementTimeByPeriodImport).empId(empId).errorAlarm(agreeConditionError.getErrorAlarm()).build());
						}
						break;
					case EXCESS_EXCEPTION_LIMIT_ALARM:
						if (agreeConditionError.getErrorAlarm() == ErrorAlarm.Alarm) {
							upperLimit = agreementTimeByPeriod.getExceptionLimitAlarmTime().isPresent() ? agreementTimeByPeriod.getExceptionLimitAlarmTime().get().toString() : "";
							// All 36協定チェック結果 to list return
							checkedAgreementResults.add(CheckedAgreementResult.builder().checkResult(true).upperLimit(upperLimit)
									.agreementTimeByPeriod(agreementTimeByPeriodImport).empId(empId).errorAlarm(agreeConditionError.getErrorAlarm()).build());
						}
						break;
					case EXCESS_EXCEPTION_LIMIT_ERROR:
						if (agreeConditionError.getErrorAlarm() == ErrorAlarm.Error) {
							upperLimit = agreementTimeByPeriod.getExceptionLimitErrorTime().isPresent() ? agreementTimeByPeriod.getExceptionLimitErrorTime().get().toString() : "";

							// All 36協定チェック結果 to list return
							checkedAgreementResults.add(CheckedAgreementResult.builder().checkResult(true).upperLimit(upperLimit)
									.agreementTimeByPeriod(agreementTimeByPeriodImport).empId(empId).errorAlarm(agreeConditionError.getErrorAlarm()).build());
						}

						break;
					default:
						break;
					}
				}
			}
		}
		
		return checkedAgreementResults;
	}
	
	private AgreementTimeByPeriodImport convertToAgreMaxTimeOfMonthly(Integer time,YearMonth start,YearMonth end) {
		return new AgreementTimeByPeriodImport(
			 	start, 
				end, 
				new AttendanceTimeYear(time),
				"0",
				"0", 
				"",  
				"", 
				AgreementTimeStatusOfMonthly.NORMAL);
	}

	
	private Integer calculateTagetYear(YearMonth tagetYM, int startingMonth) {

		Integer tagetYear = tagetYM.year();
		// 対象年月.月＞起算月
		if (tagetYM.month() < startingMonth) {
			tagetYear = tagetYM.year() - 1;
		}
		return tagetYear;
	}

	/**
	 * Maping Enum Period with enum PeriodAtrOfAgreement
	 * 
	 * @param Period
	 * @return PeriodAtrOfAgreement
	 */
	private PeriodAtrOfAgreement mapPeriodWithPeriodAtrOfAgreement(Period period) {
		PeriodAtrOfAgreement enumReturn = null;
		switch (period) {
		case One_Month:
			enumReturn = PeriodAtrOfAgreement.ONE_MONTH;
			break;
		case Two_Month:
			enumReturn = PeriodAtrOfAgreement.TWO_MONTHS;
			break;
		case Three_Month:
			enumReturn = PeriodAtrOfAgreement.THREE_MONTHS;
			break;
		case Yearly:
			enumReturn = PeriodAtrOfAgreement.ONE_YEAR;
			break;
		case Months_Average:
			enumReturn = PeriodAtrOfAgreement.Months_Average;
			break;
		default:
			break;
		}
		return enumReturn;
	}

	@Override
	public Object getCommonSetting(String companyId, List<String> employeeIds, DatePeriod period) {
		return agreementTimeByPeriodPub.getCommonSetting(AppContexts.user().companyId(), employeeIds, period);
	}

}
