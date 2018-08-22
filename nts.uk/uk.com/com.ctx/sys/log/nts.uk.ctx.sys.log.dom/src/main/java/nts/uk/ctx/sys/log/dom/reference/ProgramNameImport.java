package nts.uk.ctx.sys.log.dom.reference;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author thuongtv
 *
 */

@Data
@AllArgsConstructor
public class ProgramNameImport {
	
	private String companyId;
	
	private String programId;
	
	private String screenId;
	
	private String queryString;
	
	private String displayName;
}
