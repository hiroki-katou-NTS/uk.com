/**
 * 
 */
package nts.uk.screen.at.app.ksu001.displayinshift;

import lombok.Value;
import nts.uk.screen.at.app.ksu001.getshiftpalette.ShiftMasterDto;

/**
 * @author laitv
 *
 */
@Value
public class PageShift {
	public ShiftMasterDto shiftMaster;     // シフトマスタ
	public int workStyle;  // 出勤休日区分
	
}
