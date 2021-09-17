package nts.uk.ctx.exio.dom.input;

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
	public void separate() {
		val target = new DataItemList()
				.add(0, "a")
				.add(1, "b")
				.add(2, "c");

		val list1 = new DataItemList();
		val list2 = new DataItemList();
		target.separate(list1, list2, 0, 2);

		assertThat(list1.size()).isEqualTo(2);
		assertThat(list1.get(0).getString()).isEqualTo("a");
		assertThat(list1.get(1).getString()).isEqualTo("c");
		
		assertThat(list2.size()).isEqualTo(1);
		assertThat(list2.get(0).getString()).isEqualTo("b");
	}
}
