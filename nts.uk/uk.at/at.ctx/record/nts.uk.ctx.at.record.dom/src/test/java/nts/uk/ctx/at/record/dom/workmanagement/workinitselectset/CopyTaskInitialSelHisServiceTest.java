package nts.uk.ctx.at.record.dom.workmanagement.workinitselectset;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 
 * @author HieuLt
 *
 */

@RunWith(JMockit.class)
public class CopyTaskInitialSelHisServiceTest {

	@Injectable
	CopyTaskInitialSelHisService.Require require;

	@Test
	public void copyTaskInitialSelHist() {
		String empCopy = "EmpCopy";
		String empMain = "EmpMain";
		new Expectations() {
			{
				require.getById(empCopy);
				result = Optional.empty();
			}
		};

		NtsAssert.businessException("Msg_1185", () -> CopyTaskInitialSelHisService.copy(require, empCopy, empMain));
	}

	@Test
	public void copyTaskInitialSelHist1() {
		String empCopy = "EmpCopy";
		String empMain = "EmpMain";
		TaskInitialSel itemCopy = new TaskInitialSel(empCopy,
				new DatePeriod(GeneralDate.ymd(2021, 06, 01), GeneralDate.ymd(2021, 06, 30)),
				new TaskItem(Optional.of(new TaskCode("TaskCode1")), Optional.of(new TaskCode("TaskCode2")),
						Optional.of(new TaskCode("TaskCode3")), Optional.of(new TaskCode("TaskCode4")),
						Optional.of(new TaskCode("TaskCode5"))));
		TaskInitialSel itemMain = new TaskInitialSel(empMain,
				new DatePeriod(GeneralDate.ymd(2021, 06, 01), GeneralDate.ymd(2021, 07, 31)),
				new TaskItem(Optional.of(new TaskCode("TaskCode1")), Optional.of(new TaskCode("TaskCode2")),
						Optional.of(new TaskCode("TaskCode3")), Optional.of(new TaskCode("TaskCode4")),
						Optional.of(new TaskCode("TaskCode5"))));
		new Expectations() {
			{
				require.getById(empCopy);
				result = Optional.of(itemCopy);
			}
		};
		new Expectations() {
			{
				require.getById("empMain");
				result = Optional.of(itemMain);
			}
		};

	}

}
