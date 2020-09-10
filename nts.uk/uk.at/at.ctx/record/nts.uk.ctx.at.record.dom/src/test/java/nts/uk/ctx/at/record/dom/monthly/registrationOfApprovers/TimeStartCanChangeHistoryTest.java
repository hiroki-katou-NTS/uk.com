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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class TimeStartCanChangeHistoryTest {

	@Injectable
	TimeStartCanChangeHistory.Require require;

	@Test
	public void checkChange_1(){
		// Mock up
		new Expectations() {{
			require.getByCompanyIdAndEndDate(GeneralDate.ymd(2020,9,9));
			result = Optional.empty();
		}};

		assertThat(TimeStartCanChangeHistory.checkUpdate(
		        require, EnumAdaptor.valueOf(0, Unit.class),"workplaceId",GeneralDate.ymd(2020,9,10),GeneralDate.ymd(2020,9,1))).isTrue();
	}

	@Test
	public void checkChange_2(){
		Approver36AgrByCompany agrByCompany = new Approver36AgrByCompany(
				"cid",new DatePeriod(GeneralDate.today(),GeneralDate.max()), Arrays.asList("approverList"),Arrays.asList("confirmerList"));
		// Mock up
		new Expectations() {{
			require.getByCompanyIdAndEndDate(GeneralDate.ymd(2020,9,9));
			result = Optional.of(agrByCompany);
		}};

		assertThat(TimeStartCanChangeHistory.checkUpdate(
		        require, EnumAdaptor.valueOf(0, Unit.class),"workplaceId",GeneralDate.ymd(2020,9,10),GeneralDate.ymd(2020,9,1))).isFalse();
	}

	@Test
	public void checkChange_3(){
		// Mock up
		new Expectations() {{
			require.getByWorkplaceIdAndEndDate("workplaceId",GeneralDate.ymd(2020,9,9));
			result = Optional.empty();
		}};

		assertThat(TimeStartCanChangeHistory.checkUpdate(
				require, EnumAdaptor.valueOf(1, Unit.class),"workplaceId",GeneralDate.ymd(2020,9,10),GeneralDate.ymd(2020,9,1))).isTrue();
	}

	@Test
	public void checkChange_4(){
		Approver36AgrByWorkplace agrByWorkplace = new Approver36AgrByWorkplace(
				"cid","workplaceId",new DatePeriod(GeneralDate.today(),GeneralDate.max()), Arrays.asList("approverList"),Arrays.asList("confirmerList"));
		// Mock up
		new Expectations() {{
			require.getByWorkplaceIdAndEndDate("workplaceId",GeneralDate.ymd(2020,9,9));
			result = Optional.of(agrByWorkplace);
		}};

		assertThat(TimeStartCanChangeHistory.checkUpdate(
				require, EnumAdaptor.valueOf(1, Unit.class),"workplaceId",GeneralDate.ymd(2020,9,10),GeneralDate.ymd(2020,9,1))).isFalse();
	}

}
