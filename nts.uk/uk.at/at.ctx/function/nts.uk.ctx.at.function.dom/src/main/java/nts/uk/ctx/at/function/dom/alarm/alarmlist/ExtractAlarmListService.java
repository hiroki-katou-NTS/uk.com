package nts.uk.ctx.at.function.dom.alarm.alarmlist;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess.AggregationProcessService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ExtractAlarmListService {

//	@Inject
//	private AlarmListExtraProcessStatusRepository alListExtraProcessStatusRepo;
	
	@Inject
	private AggregationProcessService aggregationProcessService;

	public ExtractedAlarmDto extractAlarm(List<EmployeeSearchDto> listEmployee, String checkPatternCode,
			List<PeriodByAlarmCategory> periodByCategory) {		
		String companyID = AppContexts.user().companyId();
//		String employeeId = AppContexts.user().employeeId();
		
		// ドメインモデル「アラームリスト抽出処理状況」をチェックする
		// チェック条件に当てはまる場合 (When the check conditions apply)
//		if (this.alListExtraProcessStatusRepo.isAlListExtaProcessing(companyID, employeeId)) {
//			// 情報メッセージ(#Msg_993)を表示する
//			return new ExtractedAlarmDto(new ArrayList<>(), true, false);
//		}
		
		// チェック条件に当てはまらない場合(When it does not fit the check condition)
		// 条件を満たしていない
		if (listEmployee.isEmpty()) {
			// エラーメッセージ(#Msg_834)を表示する
			throw new BusinessException("Msg_834");	
		}
		
		// ドメインモデル「アラームリスト抽出処理状況」を作成する
//		GeneralDateTime now1 = GeneralDateTime.now();
		GeneralDate today = GeneralDate.today();
//		AlarmListExtraProcessStatus alarmExtraProcessStatus = new AlarmListExtraProcessStatus(
//				IdentifierUtil.randomUniqueId(),
//				companyID, GeneralDate.today(), 
//				now1.hours()*60 +now1.minutes(),
//				employeeId, null, null);
//		AlarmListExtraProcessStatusEvent.builder().isUpdate(false).status(alarmExtraProcessStatus).build().toBePublished();
//		this.alListExtraProcessStatusRepo.addAlListExtaProcess(alarmExtraProcessStatus);
		
		// 勤務実績のアラームリストの集計処理を行う
		List<AlarmExtraValueWkReDto> listAlarmExtraValueWR = aggregationProcessService.processAlarmListWorkRecord(today, companyID, listEmployee,
				checkPatternCode, periodByCategory);
		// ドメインモデル「アラームリスト抽出処理状況」を更新する
//		GeneralDateTime now2 = GeneralDateTime.now();
//		alarmExtraProcessStatus.setEndDateAndEndTime(GeneralDate.today(), now2.hours()*60 + now2.minutes());
//		this.alListExtraProcessStatusRepo.updateAlListExtaProcess(alarmExtraProcessStatus);
//		AlarmListExtraProcessStatusEvent.builder().isUpdate(true).status(alarmExtraProcessStatus).build().toBePublished();

		// 集計結果を確認する sort list
		Comparator<AlarmExtraValueWkReDto> comparator = Comparator.comparing(AlarmExtraValueWkReDto::getHierarchyCd)
																	.thenComparing(Comparator.comparing(AlarmExtraValueWkReDto::getEmployeeCode))
																	.thenComparing(Comparator.comparing(AlarmExtraValueWkReDto::getAlarmValueDate))
																	.thenComparing(Comparator.comparing(AlarmExtraValueWkReDto::getCategory));
		List<AlarmExtraValueWkReDto> sortedAlarmExtraValue = listAlarmExtraValueWR.stream().sorted(comparator).collect(Collectors.toList());
		// 集計データが無い場合
		if (listAlarmExtraValueWR.isEmpty()) {
			// 情報メッセージ(#Msg_835) を表示する
			return  new ExtractedAlarmDto(new ArrayList<>(), false, true);
		}
		// 集計データがある場合
		// B画面 ダイアログ「アラームリスト」を起動する
		return new ExtractedAlarmDto(sortedAlarmExtraValue, false, false);		

	}

}
