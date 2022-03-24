package nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.date;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import nts.arc.time.GeneralDate;

public class ExternalImportDateFormatTest {

	@Test
	public void test() {
		GeneralDate actual = ExternalImportDateFormat.YYYY_MM_DD.fromString("2000/1/1");
		assertThat(actual).isEqualTo(GeneralDate.ymd(2000, 1, 1));
	}

}
