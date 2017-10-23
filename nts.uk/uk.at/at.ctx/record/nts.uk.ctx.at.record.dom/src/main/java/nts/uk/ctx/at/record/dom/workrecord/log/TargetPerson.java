/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * @author danpv
 *
 */
@Getter
public class TargetPerson extends AggregateRoot{

	private long empCalAndSumExecLogId;

	private String employeeId;
	
	private ComplStateOfExeContents state;
	
}
