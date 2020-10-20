package nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export;

import java.util.Optional;

import org.junit.Test;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.GetHolidayOver60hRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.GetHolidayOver60hRemNumWithinPeriodImpl;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param.AggrResultOfHolidayOver60h;


public class TestHolidayOver60h {
	
	/*検証method*/
	@Test
	public void assert1(){
	
		GetHolidayOver60hRemNumWithinPeriod.RequireM1 require
			= new GetHolidayOver60hRemNumWithinPeriodImpl.GetHolidayOver60hRemNumWithinPeriodRequireM1();
	
		CacheCarrier cacheCarrier1 = new CacheCarrier();
		String companyId1 = "XXX";
		String employeeId1 = "";
		DatePeriod aggrPeriod1 = new DatePeriod(GeneralDate.ymd(2020, 4, 5), GeneralDate.ymd(2020, 10, 5));;
		InterimRemainMngMode mode1 = InterimRemainMngMode.MONTHLY;
		GeneralDate criteriaDate = GeneralDate.ymd(2020, 5, 1);
			
		GetHolidayOver60hRemNumWithinPeriod a 
			= new GetHolidayOver60hRemNumWithinPeriodImpl();
		
		AggrResultOfHolidayOver60h aggrResultOfHolidayOver60h
			= a.algorithm(
				require, 
				cacheCarrier1,
				companyId1, 
				employeeId1, 
				aggrPeriod1, 
				mode1,
				criteriaDate, 
				Optional.empty(),
				Optional.empty(),
				Optional.empty()
				);
		
		System.out.println(aggrResultOfHolidayOver60h);
		
		
	}
	
}
