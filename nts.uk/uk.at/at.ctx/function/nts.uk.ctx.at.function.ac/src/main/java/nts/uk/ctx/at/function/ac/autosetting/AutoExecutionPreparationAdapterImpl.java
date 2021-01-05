package nts.uk.ctx.at.function.ac.autosetting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.app.command.processexecution.AutoExecutionPreparationAdapter;
import nts.uk.ctx.at.function.dom.processexecution.AuxiliaryPatternCode;
import nts.uk.ctx.at.function.dom.processexecution.UpdateProcessAutoExecution;
import nts.uk.ctx.at.function.dom.processexecution.listempautoexec.ListEmpAutoExec;
import nts.uk.ctx.sys.assist.pub.command.autosetting.AutoExecutionPreparationPub;
import nts.uk.ctx.sys.assist.pub.command.autosetting.AutoPrepareDataExport;
import nts.uk.shr.com.context.AppContexts;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class AutoExecutionPreparationAdapterImpl implements AutoExecutionPreparationAdapter {

	@Inject
	private AutoExecutionPreparationPub pub;

	@Inject
	private ListEmpAutoExec listEmpAutoExec;

	@Override
	public boolean autoStoragePrepare(UpdateProcessAutoExecution domain) {
		String patternCode = domain.getExecSetting().getSaveData().getPatternCode().map(AuxiliaryPatternCode::v).orElse(null);
		AutoPrepareDataExport data = this.pub.autoStoragePrepare(patternCode);
		String storeProcessingId = data.getProcessingId();
		DatePeriod period = new DatePeriod(data.getDayStartDate(), data.getDayEndDate());
		// ドメイン「更新処理自動実行の実行対象社員リストを取得する」を実行する
		List<String> empIds = listEmpAutoExec.getListEmpAutoExec(AppContexts.user().companyId(), period,
				domain.getExecScope().getExecScopeCls(), Optional.of(domain.getExecScope().getWorkplaceIdList()),
				Optional.empty());
		return this.pub.updateTargetEmployee(storeProcessingId, patternCode, empIds);
	}

	@Override
	public boolean autoDeletionPrepare(UpdateProcessAutoExecution domain) {
		AutoPrepareDataExport data = this.pub.autoDeletionPrepare(
				domain.getExecSetting().getDeleteData().getPatternCode().map(AuxiliaryPatternCode::v).orElse(null));
		String delId = data.getProcessingId();
		DatePeriod period = new DatePeriod(data.getDayStartDate(), data.getDayEndDate());
		// ドメイン「更新処理自動実行の実行対象社員リストを取得する」を実行する
		List<String> empIds = listEmpAutoExec.getListEmpAutoExec(AppContexts.user().companyId(), period,
				domain.getExecScope().getExecScopeCls(), Optional.of(domain.getExecScope().getWorkplaceIdList()),
				Optional.empty());
		return this.pub.updateEmployeeDeletion(delId, empIds);
	}
}
