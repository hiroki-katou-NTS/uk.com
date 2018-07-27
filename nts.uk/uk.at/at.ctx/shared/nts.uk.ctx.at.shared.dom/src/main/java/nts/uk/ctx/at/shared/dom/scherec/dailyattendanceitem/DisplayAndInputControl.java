package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
/**
 * domain : 
 * 勤怠項目の表示・入力制御
 * @author tutk
 *
 */
@Getter
public class DisplayAndInputControl extends DomainObject {
	
	/**勤怠項目ID*/
	private int itemDailyID;
	
	/**利用する*/
	private boolean toUse;
	
	/**入力制御*/
	private InputControlOfAttendanceItem inputControl;

	public DisplayAndInputControl(int itemDailyID, boolean toUse, InputControlOfAttendanceItem inputControl) {
		super();
		this.itemDailyID = itemDailyID;
		this.toUse = toUse;
		this.inputControl = inputControl;
	}
	
}
