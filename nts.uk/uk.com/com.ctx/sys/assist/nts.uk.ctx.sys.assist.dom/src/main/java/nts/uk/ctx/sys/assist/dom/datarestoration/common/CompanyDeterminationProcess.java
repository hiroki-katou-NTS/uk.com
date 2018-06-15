package nts.uk.ctx.sys.assist.dom.datarestoration.common;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.sys.assist.dom.category.RecoverFormCompanyOther;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecovery;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareOperatingCondition;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CompanyDeterminationProcess {
	// アルゴリズム「別会社判定処理」を実行する
	public List<Object> sperateCompanyDeterminationProcess(ServerPrepareMng serverPrepareMng, PerformDataRecovery performDataRecovery, List<TableList> tableList){
		String cid = tableList.get(1).getFieldAcqCid();
		
		if (AppContexts.user().companyId().equals(cid)){
			//performDataRecovery.setRecoverFromAnoCom(recoverFromAnoCom);
		} else {
			// SET performDataRecovery TO ANOTHER COMPANY
			boolean isRecoveryOtherCompanyNoOccur = true;
			for(int i = 0; i < tableList.size(); i++){
				TableList tableListRecord = tableList.get(i);
				if (tableListRecord.getAnotherComCls() == RecoverFormCompanyOther.IS_RE_OTHER_COMPANY){
					tableListRecord.setCanNotBeOld(1);
					isRecoveryOtherCompanyNoOccur = false;
				} else {
					tableListRecord.setCanNotBeOld(0);
				}
			}
			if (isRecoveryOtherCompanyNoOccur) {
				serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.NO_SEPARATE_COMPANY);
			}
		}
		return Arrays.asList(serverPrepareMng, performDataRecovery, tableList);
	}
}
