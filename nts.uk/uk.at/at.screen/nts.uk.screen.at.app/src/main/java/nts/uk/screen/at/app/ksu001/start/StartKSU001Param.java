/**
 * 
 */
package nts.uk.screen.at.app.ksu001.start;

import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * @author laitv
 *
 */
@Value
public class StartKSU001Param {
	
	public boolean dataLocalstorageEmpty; // lần đầu thì sẽ = false.
	public int displayFormat;
	public int shiftPalletUnit;
	public GeneralDate startDate;
	public GeneralDate endDate;
	
}
