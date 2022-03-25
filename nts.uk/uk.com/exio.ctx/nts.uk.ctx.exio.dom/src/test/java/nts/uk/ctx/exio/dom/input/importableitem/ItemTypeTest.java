package nts.uk.ctx.exio.dom.input.importableitem;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import lombok.val;
import nts.arc.time.GeneralDate;

public class ItemTypeTest {

	@Test
	public void date() {
		val actual = ItemType.DATE.parse("2000/1/1");
		assertThat(actual.getRight()).isEqualTo(GeneralDate.ymd(2000, 1, 1));
	}

}
