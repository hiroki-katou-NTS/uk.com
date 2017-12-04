/**
 * 
 */
package nts.uk.ctx.pereg.app.find.layout;

import java.util.Date;

import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * @author danpv
 *
 */
@Data
public class LayoutQuery {

	private String mainteLayoutId;
	
	private String browsingEmpId;
	
	private Date standardDate;

}
