/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.authormanage;

import java.util.List;

/**
 * @author danpv
 *
 */
public interface DailyPerformanceAuthorityRepoInterface {
	
	public List<DailyPerformanceAuthority> getDailyPerformanceAuthorities(String roleId);

}
