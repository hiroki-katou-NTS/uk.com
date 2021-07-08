package nts.uk.ctx.exio.dom.input.canonicalize.methods;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import lombok.val;
import nts.uk.ctx.exio.dom.input.DataItemList;
import nts.uk.ctx.exio.dom.input.setting.assembly.RevisedDataRecord;

public class IntermediateResultTest {

	@Test
	public void create() {
		
		val source = new RevisedDataRecord(0, newList().add(0, "a").add(1, "b").add(2, "c"));
		val canonicalizedItems = newList().add(10, "AAA").add(12, "CCC");
		
		val actual = IntermediateResult.create(
				source,
				canonicalizedItems,
				0, 2);

		assertThat(actual.getItemsAfterCanonicalize())
			.isEqualTo(canonicalizedItems);
		
		assertThat(actual.getItemsBeforeCanonicalize())
			.isEqualTo(newList().add(0, "a").add(2, "c"));
		
		assertThat(actual.getItemsNotCanonicalize())
			.isEqualTo(newList().add(1, "b"));
	}
	
	@Test
	public void addCanonicalized() {
		
		val source = new RevisedDataRecord(0, newList().add(0, "a").add(1, "b").add(2, "c"));
		val canonicalizedItems = newList().add(10, "AAA");
		val target = IntermediateResult.create(
				source, canonicalizedItems, 0);
		
		val actual = target.addCanonicalized(newList().add(12, "CCC"), 2);

		assertThat(actual.getItemsAfterCanonicalize())
			.isEqualTo(newList().add(10, "AAA").add(12, "CCC"));
		
		assertThat(actual.getItemsBeforeCanonicalize())
			.isEqualTo(newList().add(0, "a").add(2, "c"));
		
		assertThat(actual.getItemsNotCanonicalize())
			.isEqualTo(newList().add(1, "b"));
	}
	
	@Test
	public void getItemByNo() {
		val source = new RevisedDataRecord(0, newList().add(0, "a").add(1, "b").add(2, "c"));
		
		// 項目0は正準化したときに同じ項目を変更する形とする
		val canonicalizedItems = newList().add(0, "AAA").add(12, "CCC");
		
		val target = IntermediateResult.create(
				source, canonicalizedItems, 0, 2);

		assertThat(target.getItemByNo(0).get().getString()).isEqualTo("AAA");
		assertThat(target.getItemByNo(1).get().getString()).isEqualTo("b");
		assertThat(target.getItemByNo(2).get().getString()).isEqualTo("c");
		assertThat(target.getItemByNo(12).get().getString()).isEqualTo("CCC");
	}

	private static DataItemList newList() {
		return new DataItemList();
	}
}
