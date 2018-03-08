package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.worktimenotregister;

import nts.arc.time.GeneralDate;
/**
 * 2.就業時間帯未登録
 * @author tutk
 *
 */
public interface WorkTimeNotRegisterService {
	public boolean  checkWorkTimeNotRegister(String employeeID,GeneralDate date,String workTimeCD);
}
