package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.task.AsyncTask;
import nts.arc.task.data.TaskDataSetter;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordAdapter;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordImport;
import nts.uk.ctx.at.record.dom.adapter.generalinfo.dtoimport.EmployeeGeneralInfoImport;
import nts.uk.ctx.at.record.dom.adapter.generalinfo.dtoimport.ExJobTitleHistItemImport;
import nts.uk.ctx.at.record.dom.adapter.generalinfo.dtoimport.ExJobTitleHistoryImport;
import nts.uk.ctx.at.record.dom.adapter.generalinfo.dtoimport.ExWorkPlaceHistoryImport;
import nts.uk.ctx.at.record.dom.adapter.generalinfo.dtoimport.ExWorkplaceHistItemImport;
import nts.uk.ctx.at.record.dom.adapter.specificdatesetting.RecSpecificDateSettingAdapter;
import nts.uk.ctx.at.record.dom.adapter.specificdatesetting.RecSpecificDateSettingImport;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.calculationsetting.StampReflectionManagement;
import nts.uk.ctx.at.record.dom.calculationsetting.repository.StampReflectionManagementRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ExecutionAttr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.MasterList;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.PeriodInMasterList;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainServiceImpl.ProcessState;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.algorithm.CreateEmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfo;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfoRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageResource;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLog;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.TargetPersonRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExeStateOfCalAndSum;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionContent;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionStatus;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.BPSettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.BPUnitUseSettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.CPBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.WPBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BPUnitUseSetting;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.CompanyBonusPaySetting;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.WorkplaceBonusPaySetting;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.BaseAutoCalSetting;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegister;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.service.WorkingConditionService;
import nts.uk.ctx.at.shared.dom.workrule.overtime.AutoCalculationSetService;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class CreateDailyResultDomainServiceImpl implements CreateDailyResultDomainService {

	// @Inject
	// private EmpCalAndSumExeLogRepository empCalAndSumExeLogRepository;

	@Inject
	private CreateDailyResultEmployeeDomainService createDailyResultEmployeeDomainService;

	@Inject
	private TargetPersonRepository targetPersonRepository;

	@Inject
	private EmployeeGeneralInfoService employeeGeneralInfoService;

	@Inject
	private UpdateLogInfoWithNewTransaction updateLogInfoWithNewTransaction;

	@Inject
	private StampReflectionManagementRepository stampReflectionManagementRepository;

	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;

	@Inject
	private WorkingConditionRepository workingConditionRepo;

	@Inject
	private AffWorkplaceAdapter affWorkplaceAdapter;

	@Inject
	private BPUnitUseSettingRepository bPUnitUseSettingRepository;

	@Inject
	private WorkingConditionService workingConditionService;

	@Inject
	private WPBonusPaySettingRepository wPBonusPaySettingRepository;

	@Inject
	private CPBonusPaySettingRepository cPBonusPaySettingRepository;

	@Inject
	private AutoCalculationSetService autoCalculationSetService;

	@Inject
	private BPSettingRepository bPSettingRepository;

	@Inject
	private RecSpecificDateSettingAdapter recSpecificDateSettingAdapter;

	@Inject
	private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;

	@Inject
	private EmployeeRecordAdapter employeeRecordAdapter;

	@Inject
	private CreateEmployeeDailyPerError createEmployeeDailyPerError;

	@Inject
	private ErrMessageInfoRepository errMessageInfoRepository;

	@Inject
	private ManagedParallelWithContext managedParallelWithContext;

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public ProcessState createDailyResult(AsyncCommandHandlerContext asyncContext, List<String> emloyeeIds,
			DatePeriod periodTime, ExecutionAttr executionAttr, String companyId, String empCalAndSumExecLogID,
			Optional<ExecutionLog> executionLog) {

		val dataSetter = asyncContext.getDataSetter();

		ProcessState status = ProcessState.SUCCESS;

		// AsyncCommandHandlerContext<SampleCancellableAsyncCommand> ABC;

		// ③日別実績の作成処理
		if (executionLog.isPresent()) {

			ExecutionContent executionContent = executionLog.get().getExecutionContent();

			if (executionContent == ExecutionContent.DAILY_CREATION) {

				// ④ログ情報（実行ログ）を更新する
				updateLogInfoWithNewTransaction.updateLogInfo(empCalAndSumExecLogID, 0,
						ExecutionStatus.PROCESSING.value);

				Optional<StampReflectionManagement> stampReflectionManagement = this.stampReflectionManagementRepository
						.findByCid(companyId);

				// マスタ情報を取得する
				// Imported(就業)「社員の履歴情報」 を取得する
				// reqList401
				EmployeeGeneralInfoImport employeeGeneralInfoImport = this.employeeGeneralInfoService
						.getEmployeeGeneralInfo(emloyeeIds, periodTime);

				// Imported(勤務実績)「期間分の勤務予定」を取得する
				// RequestList444 - TODO

				// ...................................
				// 社員ID（List）と期間から労働条件を取得する
				List<WorkingConditionItem> workingConditionItems = this.workingConditionItemRepository
						.getBySidsAndDatePeriod(emloyeeIds, periodTime);

				Map<String, List<WorkingConditionItem>> mapWOrking = workingConditionItems.parallelStream()
						.collect(Collectors.groupingBy(WorkingConditionItem::getEmployeeId));

				// Map<Sid, Map<HistoryID, List<WorkingConditionItem>>>
				Map<String, Map<String, WorkingConditionItem>> mapWorkingConditionItem = mapWOrking.entrySet()
						.parallelStream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().stream()
								.collect(Collectors.toMap(WorkingConditionItem::getHistoryId, Function.identity()))));

				List<WorkingCondition> workingConditions = workingConditionRepo.getBySidsAndDatePeriod(emloyeeIds,
						periodTime);

				// Map<Sid, List<DateHistoryItem>>
				Map<String, List<DateHistoryItem>> mapLstDateHistoryItem = workingConditions.parallelStream().collect(
						Collectors.toMap(WorkingCondition::getEmployeeId, WorkingCondition::getDateHistoryItem));

				// Map<Sid, Map<HistoryID, DateHistoryItem>>
				Map<String, Map<String, DateHistoryItem>> mapDateHistoryItem = mapLstDateHistoryItem.entrySet().stream()
						.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().stream()
								.collect(Collectors.toMap(DateHistoryItem::identifier, Function.identity()))));

				// 会社IDと期間から期間内の職場構成期間を取得する
				// ReqList485
				List<DatePeriod> workPlaceHistory = this.affWorkplaceAdapter.getLstPeriod(companyId, periodTime);

				StateHolder stateHolder = new StateHolder(emloyeeIds.size());

				/** 並列処理、AsyncTask */
				// Create thread pool.
				// ExecutorService executorService =
				// Executors.newFixedThreadPool(20);
				// CountDownLatch countDownLatch = new
				// CountDownLatch(emloyeeIds.size());

				this.managedParallelWithContext.forEach(emloyeeIds, employeeId -> {
					if (asyncContext.hasBeenRequestedToCancel()) {
						// asyncContext.finishedAsCancelled();
						stateHolder.add(ProcessState.INTERRUPTION);
						dataSetter.updateData("dailyCreateStatus", ExeStateOfCalAndSum.STOPPING.nameId);
						return;
						// return ProcessState.INTERRUPTION;
					}
					// AsyncTask task =
					// AsyncTask.builder().withContexts().keepsTrack(false).setDataSetter(dataSetter)
					// .threadName(this.getClass().getName()).build(() -> {
					// 社員の日別実績を計算
					if (stateHolder.isInterrupt()) {
						// Count down latch.
						// countDownLatch.countDown();
						return;
					}

					// 日別実績の作成入社前、退職後を期間から除く
					DatePeriod newPeriod = this.checkPeriod(companyId, employeeId, periodTime, empCalAndSumExecLogID);

					if (newPeriod != null) {

						// 対象期間 = periodTime
						// 職場構成期間 = workPlaceHistory
						// 社員の履歴情報 = employeeGeneralInfoImport
						// 労働条件 = workingConditionItems ( Map<String,
						// List<DateHistoryItem>> mapLstDateHistoryItem )
						// 特定日、加給、計算区分情報を取得する
						// 履歴が区切られている年月日を判断する
						List<GeneralDate> historySeparatedList = this.historyIsSeparated(newPeriod, workPlaceHistory,
								employeeGeneralInfoImport, mapLstDateHistoryItem, employeeId);

						PeriodInMasterList periodInMasterList = new PeriodInMasterList();
						List<MasterList> masterLists = new ArrayList<>();

						if (historySeparatedList.size() > 0) {
							for (int i = 0; i < historySeparatedList.size(); i++) {
								GeneralDate strDate = historySeparatedList.get(i);
								GeneralDate endDate = (i == historySeparatedList.size() - 1) ? newPeriod.end()
										: historySeparatedList.get(i + 1).addDays(-1);

								// get 職場履歴一覧
								List<ExWorkPlaceHistoryImport> exWorkPlaceHistoryImports = employeeGeneralInfoImport
										.getExWorkPlaceHistoryImports();
								// get 職位履歴一覧
								List<ExJobTitleHistoryImport> exJobTitleHistoryImports = employeeGeneralInfoImport
										.getExJobTitleHistoryImports();
								// filter follow employeeId
								Optional<ExWorkPlaceHistoryImport> optional = exWorkPlaceHistoryImports.stream()
										.filter(item -> item.getEmployeeId().equals(employeeId)).findFirst();
								Optional<ExJobTitleHistoryImport> jobTitleOptional = exJobTitleHistoryImports.stream()
										.filter(item -> item.getEmployeeId().equals(employeeId)).findFirst();
								// get workPlaceItem
								List<ExWorkplaceHistItemImport> workplaceItems = optional.isPresent()
										? optional.get().getWorkplaceItems() : new ArrayList<>();
								// get jobTitleHistItem
								List<ExJobTitleHistItemImport> jobTitleItems = jobTitleOptional.isPresent()
										? jobTitleOptional.get().getJobTitleItems() : new ArrayList<>();
								// filter : DatePeriod of workplaceItems has
								// contains start date
								Optional<ExWorkplaceHistItemImport> itemImport = workplaceItems.stream()
										.filter(item -> item.getPeriod().contains(strDate)).findFirst();
								// filter : DatePeriod of jobTitleItems has
								// contains start date
								Optional<ExJobTitleHistItemImport> jobTitleItemImport = jobTitleItems.stream()
										.filter(item -> item.getPeriod().contains(strDate)).findFirst();
								// get workPlaceId
								String workPlaceId = itemImport.isPresent() ? itemImport.get().getWorkplaceId() : null;
								// get jobTitleId
								String jobTitleId = jobTitleItemImport.isPresent()
										? jobTitleItemImport.get().getJobTitleId() : null;

								if (workPlaceId != null && jobTitleId != null) {
									// 職場IDと基準日から上位職場を取得する
									// reqList 496
									List<String> workPlaceIdList = this.affWorkplaceAdapter
											.findParentWpkIdsByWkpId(companyId, workPlaceId, strDate);
									// 特定日設定を取得する
									// Reqlist 490
									RecSpecificDateSettingImport specificDateSettingImport = this.recSpecificDateSettingAdapter
											.specificDateSettingServiceByListWpl(companyId, workPlaceIdList, strDate);

									// 会社職場個人の加給設定を取得する
									Optional<BonusPaySetting> bonusPaySettingOpt = this.reflectBonusSetting(companyId,
											employeeId, strDate, workPlaceIdList);

									// 自動計算設定の取得
									BaseAutoCalSetting baseAutoCalSetting = this.autoCalculationSetService
											.getAutoCalculationSetting(companyId, employeeId, strDate, workPlaceId,
													jobTitleId, Optional.of(workPlaceIdList));

									// set data in PeriodInMasterList
									DatePeriod datePeriod = new DatePeriod(strDate, endDate);

									MasterList masterList = new MasterList();
									masterList.setBaseAutoCalSetting(baseAutoCalSetting);
									masterList.setBonusPaySettingOpt(bonusPaySettingOpt);
									masterList.setDatePeriod(datePeriod);
									masterList.setSpecificDateSettingImport(
											Optional.ofNullable(specificDateSettingImport));

									masterLists.add(masterList);
								}
							}
							periodInMasterList.setEmployeeId(employeeId);
							periodInMasterList.setMasterLists(masterLists);
						}

						ProcessState cStatus = createData(asyncContext, newPeriod, executionAttr, companyId,
								empCalAndSumExecLogID, executionLog, dataSetter, employeeGeneralInfoImport, stateHolder,
								employeeId, stampReflectionManagement, mapWorkingConditionItem, mapDateHistoryItem,
								periodInMasterList);
						if (cStatus == ProcessState.INTERRUPTION) {
							stateHolder.add(cStatus);
							dataSetter.updateData("dailyCreateStatus", ExeStateOfCalAndSum.STOPPING.nameId);
							// Count down latch.
							// countDownLatch.countDown();
							return;
						}

						stateHolder.add(cStatus);
						// // Count down latch.
						// //countDownLatch.countDown();
						// return;
						// });
						if (stateHolder.status.stream().filter(c -> c == ProcessState.INTERRUPTION).count() > 0) {
							dataSetter.updateData("dailyCreateStatus", ExeStateOfCalAndSum.STOPPING.nameId);
							// Count down latch.
							// countDownLatch.countDown();
							stateHolder.add(ProcessState.INTERRUPTION);
							return;
							// return ProcessState.INTERRUPTION;
						}
					}
					// executorService.submit(task);
				});
				// Wait for latch until finish.
				// try {
				// countDownLatch.await();
				// } catch (InterruptedException ie) {
				// throw new RuntimeException(ie);
				// } finally {
				// // Force shut down executor services.
				// executorService.shutdown();
				// }
				status = stateHolder.status.stream().filter(c -> c == ProcessState.INTERRUPTION).findFirst()
						.orElse(ProcessState.SUCCESS);
				if (status == ProcessState.SUCCESS) {
					dataSetter.updateData("dailyCreateCount", emloyeeIds.size());
					if (executionAttr.value == 0) {
						updateLogInfoWithNewTransaction.updateLogInfo(empCalAndSumExecLogID, 0,
								ExecutionStatus.DONE.value);
					}
				}
			} else {
				dataSetter.updateData("dailyCreateStatus", ExeStateOfCalAndSum.STOPPING.nameId);
				status = ProcessState.INTERRUPTION;
			}
		}

		return status;
	}

	private DatePeriod checkPeriod(String companyId, String employeeId, DatePeriod periodTime,
			String empCalAndSumExecLogID) {

		DatePeriod datePeriodOutput = periodTime;

		// RequestList1
		EmployeeRecordImport empInfo = employeeRecordAdapter.getPersonInfor(employeeId);

		if (empInfo == null) {
			// 社員の日別実績のエラーを作成する
			EmployeeDailyPerError employeeDailyPerError = new EmployeeDailyPerError(companyId, employeeId,
					periodTime.start(), new ErrorAlarmWorkRecordCode("S025"), new ArrayList<>());
			this.createEmployeeDailyPerError.createEmployeeError(employeeDailyPerError);

			ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeId, empCalAndSumExecLogID,
					new ErrMessageResource("020"), EnumAdaptor.valueOf(0, ExecutionContent.class), periodTime.start(),
					new ErrMessageContent(TextResource.localize("Msg_1156")));
			this.errMessageInfoRepository.add(employmentErrMes);
			return null;
		}

		if (datePeriodOutput.start().before(empInfo.getEntryDate())
				&& datePeriodOutput.end().afterOrEquals(empInfo.getEntryDate())
				&& datePeriodOutput.end().beforeOrEquals(empInfo.getRetiredDate())) {
			datePeriodOutput = new DatePeriod(empInfo.getEntryDate(), datePeriodOutput.end());
		} else if (datePeriodOutput.start().afterOrEquals(empInfo.getEntryDate())
				&& datePeriodOutput.start().beforeOrEquals(empInfo.getRetiredDate())
				&& datePeriodOutput.end().after(empInfo.getRetiredDate())) {
			datePeriodOutput = new DatePeriod(datePeriodOutput.start(), empInfo.getRetiredDate());
		} else if (datePeriodOutput.start().afterOrEquals(empInfo.getEntryDate())
				&& datePeriodOutput.end().beforeOrEquals(empInfo.getRetiredDate())) {
			datePeriodOutput = new DatePeriod(datePeriodOutput.start(), datePeriodOutput.end());
		} else if (datePeriodOutput.start().beforeOrEquals(empInfo.getEntryDate())
				&& datePeriodOutput.end().afterOrEquals(empInfo.getRetiredDate())) {
			datePeriodOutput = new DatePeriod(empInfo.getEntryDate(), empInfo.getRetiredDate());
		} else
			datePeriodOutput = null;

		return datePeriodOutput;
	}

	// 会社職場個人の加給設定を取得する
	private Optional<BonusPaySetting> reflectBonusSetting(String companyId, String employeeId, GeneralDate date,
			List<String> workPlaceIdList) {
		Optional<BonusPaySetting> bonusPaySetting = Optional.empty();

		// ドメインモデル「加給利用単位」を取得する
		Optional<BPUnitUseSetting> bPUnitUseSetting = this.bPUnitUseSettingRepository.getSetting(companyId);

		// 加給利用単位．個人使用区分
		if (bPUnitUseSetting.isPresent() && bPUnitUseSetting.get().getPersonalUseAtr() == UseAtr.USE) {
			// 社員の労働条件を取得する
			Optional<WorkingConditionItem> workingConditionItem = this.workingConditionService
					.findWorkConditionByEmployee(employeeId, date);

			if (workingConditionItem.isPresent() && workingConditionItem.get().getTimeApply().isPresent()) {
				// ドメインモデル「加給設定」を取得する
				bonusPaySetting = this.bPSettingRepository.getBonusPaySetting(companyId,
						new BonusPaySettingCode(workingConditionItem.get().getTimeApply().get().v()));
				return bonusPaySetting;
			}
		}

		// 加給利用単位．職場使用区分
		if (bPUnitUseSetting.isPresent() && bPUnitUseSetting.get().getWorkplaceUseAtr() == UseAtr.USE) {
			Optional<WorkplaceBonusPaySetting> workplaceBonusPaySetting = Optional.empty();
			for (String wPId : workPlaceIdList) {
				workplaceBonusPaySetting = this.wPBonusPaySettingRepository.getWPBPSetting(companyId,
						new WorkplaceId(wPId));
				if (workplaceBonusPaySetting.isPresent()) {
					break;
				}
			}
			if (workplaceBonusPaySetting.isPresent()) {
				bonusPaySetting = this.bPSettingRepository.getBonusPaySetting(companyId,
						workplaceBonusPaySetting.get().getBonusPaySettingCode());
				return bonusPaySetting;
			}
		}

		// ドメインモデル「会社加給設定」を取得する
		Optional<CompanyBonusPaySetting> companyBonusPaySetting = this.cPBonusPaySettingRepository
				.getSetting(companyId);

		if (companyBonusPaySetting.isPresent()) {
			bonusPaySetting = this.bPSettingRepository.getBonusPaySetting(companyId,
					companyBonusPaySetting.get().getBonusPaySettingCode());
			return bonusPaySetting;
		}

		return bonusPaySetting;
	}

	// 履歴が区切られている年月日を判断する
	private List<GeneralDate> historyIsSeparated(DatePeriod periodTime, List<DatePeriod> workPlaceHistory,
			EmployeeGeneralInfoImport employeeGeneralInfoImport,
			Map<String, List<DateHistoryItem>> mapLstDateHistoryItem, String employeeID) {
		// 履歴開始日一覧
		List<GeneralDate> historyStartDateList = new ArrayList<>();
		historyStartDateList.add(periodTime.start());
		// add all startDate
		if (workPlaceHistory.size() > 1) {
			for (DatePeriod workPlaceDate : workPlaceHistory) {
				historyStartDateList.add(workPlaceDate.start());
			}
		}

		// get 所属職場の履歴
		List<ExWorkPlaceHistoryImport> exWorkPlaceHistoryImports = employeeGeneralInfoImport
				.getExWorkPlaceHistoryImports();
		// filter 所属職場の履歴 follow employeeID
		List<ExWorkPlaceHistoryImport> newExWorkPlaceHistoryImports = exWorkPlaceHistoryImports.stream()
				.filter(item -> item.getEmployeeId().equals(employeeID)).collect(Collectors.toList());
		// get all workPlaceHistory
		List<ExWorkplaceHistItemImport> workplaceItems = new ArrayList<>();
		newExWorkPlaceHistoryImports.stream().forEach(item -> {
			workplaceItems.addAll(item.getWorkplaceItems());
		});
		// add all startDate
		if (workplaceItems.size() > 1) {
			for (ExWorkplaceHistItemImport itemImport : workplaceItems) {
				if (!historyStartDateList.stream().anyMatch(item -> item.equals(itemImport.getPeriod().start()))) {
					historyStartDateList.add(itemImport.getPeriod().start());
				}
			}
		}

		// get 所属職位の履歴
		List<ExJobTitleHistoryImport> exJobTitleHistoryImports = employeeGeneralInfoImport
				.getExJobTitleHistoryImports();
		// filter 所属職位の履歴 follow employeeID
		List<ExJobTitleHistoryImport> newExJobTitleHistoryImports = exJobTitleHistoryImports.stream()
				.filter(item -> item.getEmployeeId().equals(employeeID)).collect(Collectors.toList());
		// get all jobTitleHistory
		List<ExJobTitleHistItemImport> jobTitleItems = new ArrayList<>();
		newExJobTitleHistoryImports.stream().forEach(item -> {
			jobTitleItems.addAll(item.getJobTitleItems());
		});
		// add all startDate
		if (jobTitleItems.size() > 1) {
			for (ExJobTitleHistItemImport jobTitleHistItemImport : jobTitleItems) {
				if (!historyStartDateList.stream()
						.anyMatch(item -> item.equals(jobTitleHistItemImport.getPeriod().start()))) {
					historyStartDateList.add(jobTitleHistItemImport.getPeriod().start());
				}
			}
		}

		// 労働条件の履歴が区切られている年月日を判断する
		// filter 労働条件の履歴 follow employeeID
		if (mapLstDateHistoryItem.containsKey(employeeID)) {
			List<DateHistoryItem> dateHistoryItems = mapLstDateHistoryItem.get(employeeID);
			if (dateHistoryItems.size() > 1) {
				for (DateHistoryItem dateHistoryItem : dateHistoryItems) {
					if (!historyStartDateList.stream().anyMatch(item -> item.equals(dateHistoryItem.start()))) {
						historyStartDateList.add(dateHistoryItem.start());
					}
				}
			}
		}

		historyStartDateList.sort((item1, item2) -> item1.compareTo(item2));

		return historyStartDateList;
	}

	// @Transactional(value = TxType.SUPPORTS)
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	private ProcessState createData(AsyncCommandHandlerContext asyncContext, DatePeriod periodTime,
			ExecutionAttr executionAttr, String companyId, String empCalAndSumExecLogID,
			Optional<ExecutionLog> executionLog, TaskDataSetter dataSetter,
			EmployeeGeneralInfoImport employeeGeneralInfoImport, StateHolder stateHolder, String employeeId,
			Optional<StampReflectionManagement> stampReflectionManagement,
			Map<String, Map<String, WorkingConditionItem>> mapWorkingConditionItem,
			Map<String, Map<String, DateHistoryItem>> mapDateHistoryItem, PeriodInMasterList periodInMasterList) {

		/**
		 * 勤務種別変更時に再作成 = false reCreateWorkType 異動時に再作成 = false
		 * reCreateWorkPlace 休職・休業者再作成 = false reCreateRestTime
		 */
		ProcessState cStatus = createDailyResultEmployeeDomainService.createDailyResultEmployee(asyncContext,
				employeeId, periodTime, companyId, empCalAndSumExecLogID, executionLog, false, false, false,
				employeeGeneralInfoImport, stampReflectionManagement, mapWorkingConditionItem, mapDateHistoryItem,
				periodInMasterList);

		// 暫定データの登録
		this.interimRemainDataMngRegisterDateChange.registerDateChange(companyId, employeeId,
				periodTime.datesBetween());

		// ログ情報（実行内容の完了状態）を更新する
		updateExecutionStatusOfDailyCreation(employeeId, executionAttr.value, empCalAndSumExecLogID);

		// 状態確認
		if (cStatus == ProcessState.SUCCESS) {
			dataSetter.updateData("dailyCreateCount", stateHolder.count() + 1);
		} else {
			dataSetter.updateData("dailyCreateStatus", ExeStateOfCalAndSum.STOPPING.nameId);
			return ProcessState.INTERRUPTION;
		}
		return cStatus;
	}

	private void updateExecutionStatusOfDailyCreation(String employeeID, int executionAttr,
			String empCalAndSumExecLogID) {

		if (executionAttr == 0) {
			targetPersonRepository.update(employeeID, empCalAndSumExecLogID, ExecutionStatus.DONE.value);
		}

	}

	@AllArgsConstructor
	/**
	 * 正常終了 : 0 中断 : 1
	 */
	public enum ProcessState {
		/* 中断 */
		INTERRUPTION(0),

		/* 正常終了 */
		SUCCESS(1);

		public final int value;
	}

	class StateHolder {
		private BlockingQueue<ProcessState> status;

		StateHolder(int max) {
			status = new ArrayBlockingQueue<ProcessState>(max);
		}

		void add(ProcessState status) {
			this.status.add(status);
		}

		int count() {
			return this.status.size();
		}

		boolean isInterrupt() {
			return this.status.stream().filter(s -> s == ProcessState.INTERRUPTION).findFirst().isPresent();
		}
	}

}