package nts.uk.ctx.at.record.dom.daily.ouen;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work.WorkGroup;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work.WorkGroup.Require;

/**
 * 
 * @author chungnt
 *
 */

@RunWith(JMockit.class)
public class GetTheWorkYouUseMostRecentlyServiceTest {

	@Injectable
	private GetTheWorkYouUseMostRecentlyService.Require require;

	private String empId = "empId";
	private DatePeriod targetPeriod = new DatePeriod(GeneralDate.today().addMonths(-1), GeneralDate.today());
	private List<OuenWorkTimeSheetOfDaily> list = GetTheWorkYouUseMostRecentlyServiceHelper.get();

	// $実績作業一覧 isEmpty
	@Test
	public void test_1() {

		new Expectations() {
			{
				require.findOuenWorkTimeSheetOfDaily(empId, targetPeriod);
			}
		};

		List<WorkGroup> result = GetTheWorkYouUseMostRecentlyService.get(require, empId);
		assertThat(result.isEmpty()).isTrue();
	}

	@Test
	public void test_2() {

		new Expectations() {
			{
				require.findOuenWorkTimeSheetOfDaily(empId, targetPeriod);
				result = list;
			}
		};
		
		new MockUp<WorkGroup>() {
			@Mock
			public boolean checkWorkContents(Require require) {
				return true;

			}
		};

		List<WorkGroup> result = GetTheWorkYouUseMostRecentlyService.get(require, empId);
		assertThat(result.isEmpty()).isFalse();
		assertThat(result.size()).isEqualTo(10);
		assertThat(result.get(0).getWorkCD1().v()).isEqualTo("0000010");
		assertThat(result.get(1).getWorkCD1().v()).isEqualTo("0000011");
		assertThat(result.get(2).getWorkCD1().v()).isEqualTo("0000012");
		assertThat(result.get(3).getWorkCD1().v()).isEqualTo("0000013");
		assertThat(result.get(4).getWorkCD1().v()).isEqualTo("0000014");
		assertThat(result.get(5).getWorkCD1().v()).isEqualTo("0000015");
		assertThat(result.get(6).getWorkCD1().v()).isEqualTo("0000016");
		assertThat(result.get(7).getWorkCD1().v()).isEqualTo("0000017");
		assertThat(result.get(8).getWorkCD1().v()).isEqualTo("0000018");
		assertThat(result.get(9).getWorkCD1().v()).isEqualTo("0000019");
	}

}
