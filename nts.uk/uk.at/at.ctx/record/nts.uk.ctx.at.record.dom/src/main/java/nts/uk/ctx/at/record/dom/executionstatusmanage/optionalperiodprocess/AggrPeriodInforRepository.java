package nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess;

import java.util.List;

/**
 * 
 * @author HungTT
 *
 */

public interface AggrPeriodInforRepository {

	/**
	 * 
	 * @param anyPeriodAggrLogId
	 * @return
	 */
	List<AggrPeriodInfor> findAll(String anyPeriodAggrLogId);

	void addPeriodInfor(AggrPeriodInfor periodInfor);

}
