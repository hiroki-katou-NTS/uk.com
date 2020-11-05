package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.GetTimeCardService.Require;

/**
 * 
 * @author tutk
 *
 */
@RunWith(JMockit.class)
public class GetTimeCardServiceTest {
	@Injectable
	private Require require;
	
//	/**
//	 * required.findbyPeriodOrderByYmd(employeeId,datePeriod) is empty
//	 */
//	@Test
//	public void testGetTimeCardService_1() {
//		String employeeId = "employeeId";
//		YearMonth yearMonth = GeneralDate.today().yearMonth();
//		new Expectations() {
//			{
//				require.findbyPeriodOrderByYmd(anyString,(DatePeriod)any);
//				result = new ArrayList<>();
//			}
//		};
//		assertThat(GetTimeCardService.getTimeCard(require, employeeId, yearMonth).getListAttendanceOneDay().isEmpty()).isTrue();
//		
//	}
	
	/**
	 * required.findbyPeriodOrderByYmd(employeeId,datePeriod) not empty
	 */
	@Test
	public void testGetTimeCardService_2() {
		String employeeId = "employeeId";
		YearMonth yearMonth = GeneralDate.today().yearMonth();
		new Expectations() {
			{
				require.findbyPeriodOrderByYmd(anyString,(DatePeriod)any);
				result = Arrays.asList(DomainServiceHeplper.getTimeLeavingOfDailyPerformanceDefault());
			}
		};
		assertThat(GetTimeCardService.getTimeCard(require, employeeId, yearMonth).getListAttendanceOneDay().isEmpty()).isFalse();
		
	}
	

}
