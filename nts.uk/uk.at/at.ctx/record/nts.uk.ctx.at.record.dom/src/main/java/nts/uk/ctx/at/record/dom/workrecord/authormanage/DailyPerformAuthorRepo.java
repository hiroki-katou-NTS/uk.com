/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.authormanage;

import java.util.List;

/**
 * @author danpv
 *
 */
public interface DailyPerformAuthorRepo {

	public List<DailyPerformanceAuthority> get(String roleId);

	public void save(DailyPerformanceAuthority daiPerAuthority);

}
