/**
 * 
 */
package nts.uk.screen.at.app.ksu001.displayinshift;

import lombok.Value;
import nts.uk.screen.at.app.ksu001.getshiftpalette.ShiftMasterDto;

/**
 * @author laitv
 */
@Value
public class ShiftMasterMapWithWorkStyle {
	public ShiftMasterDto shiftMaster;     // シフトマスタ
	public String workStyle;               // 出勤休日区分 (co truong hơp workStyle = null nên ko để kiểu int được)
	
}
