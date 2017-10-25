package nts.uk.ctx.at.record.dom.editstate;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.editstate.enums.EditStateSetting;

/**
 * 
 * @author nampt
 * 日別実績の編集状態 - root
 *
 */
@Getter
public class EditStateOfDailyPerformance extends AggregateRoot {
	
	private String employeeId;
	
	private int attendanceItemId;
	
	private GeneralDate ymd;
	
	private EditStateSetting editStateSetting;

}
