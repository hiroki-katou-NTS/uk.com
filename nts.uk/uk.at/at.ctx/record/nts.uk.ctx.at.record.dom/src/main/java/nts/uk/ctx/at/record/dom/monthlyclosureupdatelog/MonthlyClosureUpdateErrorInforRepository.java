package nts.uk.ctx.at.record.dom.monthlyclosureupdatelog;

import java.util.Optional;

/**
 * 
 * @author HungTT
 *
 */

public interface MonthlyClosureUpdateErrorInforRepository {
	
	public Optional<MonthlyClosureUpdateErrorInfor> getById(String monthlyClosureUpdateLogId, String employeeId);

	public void add(MonthlyClosureUpdateErrorInfor domain);
	
}
