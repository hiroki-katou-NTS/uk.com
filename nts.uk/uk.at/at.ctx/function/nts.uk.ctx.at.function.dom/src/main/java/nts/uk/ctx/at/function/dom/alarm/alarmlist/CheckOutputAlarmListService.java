package nts.uk.ctx.at.function.dom.alarm.alarmlist;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess.AggregationProcessService;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatus;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatusRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CheckOutputAlarmListService {

	@Inject
	private AlarmListExtraProcessStatusRepository alListExtraProcessStatusRepo;
	
	@Inject
	private AggregationProcessService aggregationProcessService;

	public List<AlarmExtraValueWkReDto> checkOutputAlarmList(List<String> listEmployee, String checkPatternCode,
			List<OutputScreenA> listOutputScreenA) {
		String companyID = AppContexts.user().companyId();
		String userLogin = AppContexts.user().userId();
		
		// ドメインモデル「アラームリスト抽出処理状況」をチェックする
		Optional<AlarmListExtraProcessStatus> alarmListExtraProcessStatus = alListExtraProcessStatusRepo
				.getAlListExtaProcessByEndDate(companyID);
		// チェック条件に当てはまる場合 (When the check conditions apply)
		if (!alarmListExtraProcessStatus.isPresent()) {
			// 情報メッセージ(#Msg_993)を表示する
			throw new BusinessException("Msg_993");
		}
		
		// チェック条件に当てはまらない場合(When it does not fit the check condition)
		// 条件を満たしていない
		if (listEmployee.isEmpty()) {
			// エラーメッセージ(#Msg_834)を表示する
			throw new BusinessException("Msg_834");
		}
		
		// ドメインモデル「アラームリスト抽出処理状況」を作成する
		AlarmListExtraProcessStatus alarmExtraProcessStatus = new AlarmListExtraProcessStatus(companyID, GeneralDate.today(), GeneralDateTime.now().seconds(),
				userLogin, null, null);
		this.alListExtraProcessStatusRepo.addAlListExtaProcess(alarmExtraProcessStatus);
		
		// 勤務実績のアラームリストの集計処理を行う
		List<AlarmExtraValueWkReDto> listAlarmExtraValueWR = aggregationProcessService.processAlarmListWorkRecord(listEmployee,
				checkPatternCode, listOutputScreenA);
		// ドメインモデル「アラームリスト抽出処理状況」を更新する
		alarmExtraProcessStatus.setEndDateAndEndTime(GeneralDate.today(), GeneralDateTime.now().seconds());
		this.alListExtraProcessStatusRepo.updateAlListExtaProcess(alarmExtraProcessStatus);

		// 集計結果を確認する sort list
		Comparator<AlarmExtraValueWkReDto> comparator = Comparator.comparing(AlarmExtraValueWkReDto::getWorkplaceID);
		comparator = comparator.thenComparing(Comparator.comparing(AlarmExtraValueWkReDto::getEmployeeCode));
		comparator = comparator.thenComparing(Comparator.comparing(AlarmExtraValueWkReDto::getAlarmValueDate));
		comparator = comparator.thenComparing(Comparator.comparing(AlarmExtraValueWkReDto::getCategory));
		comparator = comparator.thenComparing(Comparator.comparing(AlarmExtraValueWkReDto::getAlarmItem));
		Stream<AlarmExtraValueWkReDto> alarmExtraValueStream = listAlarmExtraValueWR.stream().sorted(comparator);
		List<AlarmExtraValueWkReDto> sortedAlarmExtraValue = alarmExtraValueStream.collect(Collectors.toList());
		// 集計データが無い場合
		if (listAlarmExtraValueWR.isEmpty()) {
			// 情報メッセージ(#Msg_835) を表示する
			throw new BusinessException("Msg_835");
		}
		// 集計データがある場合
		// B画面 ダイアログ「アラームリスト」を起動する
		return sortedAlarmExtraValue;

	}

}
