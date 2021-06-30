/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support;

/**
 * @author laitv
 *
 */
public class JudgmentCriteriaSameStampOfSupportHelper {
	
	public static JudgmentCriteriaSameStampOfSupport getDataDefault(){
		return new JudgmentCriteriaSameStampOfSupport("cid", new RangeRegardedSupportStamp(10), new MaximumNumberOfSupport(100));
	}


}
