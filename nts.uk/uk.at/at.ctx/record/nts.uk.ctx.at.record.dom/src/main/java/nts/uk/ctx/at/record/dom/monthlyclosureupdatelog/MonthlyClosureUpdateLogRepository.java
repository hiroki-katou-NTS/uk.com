package nts.uk.ctx.at.record.dom.monthlyclosureupdatelog;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author HungTT
 *
 */

public interface MonthlyClosureUpdateLogRepository {

	public List<MonthlyClosureUpdateLog> getAll(String companyId, int closureId);
	
	public Optional<MonthlyClosureUpdateLog> getLogById(String id);
	
	public void add(MonthlyClosureUpdateLog domain);
	
	public void updateStatus(MonthlyClosureUpdateLog domain);
	
}
