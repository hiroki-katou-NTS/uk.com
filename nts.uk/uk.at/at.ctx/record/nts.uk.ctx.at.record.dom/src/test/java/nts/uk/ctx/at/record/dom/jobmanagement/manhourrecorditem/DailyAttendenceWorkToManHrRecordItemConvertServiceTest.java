package nts.uk.ctx.at.record.dom.jobmanagement.manhourrecorditem;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.ouen.SupportFrameNo;

/**
 * 
 * @author tutt
 *
 */
@RunWith(JMockit.class)
public class DailyAttendenceWorkToManHrRecordItemConvertServiceTest {
	
	@Injectable
	private DailyAttendenceWorkToManHrRecordItemConvertService.Require require;
	
	@Injectable
	private DailyAttendenceWorkToManHrRecordItemConvertService service;
	
	@Test
	public void test1() {
		
		List<ManHourRecordAndAttendanceItemLink> links = new ArrayList<>();
		links.add(new ManHourRecordAndAttendanceItemLink(new SupportFrameNo(1), 1, 1));
		
		List<Integer> itemIds = new ArrayList<>();
		itemIds.add(1);
		itemIds.add(2);
		
		ManHrRecordConvertResult expectedResult = new ManHrRecordConvertResult(GeneralDate.today(), new ArrayList<>(), new ArrayList<>());
		
		new Expectations() {
			{
				require.get(itemIds);
				result = links;
			}
		};
		
		ManHrRecordConvertResult actualResult = service.convert(require, null, itemIds);
				
		assertThat(expectedResult.equals(actualResult));
		
	}
}
