package nts.uk.ctx.at.record.dom.jobmanagement.manhourrecorditem;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.record.dom.daily.ouen.SupportFrameNo;

/**
 * 
 * @author tutt
 *
 */
@RunWith(JMockit.class)
public class ManHrRecordTaskDetailToAttendanceItemServiceTest {
	
	@Injectable
	private ManHrRecordTaskDetailToAttendanceItemService.Require require;
	
	@Test
	public void test1() {
		List<Integer> itemLst = new ArrayList<>();
		itemLst.add(1);
		itemLst.add(2);
		List<ManHourRecordAndAttendanceItemLink> settings = new ArrayList<>();
		List<TaskItemValue> expectedResult = new ArrayList<>();
		expectedResult.add(new TaskItemValue(1, "1"));
		expectedResult.add(new TaskItemValue(2, "2"));
		
		List<ManHrTaskDetail> taskDetails = new ArrayList<>();
		taskDetails.add(new ManHrTaskDetail(expectedResult, new SupportFrameNo(1)));
		settings.add(new ManHourRecordAndAttendanceItemLink(new SupportFrameNo(1), 1, 1));
		
		new Expectations() {
			{
				require.get(itemLst);
				result = settings;
			}
		};
		ManHrRecordTaskDetailToAttendanceItemService service = new ManHrRecordTaskDetailToAttendanceItemService();
		List<TaskItemValue> actualResult = service.convert(require, expectedResult, taskDetails);
		assertThat(expectedResult.equals(actualResult));
	}
	
	@Test
	public void test2() {
		List<Integer> itemLst = new ArrayList<>();
		itemLst.add(1);
		itemLst.add(2);
		List<ManHourRecordAndAttendanceItemLink> settings = new ArrayList<>();
		List<TaskItemValue> values = new ArrayList<>();
		List<TaskItemValue> expectedResult = new ArrayList<>();
		values.add(new TaskItemValue(1, "1"));
		values.add(new TaskItemValue(2, "2"));
		
		List<ManHrTaskDetail> taskDetails = new ArrayList<>();
		taskDetails.add(new ManHrTaskDetail(expectedResult, new SupportFrameNo(1)));
		settings.add(new ManHourRecordAndAttendanceItemLink(new SupportFrameNo(1), 3, 1));
		
		new Expectations() {
			{
				require.get(itemLst);
				result = settings;
			}
		};
		ManHrRecordTaskDetailToAttendanceItemService service = new ManHrRecordTaskDetailToAttendanceItemService();
		List<TaskItemValue> actualResult = service.convert(require, values, taskDetails);
		assertThat(expectedResult.equals(actualResult));
	}
	
	
//	@Test
//	public void test3() {
//		List<Integer> itemLst = new ArrayList<>();
//		itemLst.add(1);
//		itemLst.add(2);
//		List<ManHourRecordAndAttendanceItemLink> settings = new ArrayList<>();
//		List<TaskItemValue> expectedResult = new ArrayList<>();
//		expectedResult.add(new TaskItemValue(1, "1"));
//		expectedResult.add(new TaskItemValue(2, "2"));
//		
//		List<ManHrTaskDetail> taskDetails = new ArrayList<>();
//		taskDetails.add(new ManHrTaskDetail(expectedResult, new SupportFrameNo(1)));
//		settings.add(new ManHourRecordAndAttendanceItemLink(new SupportFrameNo(1), 3, 3));
//		
//		new Expectations() {
//			{
//				require.get(itemLst);
//				result = settings;
//			}
//		};
//		ManHrRecordTaskDetailToAttendanceItemService service = new ManHrRecordTaskDetailToAttendanceItemService();
//		List<TaskItemValue> actualResult = service.convert(require, expectedResult, taskDetails);
//		assertThat(expectedResult.equals(actualResult));
//	}

}
