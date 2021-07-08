package nts.uk.ctx.exio.infra.entity.input.importableitem.group;

import static nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import lombok.val;
import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;
import nts.uk.ctx.exio.dom.input.group.ImportingGroup;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.group.TransactionUnit;

@RunWith(Enclosed.class)
public class XimctGroupTest {
	
	public static class ToDomain {
		
		@Test
		public void test() {
			
			val source = new ImportingGroup(
					ImportingGroupId.TASK,
					"name",
					new HashSet<>(Arrays.asList(UPDATE_ONLY, DELETE_RECORD_BEFOREHAND)),
					TransactionUnit.ALL);
			
			val entity = XimctGroup.toEntity(source);
			val restored = entity.toDomain();
			
			assertThat(source).isEqualTo(restored);
		}
		
	}

	public static class ToBits {

		@Test
		public void single() {
			test(0b0001, INSERT_ONLY);
			test(0b0010, UPDATE_ONLY);
			test(0b0100, DELETE_RECORD_BEFOREHAND);
			test(0b1000, DELETE_GROUP_BEFOREHAND);
		}
		
		@Test
		public void complex() {
			test(0b0011, INSERT_ONLY, UPDATE_ONLY);
			test(0b1100, DELETE_RECORD_BEFOREHAND, DELETE_GROUP_BEFOREHAND);
			test(0b1001, INSERT_ONLY, DELETE_GROUP_BEFOREHAND);
			test(0b1111, DELETE_RECORD_BEFOREHAND, DELETE_GROUP_BEFOREHAND, INSERT_ONLY, UPDATE_ONLY);
		}
		
		private static void test(int bits, ImportingMode... modes) {
			
			int actual = XimctGroup.AvailableModes.toBits(new HashSet<>(Arrays.asList(modes)));
			assertThat(actual).isEqualTo(bits);
		}
	}

}
