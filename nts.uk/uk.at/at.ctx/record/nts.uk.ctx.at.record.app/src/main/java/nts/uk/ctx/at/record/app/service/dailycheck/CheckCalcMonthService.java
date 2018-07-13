package nts.uk.ctx.at.record.app.service.dailycheck;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.ClosurePeriod;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.GetClosurePeriod;

@Stateless
public class CheckCalcMonthService {
      
    @Inject
    private GetClosurePeriod getClosurePeriod;
    
	public void checkCalcMonth(String companyId, String employeeId, GeneralDate criteriaDate){
		List<ClosurePeriod> closurePeriods = getClosurePeriod.get(companyId, employeeId, criteriaDate, Optional.empty(),  Optional.empty(),  Optional.empty());
	}
}
