package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.worktypenotregister;

import nts.arc.time.GeneralDate;
/**
 * 1.勤務種類未登録
 * @author tutk
 *
 */
public interface WorkTypeNotRegisterService {
	public boolean  checkWorkTypeNotRegister(String employeeID,GeneralDate date,String workTypeCD);
}
