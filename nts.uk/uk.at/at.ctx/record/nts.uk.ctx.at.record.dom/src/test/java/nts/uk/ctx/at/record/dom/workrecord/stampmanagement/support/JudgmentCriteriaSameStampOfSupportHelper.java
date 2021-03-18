/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support;

/**
 * @author laitv
 *
 */
public class JudgmentCriteriaSameStampOfSupportHelper {
	
	public static JudgmentCriteriaSameStampOfSupport getDataDefault1(){
		return new JudgmentCriteriaSameStampOfSupport("cid", 10, 100);
	}
	
	public static JudgmentCriteriaSameStampOfSupport getDataDefault2(){
		return new JudgmentCriteriaSameStampOfSupport("cid", new RangeRegardedSupportStamp(10), new MaximumNumberOfSupport(100));
	}


}
