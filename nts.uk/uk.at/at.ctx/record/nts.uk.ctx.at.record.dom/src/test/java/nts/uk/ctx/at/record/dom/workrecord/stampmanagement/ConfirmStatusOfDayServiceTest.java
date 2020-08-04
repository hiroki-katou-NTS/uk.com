package nts.uk.ctx.at.record.dom.workrecord.stampmanagement;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.ConfirmStatusActualResult;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.confirm.DailyLock;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.confirm.StatusActualDay;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.ConfirmStatusOfDayService.Require;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.DomainServiceHeplper;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

@RunWith(JMockit.class)
public class ConfirmStatusOfDayServiceTest {

	@Injectable
	private Require require;

	/**
	 * require.findBySid( employeeId, baseDate) is empty
	 */
	@Test
	public void testConfirmStatusOfDayService_1() {
		String companyId = "companyId";
		String employeeId = "employeeId";
		GeneralDate baseDate = GeneralDate.today();
		new Expectations() {
			{
				require.getClosureDataByEmployee(anyString, (GeneralDate) any);
				result = new Closure(ClosureId.ClosureFour);

				require.findBySid(anyString, (GeneralDate) any);
			}
		};
		NtsAssert.businessException("Msg_427",
				() -> ConfirmStatusOfDayService.get(require, companyId, employeeId, baseDate));
	}

	/**
	 * require.findBySid( employeeId, baseDate) not empty
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testConfirmStatusOfDayService_2() {
		String companyId = "companyId";
		String employeeId = "employeeId";
		GeneralDate baseDate = GeneralDate.today();
		new Expectations() {
			{
				require.getClosureDataByEmployee(anyString, (GeneralDate) any);
				result = new Closure(ClosureId.ClosureFour);
				
				require.findBySid(anyString, (GeneralDate) any);
				result = Optional.of(DomainServiceHeplper.getSWkpHistRcImportedDefault());
				
				require.getDailyLock((StatusActualDay)any);
				result = DomainServiceHeplper.getDailyLockDefault();
				
				require.processConfirmStatus(anyString, anyString, (List<String>) any, (Optional<DatePeriod>) any,
						(Optional<YearMonth>) any, (Optional<DailyLock>) any);
				result = new ConfirmStatusActualResult("employeeId", GeneralDate.today(), true);
			}
		};
		ConfirmStatusOfDayService.get(require, companyId, employeeId, baseDate);
	}

}
