package nts.uk.ctx.at.function.app.find.alarm.alarmlist;

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
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSetting;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSettingRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategoryRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatus;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatusRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CheckOutputAlarmList {

	@Inject
	private AlarmListExtraProcessStatusRepository alListExtraProcessStatusRepo;

	@Inject
	private AlarmPatternSettingRepository alPatternSettingRepo;

	public List<AlarmExtraValueWorkRecordDto> checkOutputAlarmList(List<String> listEmployee, String checkPatternCode,
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
		GeneralDate today = GeneralDate.today();
		int timeNow = GeneralDateTime.now().seconds();
		AlarmListExtraProcessStatus alarmExtraProcessStatus = new AlarmListExtraProcessStatus(companyID, today, timeNow,
				userLogin, null, null);
		this.alListExtraProcessStatusRepo.addAlListExtaProcess(alarmExtraProcessStatus);
		// 勤務実績のアラームリストの集計処理を行う
		List<AlarmExtraValueWorkRecordDto> listAlarmExtraValueWR = processAlarmListWorkRecord(listEmployee,
				checkPatternCode, listOutputScreenA);
		// ドメインモデル「アラームリスト抽出処理状況」を更新する
		alarmExtraProcessStatus.setEndDateAndEndTime(GeneralDate.today(), GeneralDateTime.now().seconds());
		this.alListExtraProcessStatusRepo.updateAlListExtaProcess(alarmExtraProcessStatus);

		// 集計結果を確認する sort list
		Comparator<AlarmExtraValueWorkRecordDto> comparator = Comparator.comparing(AlarmExtraValueWorkRecordDto::getWorkplaceID);
		comparator = comparator.thenComparing(Comparator.comparing(AlarmExtraValueWorkRecordDto::getEmployeeCode));
		comparator = comparator.thenComparing(Comparator.comparing(AlarmExtraValueWorkRecordDto::getAlarmValueDate));
		comparator = comparator.thenComparing(Comparator.comparing(AlarmExtraValueWorkRecordDto::getCategory));
		comparator = comparator.thenComparing(Comparator.comparing(AlarmExtraValueWorkRecordDto::getAlarmItem));
		Stream<AlarmExtraValueWorkRecordDto> alarmExtraValueStream = listAlarmExtraValueWR.stream().sorted(comparator);
		List<AlarmExtraValueWorkRecordDto> sortedAlarmExtraValue = alarmExtraValueStream.collect(Collectors.toList());
		// 集計データが無い場合
		if (listAlarmExtraValueWR.isEmpty()) {
			// 情報メッセージ(#Msg_835) を表示する
			throw new BusinessException("Msg_835");
		}
		// 集計データがある場合
		// B画面 ダイアログ「アラームリスト」を起動する
		return sortedAlarmExtraValue;

	}

	/**
	 * @param listEmployee 従業員(List)
	 * @param checkPatternCode パターンコード
	 * @param listOutputScreenA カテゴリ別期間(List)
	 * @return
	 */
	private List<AlarmExtraValueWorkRecordDto> processAlarmListWorkRecord(List<String> listEmployee,
			String checkPatternCode, List<OutputScreenA> listOutputScreenA) {
		String companyID = AppContexts.user().companyId();
		// パラメータ．パターンコードをもとにドメインモデル「アラームリストパターン設定」を取得する
		Optional<AlarmPatternSetting> alarmPatternSetting = this.alPatternSettingRepo.findByAlarmPatternCode(companyID,
				checkPatternCode);
		// 従業員ごと に行う(for list employee)
		for (String employee : listEmployee) {
			// 次のチェック条件コードで集計する(loop list by category)
			for (CheckCondition checkCondition : alarmPatternSetting.get().getCheckConList()) {
				// get Period by category
				Optional<OutputScreenA> outputScreenA = listOutputScreenA.stream()
						.filter(c -> c.getCategory() == checkCondition.getAlarmCategory().value).findFirst();
				for (String checkConditionCode : checkCondition.getCheckConditionList()) {

					// カテゴリ：日次のチェック条件( daily)
					if (checkCondition.getAlarmCategory().equals(AlarmCategory.DAILY)) {

						// アルゴリズム「日次の集計処理」を実行する
						DailyAggregationProcess(checkConditionCode, outputScreenA.get(), employee);
					}
					// カテゴリ：4週4休のチェック条件(4 week 4 day)
					if (checkCondition.getAlarmCategory().equals(AlarmCategory.SCHEDULE_4WEEK)) {
						// アルゴリズム「4週4休の集計処理」を実行する
						TotalProcess4Week4Day(checkConditionCode, outputScreenA.get(), employee);

					}
					// カテゴリ：月次のチェック条件 (month
					if (checkCondition.getAlarmCategory().equals(AlarmCategory.MONTHLY)) {
						// tạm thời chưa làm
					}
				}

			}

		}

		return null;
	}
	
	@Inject
	private AlarmCheckConditionByCategoryRepository alCheckConByCategoryRepo;

	/**
	 * 「日次の集計処理」
	 * 
	 * @param patternCode
	 * @param outputScreenA
	 * @param employee
	 */
	private void DailyAggregationProcess(String patternCode, OutputScreenA outputScreenA, String employee) {
		String companyID = AppContexts.user().companyId();
		//ドメインモデル「カテゴリ別アラームチェック条件」を取得する
		Optional<AlarmCheckConditionByCategory> alCheckConByCategory = alCheckConByCategoryRepo.find(companyID, AlarmCategory.DAILY.value, patternCode);
		//対象者を絞り込む
		 List<String>  listEmployeeID =  collapseTargetPerson(GeneralDate.today());
		
	}	
	
	private List<String> collapseTargetPerson( GeneralDate today) {
		
		return null;
	}
	
	
	/**
	 * 「4週4休の集計処理」
	 * @param patternCode
	 * @param outputScreenA
	 * @param employee
	 */
	private void TotalProcess4Week4Day(String patternCode, OutputScreenA outputScreenA, String employee) {

	}

}
