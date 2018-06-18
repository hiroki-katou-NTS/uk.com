package nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly;

import java.util.Optional;

public interface ColumnWidtgByMonthlyRepository {

	Optional<ColumnWidtgByMonthly> getColumnWidtgByMonthly(String companyID);
	
	void addColumnWidtgByMonthly (ColumnWidtgByMonthly columnWidtgByMonthly);
	
	void updateColumnWidtgByMonthly (ColumnWidtgByMonthly columnWidtgByMonthly);
	
	void deleteColumnWidtgByMonthly (String companyID);
	
}
