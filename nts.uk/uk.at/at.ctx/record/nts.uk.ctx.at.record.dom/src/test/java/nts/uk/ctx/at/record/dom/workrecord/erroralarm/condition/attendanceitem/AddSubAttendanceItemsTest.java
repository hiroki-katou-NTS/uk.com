package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;

public class AddSubAttendanceItemsTest {

	@Test
	public void calculate_True() {
		AddSubAttendanceItems items = createItems();
		
		assertEquals(items.calculate(c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList())), 20d);
	}
	
	private AddSubAttendanceItems createItems(){
		return new AddSubAttendanceItems(Arrays.asList(1, 2, 3, 4, 5, 6, 7), Arrays.asList(8));
	}
}
