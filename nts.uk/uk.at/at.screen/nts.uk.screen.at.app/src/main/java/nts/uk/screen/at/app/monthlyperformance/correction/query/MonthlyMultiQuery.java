package nts.uk.screen.at.app.monthlyperformance.correction.query;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MonthlyMultiQuery {

	private List<String> employeeIds;

	public MonthlyMultiQuery(List<String> employeeIds) {
		super();
		this.employeeIds = employeeIds;
	}
}
