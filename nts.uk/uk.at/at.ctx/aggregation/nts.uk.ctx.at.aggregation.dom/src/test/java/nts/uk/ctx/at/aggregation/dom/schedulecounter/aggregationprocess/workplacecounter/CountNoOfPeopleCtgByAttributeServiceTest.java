package nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.workplacecounter;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AggregationUnitOfEmployeeAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;

@RunWith(JMockit.class)
public class CountNoOfPeopleCtgByAttributeServiceTest {
	
	@Injectable
	CountNoOfPeopleCtgByAttributeService.Require require;
	
	@Test
	public void countingEachAttribute_empty() {
		val instance = new CountNoOfPeopleCtgByAttributeService();
		Map<GeneralDate, Map<String, BigDecimal>>  result = NtsAssert.Invoke.privateMethod(instance, "countingEachAttribute", require, AggregationUnitOfEmployeeAttribute.EMPLOYMENT, Collections.emptyList());
		assertThat(result).isEmpty();
	}
	
	@Test
	public void countingEachAttribute() {
		val instance = new CountNoOfPeopleCtgByAttributeService();
		//Map<GeneralDate, Map<String, BigDecimal>>  result = NtsAssert.Invoke.privateMethod(instance, "countingEachAttribute", require, AggregationUnitOfEmployeeAttribute.EMPLOYMENT, dailyWorks);
		//assertThat(result).isEmpty();
	}
	
	public static class Helper{
		
		@Injectable
		private static WorkInfoOfDailyAttendance workInformation;
		
		@Injectable
		private static AffiliationInforOfDailyAttd affiliationInfor;
		
		public static IntegrationOfDaily createDailyWorks(String sid, GeneralDate ymd ) {
			return new IntegrationOfDaily(
					  sid, ymd, workInformation
					, CalAttrOfDailyAttd.defaultData()
					, affiliationInfor
					, Optional.empty()
					, Collections.emptyList()
					, Optional.empty()
					, new BreakTimeOfDailyAttd(Collections.emptyList())
					, Optional.empty()
					, Optional.empty()
					, Optional.empty()
					, Optional.empty()
					, Optional.empty()
					, Optional.empty()
					, Collections.emptyList()
					, Optional.empty()
					, Collections.emptyList()
					, Optional.empty());
		}
		
		public static AffiliationInforOfDailyAttd createAffiliationInfo(EmploymentCode employmentCode
				,	String jobTitleID) {
			
			
		}

	}	

}
