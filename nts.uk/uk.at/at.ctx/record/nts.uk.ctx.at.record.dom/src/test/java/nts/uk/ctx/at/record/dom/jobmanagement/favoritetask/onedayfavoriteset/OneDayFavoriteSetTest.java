package nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.onedayfavoriteset;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.daily.ouen.SupportFrameNo;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.favoritetaskitem.FavoriteTaskName;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.favoritetaskitem.TaskContent;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work.WorkCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author tutt
 *
 */
public class OneDayFavoriteSetTest {
	
	@Test
	public void getter() {
		List<TaskContentForEachSupportFrame> taskContents = new ArrayList<>();
		taskContents.add(new TaskContentForEachSupportFrame(new SupportFrameNo(1), new TaskContent(1, new WorkCode("123"))));
		
		List<TaskBlockDetailContent> taskBlockDetailContents = new ArrayList<>();
		TaskBlockDetailContent content = new TaskBlockDetailContent(new TimeWithDayAttr(1), new TimeWithDayAttr(2), taskContents);
		
		taskBlockDetailContents.add(content);
		OneDayFavoriteSet set = new OneDayFavoriteSet("sId", "favId", new FavoriteTaskName("name"), taskBlockDetailContents);
		
		NtsAssert.invokeGetters(set);
	}

}
