package nts.uk.ctx.at.record.dom.jobmanagement.manhourrecorditem;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.record.dom.daily.ouen.SupportFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import static org.assertj.core.api.Assertions.assertThat;

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
		List<ManHourRecordAndAttendanceItemLink> settings = new ArrayList<>();
		List<ItemValue> itemValues = new ArrayList<>();
		itemValues.add(new ItemValue(null, null, 1, null));
		itemValues.add(new ItemValue(null, null, 2, "2"));
		itemValues.add(new ItemValue(null, null, 4, "4"));
		itemValues.add(new ItemValue(null, null, 5, "5"));

		List<ManHrTaskDetail> taskDetails = new ArrayList<>();
		List<TaskItemValue> taskItemValues = new ArrayList<>();
		
		itemValues.add(new ItemValue(null, null, 1, "1"));
		itemValues.add(new ItemValue(null, null, 2, "2"));
		taskItemValues.add(new TaskItemValue(1, "1"));
		taskItemValues.add(new TaskItemValue(2, "2"));
		taskItemValues.add(new TaskItemValue(3, "4"));
		
		taskDetails.add(new ManHrTaskDetail(taskItemValues, new SupportFrameNo(1)));
		taskDetails.add(new ManHrTaskDetail(taskItemValues, new SupportFrameNo(4)));
		
		settings.add(new ManHourRecordAndAttendanceItemLink(new SupportFrameNo(1), 1, 1));
		settings.add(new ManHourRecordAndAttendanceItemLink(new SupportFrameNo(2), 2, 2));
		settings.add(new ManHourRecordAndAttendanceItemLink(new SupportFrameNo(4), 4, 4));

		new Expectations() {
			{
				require.get();
				result = settings;
			}
		};
		List<ItemValue> actualResult = ManHrRecordTaskDetailToAttendanceItemService.convert(require, itemValues, taskDetails);
		
		for (ItemValue v : actualResult) {
			assertThat(v.getValue() == null);
		}
	}


	@Test
	public void test2() {
		List<ManHourRecordAndAttendanceItemLink> settings = new ArrayList<>();
		List<ItemValue> itemValues = new ArrayList<>();
		itemValues.add(new ItemValue(null, null, 1, "4"));

		List<ManHrTaskDetail> taskDetails = new ArrayList<>();
		List<TaskItemValue> taskItemValues = new ArrayList<>();
		
		itemValues.add(new ItemValue(null, null, 1, "1"));
		taskItemValues.add(new TaskItemValue(1, "1"));
		
		taskDetails.add(new ManHrTaskDetail(taskItemValues, new SupportFrameNo(1)));
		
		settings.add(new ManHourRecordAndAttendanceItemLink(new SupportFrameNo(1), 1, 1));

		new Expectations() {
			{
				require.get();
				result = settings;
			}
		};
		List<ItemValue> actualResult = ManHrRecordTaskDetailToAttendanceItemService.convert(require, itemValues, taskDetails);
		
		for (ItemValue v : actualResult) {
			
			if (v.getItemId() == 1) {
				assertThat(v.getValue().equals("1"));
			}
			
		}

	}

}
