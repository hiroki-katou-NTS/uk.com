package nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly;

import java.util.Map;
import java.util.Optional;

public interface ColumnWidtgByMonthlyRepository {

	Optional<ColumnWidtgByMonthly> getColumnWidtgByMonthly(String companyID);
	
	void addColumnWidtgByMonthly (ColumnWidtgByMonthly columnWidtgByMonthly);
	
	void updateColumnWidtgByMonthly (Map<Integer, Integer> lstHeader);
	
	void deleteColumnWidtgByMonthly (String companyID);
	
}
