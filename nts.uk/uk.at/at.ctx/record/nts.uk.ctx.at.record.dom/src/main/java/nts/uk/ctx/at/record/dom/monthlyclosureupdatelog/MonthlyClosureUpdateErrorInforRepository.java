package nts.uk.ctx.at.record.dom.monthlyclosureupdatelog;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author HungTT
 *
 */

public interface MonthlyClosureUpdateErrorInforRepository {
	
	public List<MonthlyClosureUpdateErrorInfor> getAll(String monthlyClosureUpdateLogId);
	
	public Optional<MonthlyClosureUpdateErrorInfor> getById(String monthlyClosureUpdateLogId, String employeeId);

	public void add(MonthlyClosureUpdateErrorInfor domain);
	
}
