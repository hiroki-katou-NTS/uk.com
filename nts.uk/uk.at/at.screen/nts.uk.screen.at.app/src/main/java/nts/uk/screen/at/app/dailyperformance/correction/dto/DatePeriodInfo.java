package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.screen.at.app.dailyperformance.correction.dto.cache.AggrPeriodClosure;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DatePeriodInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	List<DateRange> lstRange;
	
	DateRange targetRange;
	
	int yearMonth;
	
//	ClosureId closureId;
	Integer closureId;
	
	List<AggrPeriodClosure> lstClosureCache;
	
	List<DateRangeClosureId> lstRangeCls;
	
}
