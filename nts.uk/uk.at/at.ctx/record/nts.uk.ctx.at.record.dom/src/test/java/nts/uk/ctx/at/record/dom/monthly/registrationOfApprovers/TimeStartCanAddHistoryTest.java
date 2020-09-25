package nts.uk.ctx.at.record.dom.monthly.registrationOfApprovers;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByCompany;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByWorkplace;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Unit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class TimeStartCanAddHistoryTest {

	@Injectable
	TimeStartCanAddHistory.Require require;

	@Test
	public void checkAdd_1(){
		// Mock up
		new Expectations() {{
			require.getByCompanyIdFromDate(GeneralDate.today());
			result = new ArrayList<Approver36AgrByCompany>();
		}};

		assertThat(TimeStartCanAddHistory.checkAdd(
		        require, EnumAdaptor.valueOf(1, Unit.class),"workplaceId",GeneralDate.today())).isFalse();
	}

	@Test
	public void checkAdd_2(){

        Approver36AgrByCompany agrByCompany = new Approver36AgrByCompany(
                "cid",new DatePeriod(GeneralDate.today(),GeneralDate.max()), Arrays.asList("approverList"),Arrays.asList("confirmerList"));
		// Mock up
		new Expectations() {{
			require.getByCompanyIdFromDate(GeneralDate.today());
			result = Arrays.asList(agrByCompany);
		}};

		assertThat(TimeStartCanAddHistory.checkAdd(
		        require, EnumAdaptor.valueOf(1, Unit.class),"workplaceId",GeneralDate.today())).isTrue();
	}

	@Test
	public void checkAdd_3(){
		// Mock up
		new Expectations() {{
			require.getByWorkplaceIdFromDate("workplaceId",GeneralDate.today());
			result = Arrays.asList();
		}};

		assertThat(TimeStartCanAddHistory.checkAdd(
		        require, EnumAdaptor.valueOf(0, Unit.class),"workplaceId",GeneralDate.today())).isFalse();
	}

	@Test
	public void checkAdd_4(){

        Approver36AgrByWorkplace agrByWorkplace = new Approver36AgrByWorkplace(
                "cid","workplaceId",new DatePeriod(GeneralDate.today(),GeneralDate.max()), Arrays.asList("approverList"),Arrays.asList("confirmerList"));
		// Mock up
		new Expectations() {{
			require.getByWorkplaceIdFromDate("workplaceId",GeneralDate.today());
			result = Arrays.asList(agrByWorkplace);
		}};

		assertThat(TimeStartCanAddHistory.checkAdd(
		        require, EnumAdaptor.valueOf(0, Unit.class),"workplaceId",GeneralDate.today())).isTrue();
	}

}
