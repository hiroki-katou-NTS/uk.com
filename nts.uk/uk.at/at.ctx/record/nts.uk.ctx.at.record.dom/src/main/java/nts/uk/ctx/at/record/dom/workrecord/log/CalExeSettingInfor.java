/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.log.objectvalue.ExecutionContent;
import nts.uk.ctx.at.record.dom.workrecord.log.objectvalue.ExecutionType;

/**
 * @author danpv
 *
 */
@Getter
public class CalExeSettingInfor {

	private ExecutionContent executionContent;

	private ExecutionType executionType;
}
