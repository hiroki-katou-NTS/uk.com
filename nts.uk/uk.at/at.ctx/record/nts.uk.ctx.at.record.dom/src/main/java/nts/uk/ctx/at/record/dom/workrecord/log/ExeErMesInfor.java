/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log;

import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.log.objectvalue.SysErClass;

/**
 * @author danpv
 *
 */
@Data
public class ExeErMesInfor {

	private String errorMessage;
	
	private SysErClass sysErrorType;
	
	private long empCalAndSumExecLogId;
	
}
