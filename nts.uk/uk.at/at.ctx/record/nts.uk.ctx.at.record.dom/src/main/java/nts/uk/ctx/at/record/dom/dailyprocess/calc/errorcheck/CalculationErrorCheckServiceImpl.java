package nts.uk.ctx.at.record.dom.dailyprocess.calc.errorcheck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.record.dom.attendanceitem.util.AttendanceItemConvertFactory;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CheckExcessAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ManagePerCompanySet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.record.dom.divergencetime.service.DivTimeSysFixedCheckService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.service.ErAlCheckService;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.SystemFixedErrorAlarm;
import nts.uk.shr.com.context.AppContexts;

/**
 * 日別計算用のエラーチェック
 * @author keisuke_hoshina
 *
 */
@Stateless
public class CalculationErrorCheckServiceImpl implements CalculationErrorCheckService{
	@Inject
	private DivTimeSysFixedCheckService divTimeSysFixedCheckService;
	
	@Inject
	private ErAlCheckService erAlCheckService;
	
	@Inject
	private AttendanceItemConvertFactory converterFactory;
	
	@Override
	public IntegrationOfDaily errorCheck(IntegrationOfDaily integrationOfDaily, ManagePerCompanySet master) {
		String companyID = AppContexts.user().companyId();
		List<EmployeeDailyPerError> addItemList = new ArrayList<>();
//		if(!integrationOfDaily.getEmployeeError().isEmpty() &&  integrationOfDaily.getEmployeeError() != null)
//			addItemList = integrationOfDaily.getEmployeeError();
		DailyRecordToAttendanceItemConverter attendanceItemConverter = this.converterFactory.createDailyConverter().setData(integrationOfDaily);
		//勤務実績のエラーアラーム数分ループ
		for(ErrorAlarmWorkRecord errorItem : master.getErrorAlarm()) {
			//使用しない
			if(!errorItem.getUseAtr()) continue;
			
			//システム固定
			if(errorItem.getFixedAtr()) {
				val addItems = systemErrorCheck(integrationOfDaily,errorItem,attendanceItemConverter, master);
				if(!addItems.isEmpty() && addItems != null) {
					for(val item : addItems) {
						Boolean flg = true;
						
						//addListにふくまれていなければ追加する
						for(EmployeeDailyPerError addedItem : addItemList) {
							if(item.getErrorAlarmWorkRecordCode().equals(addedItem.getErrorAlarmWorkRecordCode())) {
								flg = false;
								break;
							}
						}
						if(flg) addItemList.add(item);
					}
					//addItemList.addAll(addItems);
				}
			}
			//ユーザ設定
			else {
				val addItems = erAlCheckService.checkErrorFor(companyID, integrationOfDaily.getAffiliationInfor().getEmployeeId(), 
						integrationOfDaily.getAffiliationInfor().getYmd(), errorItem, integrationOfDaily);
				if(!addItems.isEmpty() && addItems != null) {
					for(val item : addItems) {
						Boolean flg = true;
						
						//addListにふくまれていなければ追加する
						for(EmployeeDailyPerError addedItem : addItemList) {
							if(item.getErrorAlarmWorkRecordCode().equals(addedItem.getErrorAlarmWorkRecordCode())) {
								flg = false;
								break;
							}
						}
						if(flg) addItemList.add(item);
					}
					//addItemList.addAll(addItems);
				}
			}
		}
		integrationOfDaily.setEmployeeError(addItemList);
		return integrationOfDaily;
	}

