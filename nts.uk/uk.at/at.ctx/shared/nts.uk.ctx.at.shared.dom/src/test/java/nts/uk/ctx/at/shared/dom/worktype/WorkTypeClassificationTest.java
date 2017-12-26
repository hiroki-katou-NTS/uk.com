package nts.uk.ctx.at.shared.dom.worktype;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import lombok.val;

public class WorkTypeClassificationTest {
	
	@Test
	public void isWeekDayAttendance() {
		//
		//val target =  new 
		//取得した値
		val actual = WorkTypeClassification.Holiday.isAttendance();
		//予測値
		val expected = false;
		
		assertThat("A",actual,is(expected));	
		
		
		
		val actual2 = WorkTypeClassification.Absence.isAttendance();
		//予測値
		val expected2 = false;
		
		assertThat("B",actual2,is(expected2));
	
		
	}
}
