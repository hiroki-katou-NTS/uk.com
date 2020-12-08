package nts.uk.ctx.sys.assist.pub.command.autosetting;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.app.command.autosetting.deletion.AutoDeletionPreparationCommandHandler;
import nts.uk.ctx.sys.assist.app.command.autosetting.storage.AutoStoragePreparationCommandHandler;
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
	private EmpBasicInfoAdapter empBasicInfoAdapter;
	
	@Inject
	private ManualSetOfDataSaveService manualSetOfDataSaveService;
	
	@Override
	public void autoStoragePrepare(String patternCode, List<String> empIds) {
		ManualSetOfDataSave manualSet = this.autoStoragePreparationCommandHandler.handle(patternCode);
		// 更新処理自動実行の実行対象社員リストを取得する
		List<TargetEmployees> targetEmployees = this.empBasicInfoAdapter
				.getEmpBasicInfo(AppContexts.user().companyId(), empIds).stream()
				.map(item -> new TargetEmployees(manualSet.getStoreProcessingId(), item.getEmployeeId(),
						new BusinessName(item.getBusinessName()), new EmployeeCode(item.getEmployeeCode())))
				.collect(Collectors.toList());
		manualSet.setEmployees(targetEmployees);
		
		// ドメインモデル「データ保存の対象社員」に保存する
		this.manualSetOfDataSaveRepository.addManualSetting(manualSet);
		// アルゴリズム「サーバー手動保存処理」を実行する
		this.manualSetOfDataSaveService.start(new ManualSetOfDataSaveHolder(manualSet, patternCode));
	}

	@Override
	public void autoDeletionPrepare(String patternCode) {
		this.autoDeletionPreparationCommandHandler.handle(patternCode);
	}
}
