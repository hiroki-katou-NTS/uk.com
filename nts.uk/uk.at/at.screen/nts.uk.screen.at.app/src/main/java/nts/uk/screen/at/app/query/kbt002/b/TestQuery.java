package nts.uk.screen.at.app.query.kbt002.b;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.processexecution.UpdateProcessAutoExecution;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLog;
import nts.uk.ctx.at.function.dom.processexecution.listempautoexec.ListEmpAutoExec;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.sys.assist.dom.storage.BusinessName;
import nts.uk.ctx.sys.assist.dom.storage.DataStoragePatternSetting;
import nts.uk.ctx.sys.assist.dom.storage.DataStoragePatternSettingRepository;
import nts.uk.ctx.sys.assist.dom.storage.EmployeeCode;
import nts.uk.ctx.sys.assist.dom.storage.FileCompressionPassword;
import nts.uk.ctx.sys.assist.dom.storage.ManualSetOfDataSave;
import nts.uk.ctx.sys.assist.dom.storage.ManualSetOfDataSaveHolder;
import nts.uk.ctx.sys.assist.dom.storage.ManualSetOfDataSaveRepository;
import nts.uk.ctx.sys.assist.dom.storage.ManualSetOfDataSaveService;
import nts.uk.ctx.sys.assist.dom.storage.StorageClassification;
import nts.uk.ctx.sys.assist.dom.storage.TargetCategory;
import nts.uk.ctx.sys.assist.dom.storage.TargetEmployees;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class TestQuery {

	@Inject
	private DataStoragePatternSettingRepository dataStoragePatternSettingRepository;

	@Inject
	private ManualSetOfDataSaveRepository manualSetOfDataSaveRepository;

	@Inject
	private EmployeeDataMngInfoRepository employeeDataMngInfoRepository;

	@Inject
	private ListEmpAutoExec listEmpAutoExec;
	
	@Inject
	private ManualSetOfDataSaveService manualSetOfDataSaveService;
	
	@Inject
	private ProcessExecutionLogRepository processExecutionLogRepository;

	public boolean processDataSave(String execId, String companyId, UpdateProcessAutoExecution procExec,
			ProcessExecutionLog procExecLog) {
		/**
		 * Update procExecLog
		 */

		if (procExec.getExecSetting().getSaveData().getSaveDataCls().equals(NotUseAtr.NOT_USE)) {
			/**
			 * Update Log
			 */

			return true;
		}
		// するの場合
		// アルゴリズム「自動保存準備」を実行する
		// ドメインモデル「データ保存のパターン設定」を取得する
		Optional<DataStoragePatternSetting> optPatternSetting = this.dataStoragePatternSettingRepository
				.findByContractCdAndPatternCd(AppContexts.user().contractCode(),
						procExec.getExecSetting().getSaveData().getPatternCode().get().v());
		if (optPatternSetting.isPresent()) {
			DataStoragePatternSetting patternSetting = optPatternSetting.get();
			// ドメインモデル「データ保存の手動設定」を追加する
			int saveType = StorageClassification.AUTO.value;
			String dataStorageProcessingId = IdentifierUtil.randomUniqueId();
			String saveName = patternSetting.getPatternName().v();
			int passwordAvailability = patternSetting.getWithoutPassword().value;
			String password = patternSetting.getPatternCompressionPwd().map(FileCompressionPassword::v).orElse(null);
			GeneralDate refDate = GeneralDate.today();
			GeneralDateTime executionDateTime = GeneralDateTime.now();
			String suppleExplanation = patternSetting.getPatternSuppleExplanation().orElse(null);
			int presenceOfEmployee = NotUseAtr.USE.value;
			String practitioner = AppContexts.user().employeeId();
			List<TargetCategory> targetCategories = patternSetting.getCategories().stream().map(
					item -> new TargetCategory(dataStorageProcessingId, item.getCategoryId().v(), item.getSystemType()))
					.collect(Collectors.toList());

			// MOCK DATA
			GeneralDate daySaveStartDate = GeneralDate.today().addMonths(-1);
			GeneralDate daySaveEndDate = GeneralDate.today();
			String monthSaveStartDate = null;
			String monthSaveEndDate = null;
			Integer startYear = null;
			Integer endYear = null;

			// 更新処理自動実行の実行対象社員リストを取得する
			List<String> empIds = this.listEmpAutoExec.getListEmpAutoExec(companyId,
					new DatePeriod(daySaveStartDate, daySaveEndDate), procExec.getExecScope().getExecScopeCls(),
					Optional.of(procExec.getExecScope().getWorkplaceIdList()), Optional.of(Collections.emptyList()));
			List<TargetEmployees> targetEmployees = this.employeeDataMngInfoRepository.findByIds(empIds).stream()
					.map(item -> new TargetEmployees(dataStorageProcessingId, item.getEmployeeId(),
							new BusinessName(item.getEmployeeName()), new EmployeeCode(item.getEmployeeCode())))
					.collect(Collectors.toList());
			// ドメインモデル「データ保存の対象社員」に保存する
			ManualSetOfDataSave manualSet = new ManualSetOfDataSave(companyId, dataStorageProcessingId,
					passwordAvailability, saveName, refDate, password, executionDateTime, daySaveEndDate,
					daySaveStartDate, monthSaveEndDate, monthSaveStartDate, suppleExplanation, endYear, startYear,
					presenceOfEmployee, practitioner, saveType, targetEmployees, targetCategories);
			this.manualSetOfDataSaveRepository.addManualSetting(manualSet);
			this.manualSetOfDataSaveService.start(new ManualSetOfDataSaveHolder(manualSet, patternSetting.getPatternCode().v()));
			
			// ドメインモデル「更新処理自動実行ログ」を取得しチェックする（中断されている場合は更新されているため、最新の情報を取得する）
//			Optional<ProcessExecutionLog> optProcExecLog = 
		}
		return true;
	}
}
