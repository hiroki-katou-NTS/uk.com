package nts.uk.ctx.at.record.dom.monthlyclosureupdatelog;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author HungTT
 *
 */

public interface MonthlyClosureUpdatePersonLogRepository {

	public List<MonthlyClosureUpdatePersonLog> getAll(String monthlyClosureUpdateLogId);
	
	public Optional<MonthlyClosureUpdatePersonLog> getById(String monthlyClosureUpdateLogId, String employeeId);
	
	public void add(MonthlyClosureUpdatePersonLog domain);
	
	public void delete(String monthlyLogId, String empId);
	
}
