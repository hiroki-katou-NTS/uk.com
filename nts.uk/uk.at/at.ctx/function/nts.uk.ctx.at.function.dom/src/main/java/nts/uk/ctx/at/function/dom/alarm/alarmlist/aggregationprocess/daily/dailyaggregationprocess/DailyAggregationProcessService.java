package nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess.daily.dailyaggregationprocess;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.FixedConWorkRecordAdapter;
import nts.uk.ctx.at.function.dom.adapter.FixedConWorkRecordAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.fixedcheckitem.FixedCheckItemAdapter;
import nts.uk.ctx.at.function.dom.adapter.worklocation.RecordWorkInfoFunAdapter;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.alarmdata.ValueExtractAlarm;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.EmployeeSearchDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.PeriodByAlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategoryRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.daily.DailyAlarmCondition;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class DailyAggregationProcessService {

	@Inject
	private AlarmCheckConditionByCategoryRepository alCheckConByCategoryRepo;

	@Inject
	private FixedConWorkRecordAdapter fixedConWorkRecordAdapter;
	
	@Inject
	private FixedCheckItemAdapter fixedCheckItemAdapter;
	
	@Inject
	private RecordWorkInfoFunAdapter recordWorkInfoFunAdapter;
	
	@Inject
	private DailyPerformanceService dailyPerformanceService;


	public List<ValueExtractAlarm> dailyAggregationProcess(String checkConditionCode, PeriodByAlarmCategory period,
			EmployeeSearchDto employee) {
		
		List<ValueExtractAlarm> listValueExtractAlarm = new ArrayList<>(); 		
		String companyID = AppContexts.user().companyId();
		
		// ドメインモデル「カテゴリ別アラームチェック条件」を取得する
		Optional<AlarmCheckConditionByCategory> alCheckConByCategory = alCheckConByCategoryRepo.find(companyID,
				AlarmCategory.DAILY.value, checkConditionCode);

		// カテゴリアラームチェック条件．抽出条件を元に日次データをチェックする
		DailyAlarmCondition dailyAlarmCondition = (DailyAlarmCondition) alCheckConByCategory.get()
				.getExtractionCondition();

		// tab2: 日別実績のエラーアラーム
		List<ValueExtractAlarm> extractDailyRecord = this.extractDailyRecord(dailyAlarmCondition, period, employee, companyID);
		listValueExtractAlarm.addAll(extractDailyRecord);
		
		
		
		// tab4: 「システム固定のチェック項目」で実績をチェックする			
		List<ValueExtractAlarm> extractFixedCondition = this.extractFixedCondition(dailyAlarmCondition, period, employee);
		listValueExtractAlarm.addAll(extractFixedCondition);
		
		return listValueExtractAlarm;
	}
	
	
	private List<ValueExtractAlarm> extractDailyRecord(DailyAlarmCondition dailyAlarmCondition,
			PeriodByAlarmCategory period, EmployeeSearchDto employee, String companyID) {
		return dailyPerformanceService.aggregationProcess(dailyAlarmCondition, period, employee, companyID);
	}
	
	
	private List<ValueExtractAlarm> extractFixedCondition(DailyAlarmCondition dailyAlarmCondition, PeriodByAlarmCategory period, EmployeeSearchDto employee){
		List<ValueExtractAlarm> listValueExtractAlarm = new ArrayList<>(); 
		
		//get data by dailyAlarmCondition
		List<FixedConWorkRecordAdapterDto> listFixed =  fixedConWorkRecordAdapter.getAllFixedConWorkRecordByID(dailyAlarmCondition.getDailyAlarmConID());
		for(int i = 0;i < listFixed.size();i++) {
			if(listFixed.get(i).isUseAtr()) {
				switch(i) {
				case 0 :
					for(GeneralDate date : period.getListDate()) {
						String workType = recordWorkInfoFunAdapter.getInfoCheckNotRegister(employee.getId(), date).get().getWorkTypeCode();
						
						Optional<ValueExtractAlarm> checkWorkType = fixedCheckItemAdapter.checkWorkTypeNotRegister(employee.getWorkplaceId(),employee.getId(), date, workType);
						if(checkWorkType.isPresent()) {
							listValueExtractAlarm.add(checkWorkType.get());
						}
						
					}
					break;
				case 1 :
					for(GeneralDate date : period.getListDate()) {
						String workTime = recordWorkInfoFunAdapter.getInfoCheckNotRegister(employee.getId(), date).get().getWorkTimeCode();
						Optional<ValueExtractAlarm> checkWorkTime = fixedCheckItemAdapter.checkWorkTimeNotRegister(employee.getWorkplaceId(),employee.getId(), date, workTime);
						if(checkWorkTime.isPresent()) {
							listValueExtractAlarm.add(checkWorkTime.get());
						}
					}
					break;
				case 2 : 
					 List<ValueExtractAlarm> listCheckPrincipalUnconfirm = fixedCheckItemAdapter.checkPrincipalUnconfirm(employee.getWorkplaceId(), employee.getId(), period.getStartDate(), period.getEndDate());
					 if(!listCheckPrincipalUnconfirm.isEmpty()) {
						 listValueExtractAlarm.addAll(listCheckPrincipalUnconfirm);
					 }
					break;
				case 3 :
					List<ValueExtractAlarm> listCheckAdminUnverified = fixedCheckItemAdapter.checkAdminUnverified(employee.getWorkplaceId(), employee.getId(), period.getStartDate(), period.getEndDate());
					if(!listCheckAdminUnverified.isEmpty()) {
						 listValueExtractAlarm.addAll(listCheckAdminUnverified);
					 }
					break;
				default :
					List<ValueExtractAlarm> listCheckingData = fixedCheckItemAdapter.checkingData(employee.getWorkplaceId(),employee.getId(), period.getStartDate(), period.getEndDate());
					if(!listCheckingData.isEmpty()) {
						 listValueExtractAlarm.addAll(listCheckingData);
					 }
					break;
				}//end switch
			}//end if
		}//end for
		return listValueExtractAlarm;
	}
}
