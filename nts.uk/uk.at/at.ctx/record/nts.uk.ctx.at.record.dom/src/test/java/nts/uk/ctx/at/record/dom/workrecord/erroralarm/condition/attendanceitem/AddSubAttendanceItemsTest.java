package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class AddSubAttendanceItemsTest {

	@Test
	public void calculate_True() {
		AddSubAttendanceItems items = createItems();
		
		assertEquals(items.calculate(c -> c), 20);
	}
	
	private AddSubAttendanceItems createItems(){
		return new AddSubAttendanceItems(Arrays.asList(1, 2, 3, 4, 5, 6, 7), Arrays.asList(8));
	}
}
