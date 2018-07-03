package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.errorcheck.CalculationErrorCheckService;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeRepository;
import nts.uk.ctx.at.record.dom.divergencetime.service.MasterShareBus;
import nts.uk.ctx.at.record.dom.divergencetime.service.MasterShareBus.MasterShareContainer;
import nts.uk.ctx.at.record.dom.optitem.OptionalItem;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository;
import nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionRepository;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaRepository;
import nts.uk.ctx.at.record.dom.statutoryworkinghours.DailyStatutoryWorkingHours;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrule.specific.SpecificWorkRuleRepository;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class CalculateDailyRecordServiceCenterImpl implements CalculateDailyRecordServiceCenter{
	//休暇加算設定
	@Inject
	private HolidayAddtionRepository holidayAddtionRepository;
	//総拘束時間
	@Inject
	private SpecificWorkRuleRepository specificWorkRuleRepository;
	//会社ごとの代休設定
	@Inject
	private CompensLeaveComSetRepository compensLeaveComSetRepository;
	//乖離
	@Inject 
	private DivergenceTimeRepository divergenceTimeRepository;
	//エラーアラーム設定
	@Inject
	private ErrorAlarmWorkRecordRepository errorAlarmWorkRecordRepository;
	
	//リポジトリ：労働条件
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;

	//リポジトリ；法定労働
	@Inject
	private DailyStatutoryWorkingHours dailyStatutoryWorkingHours;
	
	@Inject
	private CalculateDailyRecordService calculate;
	
	@Inject
	private CalculationErrorCheckService calculationErrorCheckService;
	
	//↓以下任意項目の計算の為に追加
	@Inject
	private OptionalItemRepository optionalItemRepository;
	
	@Inject
	private FormulaRepository formulaRepository;
	
	@Inject
	private EmpConditionRepository empConditionRepository;

	
	@Override
	public List<IntegrationOfDaily> calculate(List<IntegrationOfDaily> integrationOfDaily) {
		
		if(integrationOfDaily.isEmpty()) return integrationOfDaily;
		//会社共通の設定を
		MasterShareContainer shareContainer = MasterShareBus.open();
		val companyCommonSetting = new ManagePerCompanySet(holidayAddtionRepository.findByCompanyId(AppContexts.user().companyId()),
														   holidayAddtionRepository.findByCId(AppContexts.user().companyId()),
														   specificWorkRuleRepository.findCalcMethodByCid(AppContexts.user().companyId()),
														   compensLeaveComSetRepository.find(AppContexts.user().companyId()),
														   divergenceTimeRepository.getAllDivTime(AppContexts.user().companyId()),
														   errorAlarmWorkRecordRepository.getAllErAlCompanyAndUseAtr(AppContexts.user().companyId(), true));

		companyCommonSetting.setShareContainer(shareContainer);
		//社員毎の期間取得
		val integraListByRecordAndEmpId = getIntegrationOfDailyByEmpId(integrationOfDaily);
		
		List<GeneralDate> sortedymd = integrationOfDaily.stream()
								  					 	.sorted((first,second) -> first.getAffiliationInfor().getYmd().compareTo(second.getAffiliationInfor().getYmd()))
								  					 	.map(tc -> tc.getAffiliationInfor().getYmd())
								  					 	.collect(Collectors.toList());
		
		val maxGeneralDate = sortedymd.get(0);
		val minGeneralDate = sortedymd.get(sortedymd.size() - 1);
		//労働制マスタ取得
		val masterData = workingConditionItemRepository.getBySidAndPeriodOrderByStrDWithDatePeriod(integraListByRecordAndEmpId,maxGeneralDate,minGeneralDate);
		

		//前日計算したboolean(必要な設定がそろっているか判断するためのもの)
//		calCalc = false;
//		//初期値
//		//返値用のリスト
		List<IntegrationOfDaily> returnList = new ArrayList<>();
//		//法定労働時間
//		DailyUnit dailyUnit = new DailyUnit();
//		//労働制
//		//雇用コード
//		//雇用ID
		
		/*----------------------------------任意項目の計算に必要なデータ取得-----------------------------------------------*/
		String companyId = AppContexts.user().companyId();
		//AggregateRoot「任意項目」取得
		List<OptionalItem> optionalItems = optionalItemRepository.findAll(companyId);
		companyCommonSetting.setOptionalItems(optionalItems);
		//任意項目NOのlist作成
		List<Integer> optionalItemNoList = optionalItems.stream().map(oi -> oi.getOptionalItemNo().v()).collect(Collectors.toList());
		//計算式を取得
		companyCommonSetting.setFormulaList(formulaRepository.find(companyId));
		//適用する雇用条件の取得
		companyCommonSetting.setEmpCondition(empConditionRepository.findAll(companyId, optionalItemNoList));
		/*----------------------------------任意項目の計算に必要なデータ取得-----------------------------------------------*/
		
		for(IntegrationOfDaily nowIntegration:integrationOfDaily) {

			//nowIntegrationの労働制取得
			Optional<Entry<DateHistoryItem, WorkingConditionItem>> nowWorkingItem = masterData.getItemAtDateAndEmpId(nowIntegration.getAffiliationInfor().getYmd(),nowIntegration.getAffiliationInfor().getEmployeeId());
			if(nowWorkingItem.isPresent()) {
				companyCommonSetting.setPersonInfo(Optional.of(nowWorkingItem.get().getValue()));
				val dailyUnit = dailyStatutoryWorkingHours.getDailyUnit(AppContexts.user().companyId(),
																		nowIntegration.getAffiliationInfor().getEmploymentCode().toString(),
																		nowIntegration.getAffiliationInfor().getEmployeeId(),
																		nowIntegration.getAffiliationInfor().getYmd(),
																		nowWorkingItem.get().getValue().getLaborSystem());
				if(dailyUnit == null) {
					returnList.add(nowIntegration);
				}
				else {
					companyCommonSetting.setDailyUnit(dailyUnit);
					returnList.add(calculate.calculate(nowIntegration, companyCommonSetting));
				}
			}
			else {
				returnList.add(nowIntegration);
			}
		}
		shareContainer.clearAll();
		shareContainer= null;
		return returnList;
	}
//			if(calCalc) {
//				if(nowWorkingItemがNotOptional) {
//					if(nowIntegration.getAffiliationInfor().getYmd()が、現在取得している労働制の期間に含まれているか) {
//						今の状態の設定とnowIntegrationを使って計算処理呼ぶ
//						returnList.add(calculate.calculate(nowIntegration, companyCommonSetting));
//						前日計算区分 = true
//					}
//					
//					else{
//						新たに労働制を取得
//
//						if(新たに取得した労働制がNotOptional) {
//							companyCommonSetting.setPersonInfo();
//							val dailyUnit = dailyStatutoryWorkingHours.getDailyUnit(AppContexts.user().companyId(),
//																					nowIntegration.getAffiliationInfor().getEmploymentCode().toString(),
//																					nowIntegration.getAffiliationInfor().getEmployeeId(),
//																					nowIntegration.getAffiliationInfor().getYmd(),
//																					nowWorkingItem.get().getValue().getLaborSystem());
//							if(dailyUnit != null) {
//								//今の状態の設定とnowIntegrationを使って計算処理呼ぶ
//								companyCommonSetting.setDailyUnit(dailyUnit);
//								returnList.add(calculate.calculate(nowIntegration, companyCommonSetting));
//							}
//							else {
//								//法定労働時間が取得できない　＝ 設定不足で計算できないと判断する
//								returnList.add(nowIntegration);
//							}
//							
//						}
//						else {
//							//労働制が取得できない　＝ 設定不足で計算できないと判断する
//							returnList.add(nowIntegration);
//						}
//					}
//				}
//				else {
//					今の状態の設定とnowIntegrationを使って計算処理呼ぶ
//					returnList.add(calculate.calculate(nowIntegration, companyCommonSetting));
//					前日計算区分 = true
//				}
//			}
//			else {
//				returnList.add(nowIntegration);
//			}

	
	/**
	 * 日別実績(WORK)Listから社員毎の計算したい期間を取得する 
	 * @param integrationOfDaily
	 * @return　<社員、計算したい期間
	 */
	private Map<String,DatePeriod> getIntegrationOfDailyByEmpId(List<IntegrationOfDaily> integrationOfDaily) {
		Map<String,DatePeriod> returnMap = new HashMap<>();
		//しゃいんID 一覧取得
		List<String> idList = getAllEmpId(integrationOfDaily);
		idList.forEach(ts -> {
			//特定の社員IDに一致しているintegrationに絞る
			val integrationOfDailys = integrationOfDaily.stream().filter(tc -> tc.getAffiliationInfor().getEmployeeId().equals(ts)).collect(Collectors.toList());
			//特定社員の開始～終了を取得する
			val createdDatePriod = getDateSpan(integrationOfDailys);
			//Map<特定の社員ID、開始～終了>に追加する
			returnMap.put(ts, createdDatePriod);
		});
		return returnMap;
	}
	
	/*
	 * 社員の一覧取得
	 */
	private List<String> getAllEmpId(List<IntegrationOfDaily> integrationOfDaily){
		return integrationOfDaily.stream().distinct().map(tc->tc.getAffiliationInfor().getEmployeeId()).collect(Collectors.toList());
	}
	
	/**
	 * 開始～終了の作成
	 * @param integrationOfDaily 日別実績(WORK)LIST
	 * @return 開始～終了
	 */
	private DatePeriod getDateSpan(List<IntegrationOfDaily> integrationOfDaily) {
		val sortedIntegration = integrationOfDaily.stream().sorted((first,second) -> first.getAffiliationInfor().getYmd().compareTo(second.getAffiliationInfor().getYmd())).collect(Collectors.toList());
		return new DatePeriod(sortedIntegration.get(0).getAffiliationInfor().getYmd(), sortedIntegration.get(sortedIntegration.size() - 1).getAffiliationInfor().getYmd());
	}
	
	/**
	 * エラーチェック(外部から呼ぶ用)
	 * @param integrationList
	 * @return
	 */
	@Override
	public List<IntegrationOfDaily> errorCheck(List<IntegrationOfDaily> integrationList){
		if(integrationList.isEmpty()) return integrationList;
		//会社共通の設定を
		val companyCommonSetting = new ManagePerCompanySet(holidayAddtionRepository.findByCompanyId(AppContexts.user().companyId()),
														   holidayAddtionRepository.findByCId(AppContexts.user().companyId()),
														   specificWorkRuleRepository.findCalcMethodByCid(AppContexts.user().companyId()),
														   compensLeaveComSetRepository.find(AppContexts.user().companyId()),
														   divergenceTimeRepository.getAllDivTime(AppContexts.user().companyId()),
														   errorAlarmWorkRecordRepository.getListErrorAlarmWorkRecord(AppContexts.user().companyId()));

		//社員毎の期間取得
		val integraListByRecordAndEmpId = getIntegrationOfDailyByEmpId(integrationList);
		
		List<GeneralDate> sortedymd = integrationList.stream()
								  					 	.sorted((first,second) -> first.getAffiliationInfor().getYmd().compareTo(second.getAffiliationInfor().getYmd()))
								  					 	.map(tc -> tc.getAffiliationInfor().getYmd())
								  					 	.collect(Collectors.toList());
		
		val maxGeneralDate = sortedymd.get(0);
		val minGeneralDate = sortedymd.get(sortedymd.size() - 1);
		//労働制マスタ取得
		val masterData = workingConditionItemRepository.getBySidAndPeriodOrderByStrDWithDatePeriod(integraListByRecordAndEmpId,maxGeneralDate,minGeneralDate);
		List<IntegrationOfDaily> returnList = new ArrayList<>();
		for(IntegrationOfDaily integration : integrationList) {

			//nowIntegrationの労働制取得
			Optional<Entry<DateHistoryItem, WorkingConditionItem>> nowWorkingItem = masterData.getItemAtDateAndEmpId(integration.getAffiliationInfor().getYmd(),integration.getAffiliationInfor().getEmployeeId());
			if(nowWorkingItem.isPresent()) {
				companyCommonSetting.setPersonInfo(Optional.of(nowWorkingItem.get().getValue()));
				val dailyUnit = dailyStatutoryWorkingHours.getDailyUnit(AppContexts.user().companyId(),
																		integration.getAffiliationInfor().getEmploymentCode().toString(),
																		integration.getAffiliationInfor().getEmployeeId(),
																		integration.getAffiliationInfor().getYmd(),
																		nowWorkingItem.get().getValue().getLaborSystem());
				if(dailyUnit == null) {
					returnList.add(integration);
				}
				else {
					companyCommonSetting.setDailyUnit(dailyUnit);
					returnList.add(calculationErrorCheckService.errorCheck(integration, companyCommonSetting));
				}
			}
			else {
				returnList.add(integration);
			}
		}
		return returnList;
	}

}
