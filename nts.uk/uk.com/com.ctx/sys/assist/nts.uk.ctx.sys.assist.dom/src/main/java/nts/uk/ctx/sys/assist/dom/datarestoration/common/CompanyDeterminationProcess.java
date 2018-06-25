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
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareOperatingCondition;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;
import nts.uk.ctx.sys.assist.dom.tablelist.TableListRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class CompanyDeterminationProcess {
	
	@Inject
	private TableListRepository tableListRepository;
	
	// アルゴリズム「別会社判定処理」を実行する
	public List<Object> sperateCompanyDeterminationProcess(ServerPrepareMng serverPrepareMng, PerformDataRecovery performDataRecovery, List<TableList> tableList){
		String cid = tableList.get(0).getFieldAcqCid().orElse("");
		performDataRecovery.setRecoverFromAnoCom(NotUseAtr.NOT_USE);
		if (AppContexts.user().companyId().equals(cid)){
			performDataRecovery.setRecoverFromAnoCom(NotUseAtr.USE);
			performDataRecovery.setRecoveryMethod(RecoveryMethod.ALL_CASES_RESTORED);
		} else {
			performDataRecovery.setRecoveryMethod(RecoveryMethod.ALL_CASES_RESTORED);
			boolean isRecoveryOtherCompanyNoOccur = true;
			for(int i = 0; i < tableList.size(); i++){
				//TODO
				TableList tableListRecord = tableList.get(i);
				if (tableListRecord.getAnotherComCls() == RecoverFormCompanyOther.IS_RE_OTHER_COMPANY){
					tableListRecord.setCanNotBeOld(Optional.of(1));
					isRecoveryOtherCompanyNoOccur = false;
				} else {
					tableListRecord.setCanNotBeOld(Optional.of(0));
				}
				tableListRepository.update(tableListRecord);
			}
			if (isRecoveryOtherCompanyNoOccur) {
				serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.NO_SEPARATE_COMPANY);
			}
		}
		return Arrays.asList(serverPrepareMng, performDataRecovery, tableList);
	}
}