	/**
	 * システム固定エラーチェック
	 * @param attendanceItemConverter 
	 * @return 社員の日別実績エラー一覧
	 */
	public List<EmployeeDailyPerError> systemErrorCheck(IntegrationOfDaily integrationOfDaily,ErrorAlarmWorkRecord errorItem, 
			DailyRecordToAttendanceItemConverter attendanceItemConverter, ManagePerCompanySet master) {
		Optional<SystemFixedErrorAlarm> fixedErrorAlarmCode = SystemFixedErrorAlarm.getEnumFromErrorCode(errorItem.getCode().toString());
		if(!integrationOfDaily.getAttendanceLeave().isPresent() || !fixedErrorAlarmCode.isPresent())
			return Collections.emptyList();
		
		switch(fixedErrorAlarmCode.get()) {
			//事前残業申請超過
			case PRE_OVERTIME_APP_EXCESS:
				return integrationOfDaily.getErrorList(integrationOfDaily.getAffiliationInfor().getEmployeeId(), 
												integrationOfDaily.getAffiliationInfor().getYmd(),
												fixedErrorAlarmCode.get(),
												CheckExcessAtr.PRE_OVERTIME_APP_EXCESS);
			//事前休出申請超過
			case PRE_HOLIDAYWORK_APP_EXCESS:
				return integrationOfDaily.getErrorList(integrationOfDaily.getAffiliationInfor().getEmployeeId(), 
												integrationOfDaily.getAffiliationInfor().getYmd(),
												fixedErrorAlarmCode.get(),
												CheckExcessAtr.PRE_HOLIDAYWORK_APP_EXCESS);
			//事前フレックス申請超過
			case PRE_FLEX_APP_EXCESS:
				return integrationOfDaily.getErrorList(integrationOfDaily.getAffiliationInfor().getEmployeeId(), 
												integrationOfDaily.getAffiliationInfor().getYmd(),
												fixedErrorAlarmCode.get(),
												CheckExcessAtr.PRE_FLEX_APP_EXCESS);
			//事前深夜申請超過
			case PRE_MIDNIGHT_EXCESS:
				return integrationOfDaily.getErrorList(integrationOfDaily.getAffiliationInfor().getEmployeeId(), 
												integrationOfDaily.getAffiliationInfor().getYmd(),
												fixedErrorAlarmCode.get(),
												CheckExcessAtr.PRE_MIDNIGHT_EXCESS);
			//残業時間実績超過
			case OVER_TIME_EXCESS:
				return integrationOfDaily.getErrorList(integrationOfDaily.getAffiliationInfor().getEmployeeId(), 
												integrationOfDaily.getAffiliationInfor().getYmd(),
												fixedErrorAlarmCode.get(),
												CheckExcessAtr.OVER_TIME_EXCESS);
			//休出時間実績超過
			case REST_TIME_EXCESS:
				return integrationOfDaily.getErrorList(integrationOfDaily.getAffiliationInfor().getEmployeeId(), 
												integrationOfDaily.getAffiliationInfor().getYmd(),
												fixedErrorAlarmCode.get(),
												CheckExcessAtr.REST_TIME_EXCESS);
			//フレックス時間実績超過
			case FLEX_OVER_TIME:
				return integrationOfDaily.getErrorList(integrationOfDaily.getAffiliationInfor().getEmployeeId(), 
												integrationOfDaily.getAffiliationInfor().getYmd(),
												fixedErrorAlarmCode.get(),
												CheckExcessAtr.FLEX_OVER_TIME);
			//深夜時間実績超過
			case MIDNIGHT_EXCESS:
				return integrationOfDaily.getErrorList(integrationOfDaily.getAffiliationInfor().getEmployeeId(), 
												integrationOfDaily.getAffiliationInfor().getYmd(),
												fixedErrorAlarmCode.get(),
												CheckExcessAtr.MIDNIGHT_EXCESS);
			
			//乖離時間のエラー	
			case ERROR_OF_DIVERGENCE_TIME:
			case ALARM_OF_DIVERGENCE_TIME:
			case DIVERGENCE_ERROR_1:
			case DIVERGENCE_ALARM_1:
			case DIVERGENCE_ERROR_2:
			case DIVERGENCE_ALARM_2:
			case DIVERGENCE_ERROR_3:
			case DIVERGENCE_ALARM_3:
			case DIVERGENCE_ERROR_4:
			case DIVERGENCE_ALARM_4:
			case DIVERGENCE_ERROR_5:
			case DIVERGENCE_ALARM_5:
			case DIVERGENCE_ERROR_6:
			case DIVERGENCE_ALARM_6:
			case DIVERGENCE_ERROR_7:
			case DIVERGENCE_ALARM_7:
			case DIVERGENCE_ERROR_8:
			case DIVERGENCE_ALARM_8:
			case DIVERGENCE_ERROR_9:
			case DIVERGENCE_ALARM_9:	
			case DIVERGENCE_ERROR_10:
			case DIVERGENCE_ALARM_10:
							
				if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().isPresent()) {
					return divTimeSysFixedCheckService.divergenceTimeCheckBySystemFixed(AppContexts.user().companyId(), 
																			 	 integrationOfDaily.getAffiliationInfor().getEmployeeId(), 
																			 	 integrationOfDaily.getAffiliationInfor().getYmd(),
																			 	 integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getDivTime().getDivergenceTime(),
																			 	integrationOfDaily.getAttendanceLeave(),
																			 	Arrays.asList(errorItem),
																			 	master.getDivergenceTime(),
																			 	master.getShareContainer());
				}
				return Collections.emptyList();
			//遅刻
			case LATE:
				return integrationOfDaily.getErrorList(integrationOfDaily.getAffiliationInfor().getEmployeeId(), 
												integrationOfDaily.getAffiliationInfor().getYmd(),
												fixedErrorAlarmCode.get(),
												CheckExcessAtr.LATE);
			//早退
			case LEAVE_EARLY:
				return integrationOfDaily.getErrorList(integrationOfDaily.getAffiliationInfor().getEmployeeId(), 
												integrationOfDaily.getAffiliationInfor().getYmd(),
												fixedErrorAlarmCode.get(),
												CheckExcessAtr.LEAVE_EARLY);
			//出勤打刻漏れ
			case TIME_LEAVING_STAMP_LEAKAGE:
			//入退門	
			case ENTRANCE_STAMP_LACKING:
			//PCログ打刻漏れ
			case PCLOG_STAMP_LEAKAGE:
			//打刻順序不正
			case INCORRECT_STAMP: 
			//休日打刻
			case HOLIDAY_STAMP:
			//二重打刻
			case DOUBLE_STAMP:
				
				
			//それ以外ルート
			default:
				return Collections.emptyList();
		}
	}
}
