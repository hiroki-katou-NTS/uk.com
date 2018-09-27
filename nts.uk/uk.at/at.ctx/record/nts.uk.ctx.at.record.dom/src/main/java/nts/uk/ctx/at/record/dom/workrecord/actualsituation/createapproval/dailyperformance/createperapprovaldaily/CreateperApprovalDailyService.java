package nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.createperapprovaldaily;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;

/**
 * 日別実績の承認ルート中間データの作成
 * @author tutk
 *
 */
public interface CreateperApprovalDailyService {
	public boolean createperApprovalDaily(String companyId,String executionId,List<String> employeeIDs,int processExecType,Integer createNewEmp,GeneralDate startDateClosure);
}
