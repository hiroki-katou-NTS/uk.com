package nts.uk.ctx.sys.assist.pub.command.autosetting;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.app.command.autosetting.deletion.AutoDeletionPreparationCommandHandler;
import nts.uk.ctx.sys.assist.app.command.autosetting.storage.AutoStoragePreparationCommandHandler;
import nts.uk.ctx.sys.assist.app.command.deletedata.manualsetting.ManualSetDeletionService;
import nts.uk.ctx.sys.assist.dom.deletedata.EmployeeDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.EmployeesDeletionRepository;
import nts.uk.ctx.sys.assist.dom.deletedata.ManualSetDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.ManualSetDeletionRepository;
import nts.uk.ctx.sys.assist.dom.reference.record.EmpBasicInfoAdapter;
import nts.uk.ctx.sys.assist.dom.storage.BusinessName;
import nts.uk.ctx.sys.assist.dom.storage.EmployeeCode;
import nts.uk.ctx.sys.assist.dom.storage.ManualSetOfDataSave;
import nts.uk.ctx.sys.assist.dom.storage.ManualSetOfDataSaveHolder;
import nts.uk.ctx.sys.assist.dom.storage.ManualSetOfDataSaveRepository;
import nts.uk.ctx.sys.assist.dom.storage.ManualSetOfDataSaveService;
import nts.uk.ctx.sys.assist.dom.storage.TargetEmployees;
import nts.uk.shr.com.context.AppContexts;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class AutoExecutionPreparationPubImpl implements AutoExecutionPreparationPub {

	@Inject
	private AutoStoragePreparationCommandHandler autoStoragePreparationCommandHandler;

	@Inject
	private AutoDeletionPreparationCommandHandler autoDeletionPreparationCommandHandler;

	@Inject
	private ManualSetOfDataSaveRepository manualSetOfDataSaveRepository;

	@Inject
	private ManualSetDeletionRepository manualSetDeletionRepository;

	@Inject
	private EmployeesDeletionRepository employeesDeletionRepository;

	@Inject
	private EmpBasicInfoAdapter empBasicInfoAdapter;

	@Inject
	private ManualSetOfDataSaveService manualSetOfDataSaveService;

	@Inject
	private ManualSetDeletionService manualSetDeletionService;

	@Override
	public AutoPrepareDataExport autoStoragePrepare(String patternCode) {
		ManualSetOfDataSave manualSet = this.autoStoragePreparationCommandHandler.handle(patternCode);
		// ドメインモデル「データ保存の対象社員」に保存する
		this.manualSetOfDataSaveRepository.addManualSetting(manualSet);
		return new AutoPrepareDataExport(manualSet.getStoreProcessingId(), manualSet.getDaySaveStartDate(),
				manualSet.getDaySaveEndDate());
	}

	@Override
	public void updateTargetEmployee(String storeProcessId, String patternCode, List<String> empIds) {
		// 更新処理自動実行の実行対象社員リストを取得する
		List<TargetEmployees> targetEmployees = this.empBasicInfoAdapter
				.getEmpBasicInfo(AppContexts.user().companyId(), empIds).stream()
				.map(item -> new TargetEmployees(storeProcessId, item.getEmployeeId(),
						new BusinessName(item.getBusinessName()), new EmployeeCode(item.getEmployeeCode())))
				.collect(Collectors.toList());

		ManualSetOfDataSave manualSet = this.manualSetOfDataSaveRepository.getManualSetOfDataSaveById(storeProcessId)
				.orElse(null);
		manualSet.setEmployees(targetEmployees);
		this.manualSetOfDataSaveRepository.update(manualSet);
		// アルゴリズム「サーバー手動保存処理」を実行する
		this.manualSetOfDataSaveService.start(new ManualSetOfDataSaveHolder(manualSet, patternCode));
	}

	@Override
	public AutoPrepareDataExport autoDeletionPrepare(String patternCode) {
		ManualSetDeletion manualSet = this.autoDeletionPreparationCommandHandler.handle(patternCode);

		// ドメインモデル「データ削除の手動設定」を追加する
		this.manualSetDeletionRepository.addManualSetting(manualSet);

		return new AutoPrepareDataExport(manualSet.getDelId(), manualSet.getStartDateOfDaily().orElse(null),
				manualSet.getEndDateOfDaily().orElse(null));
	}

	@Override
	public void updateEmployeeDeletion(String delId, List<String> empIds) {
		// 取得できた社員IDを社員ID（List）とする
		List<EmployeeDeletion> targetEmployees = this.empBasicInfoAdapter
				.getEmpBasicInfo(AppContexts.user().companyId(), empIds).stream()
				.map(item -> EmployeeDeletion.createFromJavatype(delId, item.getEmployeeId(), item.getEmployeeCode(),
						item.getBusinessName()))
				.collect(Collectors.toList());

		// ドメインモデル「データ削除の対象社員」を更新する
		employeesDeletionRepository.addAll(targetEmployees);

		// アルゴリズム「サーバ手動削除処理」を実行する
		this.manualSetDeletionService
				.start(this.manualSetDeletionRepository.getManualSetDeletionById(delId).orElse(null));
	}
}
