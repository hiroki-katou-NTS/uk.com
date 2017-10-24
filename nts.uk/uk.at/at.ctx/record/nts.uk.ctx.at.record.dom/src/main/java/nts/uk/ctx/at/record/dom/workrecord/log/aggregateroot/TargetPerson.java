/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log.aggregateroot;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.log.ComplStateOfExeContents;

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
