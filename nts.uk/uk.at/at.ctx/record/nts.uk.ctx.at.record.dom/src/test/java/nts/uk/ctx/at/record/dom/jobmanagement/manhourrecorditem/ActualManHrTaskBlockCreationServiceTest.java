package nts.uk.ctx.at.record.dom.jobmanagement.manhourrecorditem;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.ouen.SupportFrameNo;
import nts.uk.ctx.at.record.dom.daily.timegroup.TaskTimeGroup;
import nts.uk.ctx.at.record.dom.daily.timegroup.TaskTimeZone;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author tutt
 *
 */
@RunWith(JMockit.class)
public class ActualManHrTaskBlockCreationServiceTest {

	@Injectable
	private ActualManHrTaskBlockCreationService.Require require;

	String sId = "employee";
	GeneralDate date = GeneralDate.today();
	
	@Test
	public void test1() {
		
		List<ManHrTaskDetail> taskDetails = new ArrayList<>();
		List<TaskItemValue> taskItemValues = new ArrayList<>();
		
		taskItemValues.add(new TaskItemValue(1, "1"));
		taskItemValues.add(new TaskItemValue(2, "2"));
		
		ManHrTaskDetail detail = new ManHrTaskDetail(taskItemValues, new SupportFrameNo(1));
		ManHrTaskDetail detail2 = new ManHrTaskDetail(taskItemValues, new SupportFrameNo(2));
		taskDetails.add(detail);
		taskDetails.add(detail2);
		
		DailyActualManHrActualTask expectedResult = new DailyActualManHrActualTask(date, new ArrayList<>());
		
		List<TaskTimeZone> timezones = new ArrayList<>();
		timezones.add(new TaskTimeZone(new TimeSpanForCalc(new TimeWithDayAttr(1), new TimeWithDayAttr(10)), new SupportFrameNo(1)));
		
		//if not $作業時間帯グループ.isEmpty
		TaskTimeGroup group = new TaskTimeGroup(sId, date, timezones);

		new Expectations() {
			{
				require.get(sId, date);
				result = Optional.of(group);

			}
		};
		ActualManHrTaskBlockCreationService service = new ActualManHrTaskBlockCreationService();
		DailyActualManHrActualTask actualResult = service.create(require, sId, date,
				taskDetails);

		assertThat(expectedResult.getDate()).isEqualTo(actualResult.getDate());
	}
	
	@Test
	public void test2() {
		
		List<ManHrTaskDetail> taskDetails = new ArrayList<>();
		List<TaskItemValue> taskItemValues = new ArrayList<>();
		
		//if ($開始時刻.isEmpty OR $終了時刻.isEmpty)
		taskItemValues.add(new TaskItemValue(3, "1"));
		taskItemValues.add(new TaskItemValue(4, "2"));
		
		ManHrTaskDetail detail = new ManHrTaskDetail(taskItemValues, new SupportFrameNo(1));
		ManHrTaskDetail detail2 = new ManHrTaskDetail(taskItemValues, new SupportFrameNo(2));
		taskDetails.add(detail);
		taskDetails.add(detail2);
		
		DailyActualManHrActualTask expectedResult = new DailyActualManHrActualTask(date, new ArrayList<>());
		
		List<TaskTimeZone> timezones = new ArrayList<>();
		timezones.add(new TaskTimeZone(new TimeSpanForCalc(new TimeWithDayAttr(1), new TimeWithDayAttr(10)), new SupportFrameNo(1)));
		
		//if not $作業時間帯グループ.isEmpty
		TaskTimeGroup group = new TaskTimeGroup(sId, date, timezones);

		new Expectations() {
			{
				require.get(sId, date);
				result = Optional.of(group);

			}
		};
		ActualManHrTaskBlockCreationService service = new ActualManHrTaskBlockCreationService();
		DailyActualManHrActualTask actualResult = service.create(require, sId, date,
				taskDetails);

		assertThat(expectedResult.getDate()).isEqualTo(actualResult.getDate());
	}
	
	@Test
	public void test3() {
		
		// if $作業時間帯グループ.isEmpty
		new Expectations() {
			{
				require.get(sId, date);
				result = Optional.empty();

			}
		};
		ActualManHrTaskBlockCreationService service = new ActualManHrTaskBlockCreationService();
		DailyActualManHrActualTask expectedResult = new DailyActualManHrActualTask(date, new ArrayList<>());
		DailyActualManHrActualTask actualResult = service.create(require, sId, date,
				new ArrayList<>());
		assertThat(expectedResult.getDate()).isEqualTo(actualResult.getDate());
	}

}
