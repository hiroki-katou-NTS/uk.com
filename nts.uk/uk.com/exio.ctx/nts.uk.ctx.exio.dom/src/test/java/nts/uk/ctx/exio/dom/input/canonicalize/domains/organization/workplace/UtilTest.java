package nts.uk.ctx.exio.dom.input.canonicalize.domains.organization.workplace;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

@RunWith(Enclosed.class)
public class UtilTest {
	
	public static class collectDuplicated {

		@Test
		public void 空リスト() {
			
			val actual = Util.collectDuplicated(Collections.emptyList());
			
			assertThat(actual.isEmpty()).isTrue();
		}
		
		@Test
		public void 重複なし() {

			val periods = Arrays.asList(
					period(5, 6),
					period(7, 8),
					period(3, 4)
					);
			
			val actual = Util.collectDuplicated(periods);
			
			assertThat(actual.isEmpty()).isTrue();
		}
		
		@Test
		public void 重複あり1() {
			
			val periods = Arrays.asList(
					period(5, 6),
					period(7, 8),
					period(4, 5)
					);
			
			val actual = Util.collectDuplicated(periods);
			
			assertThat(actual.size()).isEqualTo(2);
			assertThat(actual.contains(periods.get(0))).isTrue();
			assertThat(actual.contains(periods.get(2))).isTrue();
		}

		
		@Test
		public void 重複あり2() {
			
			val periods = Arrays.asList(
					period(5, 6),
					period(7, 8),
					period(3, 9)
					);
			
			val actual = Util.collectDuplicated(periods);
			
			assertThat(actual.size()).isEqualTo(2);
			assertThat(actual.contains(periods.get(0))).isTrue();
			assertThat(actual.contains(periods.get(2))).isTrue();
		}
		
		
	}
	
	private static DatePeriod period(int start, int end) {
		return new DatePeriod(date(start), date(end));
	}
	
	private static GeneralDate date(int day) {
		return GeneralDate.ymd(2000, 1, day);
	}


}
