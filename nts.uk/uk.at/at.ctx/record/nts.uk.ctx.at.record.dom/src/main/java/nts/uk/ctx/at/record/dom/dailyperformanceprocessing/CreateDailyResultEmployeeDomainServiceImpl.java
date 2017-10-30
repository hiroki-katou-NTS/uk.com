package nts.uk.ctx.at.record.dom.dailyperformanceprocessing;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class CreateDailyResultEmployeeDomainServiceImpl implements CreateDailyResultEmployeeDomainService {

	@Override
	public int createDailyResultEmployee(String emloyeeId, GeneralDate startDate, GeneralDate endDate) {
		
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		
		/**
		 * 正常終了 : 0
		 */
		int endStatus = 0;
		
		int days = endDate.day() - startDate.day(); 
		GeneralDate processingDate = startDate;
		
		for (int i = 0; i < days; i++) {
			processingDate.addDays(1);
			if (!processingDate.equals(endDate)) {
				
			} else {
				endStatus = 0;
			}
		}
		
		return endStatus;
	}

}
