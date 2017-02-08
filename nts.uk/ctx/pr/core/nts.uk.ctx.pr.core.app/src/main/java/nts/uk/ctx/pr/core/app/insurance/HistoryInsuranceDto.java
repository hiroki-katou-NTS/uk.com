package nts.uk.ctx.pr.core.app.insurance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryInsuranceDto{
	private String historyId;
	//private MonthRange monthRage;
	private String startMonthRage;
	private String endMonthRage;
	private String inforMonthRage;
}
