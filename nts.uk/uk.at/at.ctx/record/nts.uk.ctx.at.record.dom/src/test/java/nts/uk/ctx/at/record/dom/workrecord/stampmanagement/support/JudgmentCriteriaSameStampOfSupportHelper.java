/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support;

import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.MaximumNumberOfSupport;

/**
 * @author laitv
 *
 */
public class JudgmentCriteriaSameStampOfSupportHelper {
	
	public static JudgmentCriteriaSameStampOfSupport getDataDefault(){
		return new JudgmentCriteriaSameStampOfSupport("cid", new RangeRegardedSupportStamp(10));
	}


}
