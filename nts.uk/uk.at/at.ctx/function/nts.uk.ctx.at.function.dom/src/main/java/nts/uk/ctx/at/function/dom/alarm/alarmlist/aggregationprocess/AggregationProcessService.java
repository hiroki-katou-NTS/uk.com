package nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.WorkPlaceHistImport;
import nts.uk.ctx.at.function.dom.adapter.WorkplaceWorkRecordAdapter;
import nts.uk.ctx.at.function.dom.adapter.companyRecord.StatusOfEmployeeAdapter;
import nts.uk.ctx.at.function.dom.adapter.companyRecord.SyCompanyRecordFuncAdapter;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkPlaceInforExport;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.recordcheck.ErAlWorkRecordCheckAdapter;
import nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.recordcheck.RegulationInfoEmployeeResult;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSetting;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSettingRepository;
import nts.uk.ctx.at.function.dom.alarm.alarmdata.ValueExtractAlarm;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.AlarmExtraValueWkReDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.EmployeeSearchDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.PeriodByAlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategoryRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmExtracResult;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckInfor;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmPatternExtractResult;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmPatternExtractResultRepository;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.CategoryCondValueDto;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ExtractionResultDetail;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ResultOfEachCondition;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class AggregationProcessService {
	@Inject
	private AlarmPatternExtractResultRepository alarmRepos;
	@Inject
	private SyCompanyRecordFuncAdapter syCompAdapter;
	@Inject
	private WorkplaceWorkRecordAdapter wpAdapter;
	@Inject
	private ManagedParallelWithContext parallelManager;
	@Inject
	private AlarmPatternSettingRepository alPatternSettingRepo;
	
	@Inject
	private ExtractAlarmForEmployeeService extractService;
	
	@Inject
	private WorkplaceAdapter workplaceAdapter;
	@Inject
	private AlarmCheckConditionByCategoryRepository checkConditionRepo;
	@Inject
	private ErAlWorkRecordCheckAdapter erCheckAdapter;
		
	public List<AlarmExtraValueWkReDto> processAlarmListWorkRecord(GeneralDate baseDate, String companyID, List<EmployeeSearchDto> listEmployee, 
			String checkPatternCode, List<PeriodByAlarmCategory> periodByCategory) {
		List<AlarmExtraValueWkReDto> result = new ArrayList<>();
		
		// パラメータ．パターンコードをもとにドメインモデル「アラームリストパターン設定」を取得する
		// パラメータ．パターンコードから「アラームリストパターン設定」を取得する
		Optional<AlarmPatternSetting> alarmPatternSetting = this.alPatternSettingRepo.findByAlarmPatternCode(companyID, checkPatternCode);		
		if(!alarmPatternSetting.isPresent())
			throw new RuntimeException("「アラームリストパターン設定 」が見つかりません！");
		
		
		List<ValueExtractAlarm> valueList = new ArrayList<>();

		valueList.addAll(extractService.process(companyID, alarmPatternSetting.get().getCheckConList(), periodByCategory, listEmployee));

		
		// get list workplaceId and hierarchyCode 
		List<String> listWorkplaceId = listEmployee.stream().map(e -> e.getWorkplaceId()).filter(wp -> wp != null).distinct().collect(Collectors.toList());
//		Map<String, WkpConfigAtTimeAdapterDto> hierarchyWPMap = syWorkplaceAdapter.findByWkpIdsAtTime(companyID, baseDate, listWorkplaceId)
//																			.stream().collect(Collectors.toMap(WkpConfigAtTimeAdapterDto::getWorkplaceId, x->x));
		//[No.560]職場IDから職場の情報をすべて取得する
		List<WorkPlaceInforExport> wkpExportList = this.workplaceAdapter.getWorkplaceInforByWkpIds(companyID, listWorkplaceId, baseDate);
		
		// Map employeeID to EmployeeSearchDto object
		Map<String, EmployeeSearchDto> mapEmployeeId = listEmployee.stream().collect(Collectors.toMap(EmployeeSearchDto::getId, x->x));
		
		// MAP Enum AlarmCategory 
		Map<String, AlarmCategory> mapTextResourceToEnum = this.mapTextResourceToEnum();
		
		
		//Convert from ValueExtractAlarm to AlarmExtraValueWkReDto
		for(ValueExtractAlarm value: valueList) {
			AlarmExtraValueWkReDto itemResult = new AlarmExtraValueWkReDto(value.getWorkplaceID().orElse(null),
					value.getWorkplaceID().isPresent() && wkpExportList.stream().filter(x -> value.getWorkplaceID().get().equals(x.getWorkplaceId())).findFirst().isPresent() ?
							wkpExportList.stream().filter(x -> value.getWorkplaceID().get().equals(x.getWorkplaceId())).findFirst().get().getHierarchyCode() : "",
					mapEmployeeId.get(value.getEmployeeID()).getWorkplaceName(), 
					value.getEmployeeID(),
					mapEmployeeId.get(value.getEmployeeID()).getCode(),
					mapEmployeeId.get(value.getEmployeeID()).getName(), 
					value.getAlarmValueDate(), 
					mapTextResourceToEnum.get(value.getClassification()).value,
					value.getClassification(),
					value.getAlarmItem(),
					value.getAlarmValueMessage(),
					value.getComment().orElse(null),null);
			result.add(itemResult);
		}
		return result;
	}
	/**
	 * アラーム: 集計処理
	 * @param baseDate システム日付
	 * @param companyID　ログイン会社ID
	 * @param listEmployee　List＜社員ID＞
	 * @param periodByCategory　List＜カテゴリ別期間＞
	 * @param eralCate　List＜カテゴリ別アラームチェック条件＞
	 * @param checkConList　List＜チェック条件＞
	 * @param counter
	 * @param shouldStop
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<AlarmExtraValueWkReDto> processAlarmListWorkRecordV2(GeneralDate baseDate, String companyID, List<EmployeeSearchDto> listEmployee, 
			List<PeriodByAlarmCategory> periodByCategory, List<AlarmCheckConditionByCategory> eralCate,
			List<CheckCondition> checkConList, Consumer<Integer> counter, Supplier<Boolean> shouldStop) {
		
		List<ValueExtractAlarm> valueList = new ArrayList<>();
		
		// get list workplaceId and hierarchyCode 
		List<String> listWorkplaceId = listEmployee.stream().map(e -> e.getWorkplaceId()).filter(wp -> wp != null).distinct().collect(Collectors.toList());
//		Map<String, WkpConfigAtTimeAdapterDto> hierarchyWPMap = syWorkplaceAdapter.findByWkpIdsAtTime(companyID, baseDate, listWorkplaceId)
//																			.stream().collect(Collectors.toMap(WkpConfigAtTimeAdapterDto::getWorkplaceId, x->x));
		//[No.560]職場IDから職場の情報をすべて取得する
		List<WorkPlaceInforExport> wkpExportList = this.workplaceAdapter.getWorkplaceInforByWkpIds(companyID, listWorkplaceId, baseDate);
		
		// Map employeeID to EmployeeSearchDto object
		Map<String, EmployeeSearchDto> mapEmployeeId = listEmployee.stream().collect(Collectors.toMap(EmployeeSearchDto::getId, x->x));
		
		// MAP Enum AlarmCategory 
		Map<String, AlarmCategory> mapTextResourceToEnum = this.mapTextResourceToEnum();

		valueList.addAll(extractService.processV2(companyID, checkConList, periodByCategory, 
				listEmployee, eralCate, counter, shouldStop));
		
		//Convert from ValueExtractAlarm to AlarmExtraValueWkReDto
		return valueList.stream().map(value -> {
			return new AlarmExtraValueWkReDto(value.getWorkplaceID().orElse(null),
					value.getWorkplaceID().isPresent() && wkpExportList.stream().filter(x -> value.getWorkplaceID().get().equals(x.getWorkplaceId())).findFirst().isPresent() ?
							wkpExportList.stream().filter(x -> value.getWorkplaceID().get().equals(x.getWorkplaceId())).findFirst().get().getHierarchyCode() : "",
					mapEmployeeId.get(value.getEmployeeID()).getWorkplaceName(), 
					value.getEmployeeID(),
					mapEmployeeId.get(value.getEmployeeID()).getCode(),
					mapEmployeeId.get(value.getEmployeeID()).getName(), 
					value.getAlarmValueDate(), 
					mapTextResourceToEnum.get(value.getClassification()).value,
					value.getClassification(),
					value.getAlarmItem(),
					value.getAlarmValueMessage(),
					value.getComment().orElse(null),
					value.getCheckedValue().orElse(null)
					);
		}).collect(Collectors.toList());
	}
	
	private Map<String, AlarmCategory> mapTextResourceToEnum(){
		Map<String, AlarmCategory> map = new HashMap<String, AlarmCategory>();
		map.put(TextResource.localize("KAL010_1"), AlarmCategory.DAILY);
		map.put(TextResource.localize("KAL010_62"), AlarmCategory.SCHEDULE_4WEEK);
		map.put(TextResource.localize("KAL010_100"), AlarmCategory.MONTHLY);
		map.put(TextResource.localize("KAL010_208"), AlarmCategory.AGREEMENT);
		map.put(TextResource.localize("KAL010_250"), AlarmCategory.MULTIPLE_MONTH);
		map.put(AlarmCategory.ATTENDANCE_RATE_FOR_HOLIDAY.nameId, AlarmCategory.ATTENDANCE_RATE_FOR_HOLIDAY);
		return map;
	} 
	
	/**
	 * 集計処理
	 * @param cid 会社ID
	 * @param pattentCd　パターンコード
	 * @param pattentName　パターン名称
	 * @param lstCategoryPeriod　List<カテゴリ別期間>
	 * @param lstSid　List<従業員>
	 * @param runCode　自動実行コード　（Default：　”Z”）
	 * @param counter
	 * @param shouldStop
	 * @return 
	 */
	public List<AlarmListResultDto> processAlarmListResult(String cid, String pattentCd, String pattentName,
			List<PeriodByAlarmCategory> lstCategoryPeriod,List<String> lstSid, String runCode,
			Consumer<Integer> counter, Supplier<Boolean> shouldStop){
		List<AlarmListResultDto> result = new ArrayList<>();
		//パラメータ．パターンコードをもとにドメインモデル「アラームリストパターン設定」を取得する
		Optional<AlarmPatternSetting> findByAlarmPatternCode = alPatternSettingRepo.findByAlarmPatternCode(cid, pattentCd);
		if(!findByAlarmPatternCode.isPresent()) {
			throw new BusinessException("Msg_2059", pattentCd);
		}
		//ドメインモデル「カテゴリ別アラームチェック条件」を取得
		List<AlarmCheckConditionByCategory> lstCondCate = new ArrayList<>();
		findByAlarmPatternCode.get().getCheckConList().stream().forEach(x->{
			List<AlarmCheckConditionByCategory> lstCond = checkConditionRepo.findByCategoryAndCode(cid, 
					x.getAlarmCategory().value, 
					x.getCheckConditionList());
			lstCondCate.addAll(lstCond);
		});
		if(lstCondCate.isEmpty()) {
			throw new BusinessException("Msg_2038");
		}
		
		List<AlarmExtracResult> lstExtracResult = new ArrayList<>();
		List<CategoryCondValueDto> lstValueDto = new ArrayList<>();
		lstCondCate.stream().forEach(x ->{
			DatePeriod datePeriod = new DatePeriod(GeneralDate.today(), GeneralDate.today());
			//期間条件を絞り込む TODO can xem lai voi truong hop 36
			List<PeriodByAlarmCategory> periodCheck = lstCategoryPeriod.stream()
					.filter(y -> y.getCategory() == x.getCategory())
					.collect(Collectors.toList());
			if(!periodCheck.isEmpty()) {
				datePeriod = new DatePeriod(periodCheck.get(0).getStartDate(),periodCheck.get(0).getEndDate());
			}
			List<String> lstSidTmp = new ArrayList<>(lstSid);
			//対象者をしぼり込む(レスポンス改善)
			if(x.getExtractionCondition() != null) {
				lstSidTmp.clear();
				Map<String, List<RegulationInfoEmployeeResult>> listTargetMap = erCheckAdapter.filterEmployees(datePeriod
						,lstSid
						,Arrays.asList(x.getExtractTargetCondition()));
				lstSidTmp.addAll(listTargetMap.get(x.getExtractTargetCondition().getId())
						.stream().map(c -> c.getEmployeeId())
						.collect(Collectors.toList()));
			}
			if(!lstSidTmp.isEmpty()) {
				List<AlarmListCheckInfor> lstCheckType = new ArrayList<>();
				//List<ResultOfEachCondition> lstResult = new ArrayList<>();
				//[RQ189] 社員ID（List）と指定期間から所属職場履歴を取得
				List<WorkPlaceHistImport> getWplByListSidAndPeriod = wpAdapter.getWplByListSidAndPeriod(lstSidTmp, datePeriod);
				//[RQ588]社員の指定期間中の所属期間を取得する
				List<StatusOfEmployeeAdapter> lstStatusEmp = syCompAdapter.getAffCompanyHistByEmployee(lstSidTmp, datePeriod);
				//ループ中のカテゴリ別アラームチェック条件．カテゴリをチェック
				switch (x.getCategory()) {
				case SCHEDULE_DAILY:
					
				case SCHEDULE_WEEKLY:
					
				case SCHEDULE_4WEEK:
					
				case SCHEDULE_MONTHLY:
					
				case SCHEDULE_YEAR:
					
				case DAILY:
					
				case WEEKLY:
					
				case MONTHLY:
					
				case APPLICATION_APPROVAL:
					
				case MULTIPLE_MONTH:
					
				case ANY_PERIOD:
					
				case ATTENDANCE_RATE_FOR_HOLIDAY:
					
				case AGREEMENT:
					
				case MAN_HOUR_CHECK:
					
				case MASTER_CHECK:
					
					default:
						
				
				}
				
				//List＜チェック種類、コード＞に追加
				CategoryCondValueDto valueDto = new CategoryCondValueDto(x.getCategory().value, x.getCode().v(), lstCheckType);
				lstValueDto.add(valueDto);
				//「アラーム抽出結果」を作成してList<アラーム抽出結果＞に追加
				AlarmExtracResult extracResult = new AlarmExtracResult(x.getCode().v(), x.getCategory().value, new ArrayList<>());
				lstExtracResult.add(extracResult);
				//TODO 抽出処理停止フラグが立っているかチェックする(check xem có flag dừng xử lý extract hay k)
				
			}
			
		});
		AlarmPatternExtractResult alarmResult = new AlarmPatternExtractResult(cid, runCode, pattentCd, pattentName, lstExtracResult);
		//アラーム（トップページ）永続化の処理
		this.createAlarmToppage(alarmResult, lstSid, lstValueDto, lstCategoryPeriod, pattentName);
		return result;
			
		
	}
	
	private void createAlarmToppage(AlarmPatternExtractResult alarmResult,//今回のアラーム結果
			List<String> lstSid, 
			List<CategoryCondValueDto> lstCheckType,
			List<PeriodByAlarmCategory> lstCategoryPeriod, String patternName) {
		String cid = alarmResult.getCID();
		String runCode = alarmResult.getRunCode();
		String pattentCd = alarmResult.getPatternCode();
		Optional<AlarmPatternExtractResult> optAlarmPattern = alarmRepos.optAlarmPattern(cid, runCode, pattentCd); //存在しているアラーム
		if(!optAlarmPattern.isPresent()) {
			alarmResult.setPatternName(patternName);
			alarmRepos.addAlarmPattern(alarmResult);
		} else {
			List<AlarmExtracResult> lstExResultInsert = new ArrayList<>();
			List<AlarmExtracResult> lstExResultDelete = new ArrayList<>();
			AlarmPatternExtractResult alarmPattern = optAlarmPattern.get();
			
			//①今回のアラーム結果がデータベースに存在してない場合データベースのデータを追加
			
			//②今回のアラーム結果がないがデータベースに存在している場合データベースを削除
			//③データベースに存在しているアラームが今回のアラーム結果がある場合データがそのまま存在
			lstCheckType.stream().forEach(x -> {
				//TODO can phai debug de xem lai dinh dang ngay thang
				List<PeriodByAlarmCategory> period = lstCategoryPeriod.stream().filter(p -> p.getCategory() == p.getCategory())
						.collect(Collectors.toList());
				//カテゴリ、アラームリストチェック条件コードを絞り込む
				List<AlarmExtracResult> lstExtracResultDB = alarmPattern.getLstExtracResult().stream()
						.filter(ex -> ex.getCategory() == x.getCategory() && ex.getCoditionCode().equals(x.getCondCode()))
						.collect(Collectors.toList());
				List<AlarmExtracResult> lstExtracResultNew = alarmResult.getLstExtracResult().stream()
						.filter(ex -> ex.getCategory() == x.getCategory() && ex.getCoditionCode().equals(x.getCondCode()))
						.collect(Collectors.toList());
				if(lstExtracResultDB.isEmpty()) {
					lstExResultInsert.addAll(lstExtracResultNew);
				} else {
					x.getMapCondCdCheckNoType().stream().forEach(y ->{
						List<ResultOfEachCondition> lstCondResultDB = new ArrayList<>();
						List<ResultOfEachCondition> lstCondResultNew = new ArrayList<>();
						//チェック種類、アラームリストチェック条件のNo・コードを絞り込む
						lstExtracResultDB.stream().forEach(ex ->{
							List<ResultOfEachCondition> lstCondResultDBT = ex.getLstResult().stream()
									.filter(co -> co.getCheckType() == y.getChekType() && co.getNo().equals(y.getNo()))
									.collect(Collectors.toList());							
							lstCondResultDB.addAll(lstCondResultDBT);							
						});	
						
						lstExtracResultNew.stream().forEach(ex -> {
							List<ResultOfEachCondition> lstCondResultNewT = ex.getLstResult().stream()
									.filter(co -> co.getCheckType() == y.getChekType() && co.getNo().equals(y.getNo()))
									.collect(Collectors.toList());							
							lstCondResultNew.addAll(lstCondResultNewT);	
						});
						if(lstCondResultNew.isEmpty() && !lstCondResultDB.isEmpty()) {
							AlarmExtracResult extracResultDelete = new AlarmExtracResult(x.getCondCode(), x.getCategory(), lstCondResultDB);
							lstExResultDelete.add(extracResultDelete);
						} else if(!lstCondResultNew.isEmpty() && lstCondResultDB.isEmpty()) {
							AlarmExtracResult extracResultInsert = new AlarmExtracResult(x.getCondCode(), x.getCategory(), lstCondResultNew);
							lstExResultInsert.add(extracResultInsert);
						} else if (!lstCondResultNew.isEmpty() && !lstCondResultDB.isEmpty()) {
							List<ExtractionResultDetail> lstResultDetailDB = new ArrayList<>();
							List<ExtractionResultDetail> lstResultDetailNew = new ArrayList<>();
							//社員ID、日付から絞り込む
							lstCondResultDB.stream().forEach(de -> {
								List<ExtractionResultDetail> lstResultDetailDBT = de.getLstResultDetail().stream()
										.filter(dt -> lstSid.contains(dt.getSID())
												&& (!period.isEmpty() 
														&& dt.getPeriodDate().getStartDate().afterOrEquals(period.get(0).getStartDate())
														&& dt.getPeriodDate().getStartDate().beforeOrEquals(period.get(0).getEndDate())))
										.collect(Collectors.toList());
								lstResultDetailDB.addAll(lstResultDetailDBT);
							});
							
							lstCondResultNew.stream().forEach(de -> {
								List<ExtractionResultDetail> lstResultDetailNewT = de.getLstResultDetail().stream()
										.filter(dt -> lstSid.contains(dt.getSID())
												&& (!period.isEmpty() 														
														&& dt.getPeriodDate().getStartDate().afterOrEquals(period.get(0).getStartDate())
														&& dt.getPeriodDate().getStartDate().beforeOrEquals(period.get(0).getEndDate())))
										.collect(Collectors.toList());
								lstResultDetailNew.addAll(lstResultDetailNewT);
							});
							
							if(lstResultDetailDB.isEmpty() && !lstResultDetailNew.isEmpty()) {
								ResultOfEachCondition eachCondNew = new ResultOfEachCondition(y.getChekType(), y.getNo(), lstResultDetailNew);
								AlarmExtracResult extracResultInsert = new AlarmExtracResult(x.getCondCode(), x.getCategory(), Arrays.asList(eachCondNew));
								lstExResultInsert.add(extracResultInsert);								
							} else if (!lstResultDetailDB.isEmpty() && lstResultDetailNew.isEmpty()){
								ResultOfEachCondition eachCondDel = new ResultOfEachCondition(y.getChekType(), y.getNo(), lstResultDetailDB);
								AlarmExtracResult extracResultDel = new AlarmExtracResult(x.getCondCode(), x.getCategory(), Arrays.asList(eachCondDel));
								lstExResultDelete.add(extracResultDel);
							} else {
								List<ExtractionResultDetail> lstDetailIns = new ArrayList<>();
								List<ExtractionResultDetail> lstDetailDel = new ArrayList<>();
								
								lstResultDetailDB.stream().forEach(db -> {
									List<ExtractionResultDetail> lstDel = lstResultDetailNew.stream()
											.filter(news -> db.getSID().equals(news.getSID())
													&& db.getPeriodDate().getStartDate().equals(news.getPeriodDate().getStartDate()))
											.collect(Collectors.toList());
									if(lstDel.isEmpty()) {
										lstDetailDel.add(db);
									}					
								});
								ResultOfEachCondition eachCondDel = new ResultOfEachCondition(y.getChekType(), y.getNo(), lstDetailDel);
								AlarmExtracResult extracResultDel = new AlarmExtracResult(x.getCondCode(), x.getCategory(), Arrays.asList(eachCondDel));
								lstExResultDelete.add(extracResultDel);
								
								lstResultDetailNew.stream().forEach(news -> {
									List<ExtractionResultDetail> lstnew = lstResultDetailDB.stream()
											.filter(db -> db.getSID().equals(db.getSID())
													&& db.getPeriodDate().getStartDate().equals(db.getPeriodDate().getStartDate()))
											.collect(Collectors.toList());
									if(lstnew.isEmpty()) {
										lstDetailIns.add(news);
									}					
								});
								ResultOfEachCondition eachCondIns = new ResultOfEachCondition(y.getChekType(), y.getNo(), lstDetailIns);
								AlarmExtracResult extracResultIns = new AlarmExtracResult(x.getCondCode(), x.getCategory(), Arrays.asList(eachCondIns));
								lstExResultInsert.add(extracResultIns);
							}
							
						}
						
					});
					
				}
				
			});
			if(!lstExResultInsert.isEmpty()) {
				AlarmPatternExtractResult alarmInsert = new AlarmPatternExtractResult(cid, runCode, pattentCd, patternName, lstExResultInsert);
				alarmRepos.addAlarmPattern(alarmInsert);
			}
			if(!lstExResultDelete.isEmpty()) {
				AlarmPatternExtractResult alarmDelete = new AlarmPatternExtractResult(cid, runCode, pattentCd, patternName, lstExResultDelete);
				alarmRepos.deleteAlarmPattern(alarmDelete);
			}
		}
	}

}
