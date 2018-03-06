package nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess.daily.dailyaggregationprocess;

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


	public void dailyAggregationProcess(String checkConditionCode, PeriodByAlarmCategory period,
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
		
		for(int i = 1;i <= listFixed.size();i++) {
			if(listFixed.get(i).isUseAtr()) {
				switch(i) {
				case 1 :
					for(GeneralDate date = period.getStartDate();date.after(period.getEndDate());date.addDays(1)) {
						String workType = recordWorkInfoFunAdapter.getInfoCheckNotRegister(employee.getId(), date).getWorkTypeCode();
						fixedCheckItemAdapter.checkWorkTypeNotRegister(employee.getId(), date, workType);
					}
					break;
				case 2 :
					for(GeneralDate date = period.getStartDate();date.after(period.getEndDate());date.addDays(1)) {
						String workTime = recordWorkInfoFunAdapter.getInfoCheckNotRegister(employee.getId(), date).getWorkTimeCode();
						fixedCheckItemAdapter.checkWorkTimeNotRegister(employee.getId(), date, workTime);
					}
					break;
				case 3 : 
					fixedCheckItemAdapter.checkPrincipalUnconfirm(employee.getWorkplaceId(), employee.getId(), period.getStartDate(), period.getEndDate());
					break;
				case 4 :
					fixedCheckItemAdapter.checkAdminUnverified(employee.getWorkplaceId(), employee.getId(), period.getStartDate(), period.getEndDate());
					break;
				default :
					fixedCheckItemAdapter.checkingData(employee.getId(), period.getStartDate(), period.getEndDate());
					break;
				}//end switch
			}//end if
		}//end for

	}
}
