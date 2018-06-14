package nts.uk.ctx.sys.assist.dom.datarestoration.common;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecovery;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;

@Stateless
public class CompanyDeterminationProcess {
	// アルゴリズム「別会社判定処理」を実行する
	public List<Object> sperateCompanyDeterminationProcess(ServerPrepareMng serverPrepareMng, PerformDataRecovery performDataRecovery, List<TableList> tableList){
		return Arrays.asList(serverPrepareMng, performDataRecovery);
	}
}
