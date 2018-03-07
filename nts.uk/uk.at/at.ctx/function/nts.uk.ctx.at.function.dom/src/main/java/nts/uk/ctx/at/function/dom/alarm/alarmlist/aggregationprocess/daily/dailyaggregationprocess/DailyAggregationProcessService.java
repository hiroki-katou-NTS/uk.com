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
import nts.uk.ctx.at.function.dom.alarm.alarmlist.FuncEmployeeSearchDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.PeriodByAlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategoryRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.daily.DailyAlarmCondition;
import nts.uk.shr.com.context.AppContexts;

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


	public List<ValueExtractAlarm> dailyAggregationProcess(String checkConditionCode, PeriodByAlarmCategory period,
			FuncEmployeeSearchDto employee) {
		String companyID = AppContexts.user().companyId();
		// ドメインモデル「カテゴリ別アラームチェック条件」を取得する
		Optional<AlarmCheckConditionByCategory> alCheckConByCategory = alCheckConByCategoryRepo.find(companyID,
				AlarmCategory.DAILY.value, checkConditionCode);

		// カテゴリアラームチェック条件．抽出条件を元に日次データをチェックする
		DailyAlarmCondition dailyAlarmCondition = (DailyAlarmCondition) alCheckConByCategory.get()
				.getExtractionCondition();

		// tab2: 日別実績のエラーアラーム
			
		// tab4:
		//「システム固定のチェック項目」で実績をチェックする
		
		
		//get data by dailyAlarmCondition
		List<FixedConWorkRecordAdapterDto> listFixed =  fixedConWorkRecordAdapter.getAllFixedConWorkRecordByID(dailyAlarmCondition.getDailyAlarmConID());
		List<ValueExtractAlarm> listValueExtractAlarm = new ArrayList<>(); 
		for(int i = 1;i <= listFixed.size();i++) {
			if(listFixed.get(i).isUseAtr()) {
				switch(i) {
				case 1 :
					for(GeneralDate date = period.getStartDate();date.after(period.getEndDate());date.addDays(1)) {
						String workType = recordWorkInfoFunAdapter.getInfoCheckNotRegister(employee.getId(), date).getWorkTypeCode();
						
						ValueExtractAlarm checkWorkType = fixedCheckItemAdapter.checkWorkTypeNotRegister(employee.getWorkplaceId(),employee.getId(), date, workType);
						if(checkWorkType !=null) {
							listValueExtractAlarm.add(checkWorkType);
						}
						
					}
					break;
				case 2 :
					for(GeneralDate date = period.getStartDate();date.after(period.getEndDate());date.addDays(1)) {
						String workTime = recordWorkInfoFunAdapter.getInfoCheckNotRegister(employee.getId(), date).getWorkTimeCode();
						ValueExtractAlarm checkWorkTime = fixedCheckItemAdapter.checkWorkTimeNotRegister(employee.getWorkplaceId(),employee.getId(), date, workTime);
						if(checkWorkTime !=null) {
							listValueExtractAlarm.add(checkWorkTime);
						}
					}
					break;
				case 3 : 
					 List<ValueExtractAlarm> listCheckPrincipalUnconfirm = fixedCheckItemAdapter.checkPrincipalUnconfirm(employee.getWorkplaceId(), employee.getId(), period.getStartDate(), period.getEndDate());
					 if(!listCheckPrincipalUnconfirm.isEmpty()) {
						 listValueExtractAlarm.addAll(listCheckPrincipalUnconfirm);
					 }
					break;
				case 4 :
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
