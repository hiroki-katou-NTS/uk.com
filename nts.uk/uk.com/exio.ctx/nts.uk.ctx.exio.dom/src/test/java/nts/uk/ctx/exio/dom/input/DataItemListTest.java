package nts.uk.ctx.exio.dom.input;

import static nts.arc.time.GeneralDate.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import lombok.val;

public class DataItemListTest {

	@Test
	public void getItemByNo() {
		val target = new DataItemList()
				.add(0, "a")
				.add(1, "b")
				.add(2, "c");
		
		String c = target.getItemByNo(2).get().getString();
		assertThat(c).isEqualTo("c");
	}

	@Test
	public void getItemByNoEmpty() {
		val target = new DataItemList()
				.add(0, "a")
				.add(1, "b")
				.add(2, "c");
		
		assertThat(target.getItemByNo(3).isPresent()).isFalse();
	}

	@Test
	public void getPeriod() {
		val target = new DataItemList()
				.add(0, "a")
				.add(1, ymd(2000, 1, 1))
				.add(2, ymd(2000, 1, 5));
		
		val actual = target.getPeriod(1, 2);
		assertThat(actual.start()).isEqualTo(ymd(2000, 1, 1));
		assertThat(actual.end()).isEqualTo(ymd(2000, 1, 5));
	}
}
