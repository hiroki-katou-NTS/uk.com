package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.worktypenotregister;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.checkprincipalunconfirm.ValueExtractAlarmWR;
/**
 * 1.勤務種類未登録
 * @author tutk
 *
 */
public interface WorkTypeNotRegisterService {
	public Optional<ValueExtractAlarmWR>  checkWorkTypeNotRegister(String workplaceID,String employeeID,GeneralDate date,String workTypeCD);
}
