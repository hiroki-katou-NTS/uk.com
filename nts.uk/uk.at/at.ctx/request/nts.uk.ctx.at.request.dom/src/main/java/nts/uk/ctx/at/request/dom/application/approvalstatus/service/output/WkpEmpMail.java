package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class WkpEmpMail {
	/**
	 * 職場ID
	 */
	private String wkpID;
	
	/**
	 * 社員ID
	 */
	private String empID;
	
	/**
	 * 日付
	 */
	private GeneralDate date;
}
