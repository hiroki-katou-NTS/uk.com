package nts.uk.ctx.exio.dom.input;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.Test;

import lombok.SneakyThrows;
import lombok.val;
import nts.arc.time.GeneralDate;

public class DataItemTest {

	@Test
	public void typeString() {
		String value = "a";
		assertGets(DataItem.of(0, value), "getString", value);
	}

	@Test
	public void typeInt() {
		long value = 1L;
		assertGets(DataItem.of(0, value), "getInt", value);
	}

	@Test
	public void typeReal() {
		val value = new BigDecimal(1.5f);
		assertGets(DataItem.of(0, value), "getReal", value);
	}

	@Test
	public void typeDate() {
		val value = GeneralDate.ymd(2000, 1, 1);
		assertGets(DataItem.of(0, value), "getDate", value);
	}

	@SneakyThrows
	private static void assertGets(
			DataItem target,
			String suitableGetterName,
			Object expectedValue) {
		
		for (Method method : DataItem.class.getDeclaredMethods()) {
			
			// getter以外は無視
			if (method.getName().indexOf("get") != 0) {
				continue;
			}
			
			// フィールドのgetterも対象外
			if (Arrays.asList("getItemNo", "getType", "getValue").contains(method.getName())) {
				continue;
			}
			
			if (method.getName().equals(suitableGetterName)) {
				Object actual = method.invoke(target);
				assertThat(actual).isEqualTo(expectedValue);
			}
			
			else {
				assertThatThrownBy(() -> {
					method.invoke(target);
				}, method.getName());
			}
		}
	}
}
