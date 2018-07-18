package nts.uk.ctx.sys.assist.dom.datarestoration.common;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.category.RecoverFormCompanyOther;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecovery;
import nts.uk.ctx.sys.assist.dom.datarestoration.RecoveryMethod;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMngRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareOperatingCondition;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;
import nts.uk.ctx.sys.assist.dom.tablelist.TableListRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class CompanyDeterminationProcess {

	@Inject
	private TableListRepository tableListRepository;
	@Inject
	private ServerPrepareMngRepository serverPrepareMngRepository;

	// アルゴリズム「別会社判定処理」を実行する
	public List<Object> sperateCompanyDeterminationProcess(ServerPrepareMng serverPrepareMng,
			PerformDataRecovery performDataRecovery, List<TableList> tableList) {
		final int FIRST_LINE = 0;
		String internalFileName = tableList.get(FIRST_LINE).getInternalFileName();
		// Get CID (17 first chars)
		String cid = internalFileName.length() > 17 ? internalFileName.substring(0, 17) : "";
		performDataRecovery.setRecoverFromAnoCom(NotUseAtr.NOT_USE);
		if (AppContexts.user().companyId().equals(cid)) {
			performDataRecovery.setRecoverFromAnoCom(NotUseAtr.USE);
			performDataRecovery.setRecoveryMethod(RecoveryMethod.ALL_CASES_RESTORED);
			for (TableList tableListRecord: tableList) {
				tableListRecord.setAnotherComCls(RecoverFormCompanyOther.IS_RE_OTHER_COMPANY);
				tableListRepository.update(tableListRecord);
			}
			return Arrays.asList(serverPrepareMng, performDataRecovery, tableList);
		}
		performDataRecovery.setRecoveryMethod(RecoveryMethod.RESTORE_SELECTED_RANGE);
		boolean isRecoveryOtherCompanyNoOccur = true;
		for (TableList tableListRecord: tableList) {
			if (tableListRecord.getAnotherComCls() == RecoverFormCompanyOther.IS_RE_OTHER_COMPANY) {
				tableListRecord.setCanNotBeOld(Optional.of(1));
				isRecoveryOtherCompanyNoOccur = false;
			} else {
				tableListRecord.setCanNotBeOld(Optional.of(0));
			}
			tableListRepository.update(tableListRecord);
		}
		// If no category can be recover other company
		if (isRecoveryOtherCompanyNoOccur) {
			serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.NO_SEPARATE_COMPANY);
			serverPrepareMngRepository.update(serverPrepareMng);
		}
		return Arrays.asList(serverPrepareMng, performDataRecovery, tableList);
	}
}
