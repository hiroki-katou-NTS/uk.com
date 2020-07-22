/**
 * 
 */
package nts.uk.screen.at.app.ksu001.getshiftpalette;
import lombok.Value;
import nts.uk.ctx.at.schedule.app.find.shift.shiftpalletsorg.ShiftPalletsOrgDto;
import nts.uk.ctx.at.schedule.app.find.shift.shijtpalletcom.ComPatternScreenDto;


/**
 * @author laitv
 *
 */

@Value
public class TargetShiftPalette {
	
	public int pageNumber;
	public ComPatternScreenDto shiftPalletCom;
	public ShiftPalletsOrgDto  shiftPalletWorkPlace;

}
