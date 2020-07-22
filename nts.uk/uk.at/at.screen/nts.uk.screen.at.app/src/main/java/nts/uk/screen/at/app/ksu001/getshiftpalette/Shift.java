/**
 * 
 */
package nts.uk.screen.at.app.ksu001.getshiftpalette;

import java.util.Optional;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;

/**
 * @author laitv
 *
 */
@Value
public class Shift {
	public ShiftMaster shiftMaster;
	public Optional<WorkStyle> workStyle;
	
}
