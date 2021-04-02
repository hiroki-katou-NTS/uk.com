package nts.uk.ctx.at.shared.app.command.workingconditionitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
/**
 * 
 * @author quytb
 *
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkingConditionItemSaveCommand {
	/**履歴項目 */
	private String histId;
	
	/** 社員ID*/
	private String employeeId;
	
	/** 加給時間帯*/
	private String bonusPaySettingCode;
	
	public static WorkingConditionItem toDomain(WorkingConditionItemSaveCommand command) {
		return null;
	}
}
