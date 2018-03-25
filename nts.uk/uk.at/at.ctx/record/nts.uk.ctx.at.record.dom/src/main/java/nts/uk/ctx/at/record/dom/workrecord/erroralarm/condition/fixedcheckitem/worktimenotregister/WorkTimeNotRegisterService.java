package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.worktimenotregister;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.checkprincipalunconfirm.ValueExtractAlarmWR;
/**
 * 2.就業時間帯未登録
 * @author tutk
 *
 */
public interface WorkTimeNotRegisterService {
	public Optional<ValueExtractAlarmWR>  checkWorkTimeNotRegister(String workplaceID,String employeeID,GeneralDate date,String workTimeCD);
}
