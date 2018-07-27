package nts.uk.ctx.at.record.dom.monthlyclosureupdatelog;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author HungTT
 *
 */

public interface MonthlyClosureUpdateErrorInforRepository {

	public List<MonthlyClosureUpdateErrorInfor> getAll(String monthlyClosureUpdateLogId);

	public List<MonthlyClosureUpdateErrorInfor> getByLogIdAndEmpId(String monthlyClosureUpdateLogId, String employeeId);

	public Optional<MonthlyClosureUpdateErrorInfor> getById(String monthlyClosureUpdateLogId, String employeeId,
			GeneralDate actualClosureEndDate, String resourceId);

	public void add(MonthlyClosureUpdateErrorInfor domain);

}
