package nts.uk.ctx.at.record.dom.jobmanagement.manhourrecorditem;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.assertj.core.condition.AnyOf;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.ouen.SupportFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;

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
	
	@Injectable
	private AttendanceItemConvertFactory attendanceItemConvertFactory;

	
//	@Test
//	public void test1() {
//		DailyRecordToAttendanceItemConverter converter = attendanceItemConvertFactory.createDailyConverter();
//		converter.setData(null);
//		
//		List<ManHourRecordAndAttendanceItemLink> links = new ArrayList<>();
//		links.add(new ManHourRecordAndAttendanceItemLink(new SupportFrameNo(1), 1, 1));
//		links.add(new ManHourRecordAndAttendanceItemLink(new SupportFrameNo(2), 2, 2));
//		links.add(new ManHourRecordAndAttendanceItemLink(new SupportFrameNo(3), 2, 3));
//		
//		List<Integer> itemIds = new ArrayList<>();
//		itemIds.add(1);
//		itemIds.add(2);
//		
//		ManHrRecordConvertResult expectedResult = new ManHrRecordConvertResult(null, null, null);
//		
//		new Expectations() {
//			{
//				require.get(itemIds);
//				result = links;
//			}
//		};
//		
//		ManHrRecordConvertResult actualResult = service.convert(require, null, itemIds);
//				
//		assertThat(expectedResult.equals(actualResult));
//		
//	}
}
