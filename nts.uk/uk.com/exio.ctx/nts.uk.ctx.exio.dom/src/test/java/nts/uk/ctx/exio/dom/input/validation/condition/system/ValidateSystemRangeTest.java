package nts.uk.ctx.exio.dom.input.validation.condition.system;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.validation.condition.system.helper.Helper;
import nts.uk.ctx.exio.dom.input.validation.system.ValidateSystemRange;

public class ValidateSystemRangeTest {

	@Injectable
	private ValidateSystemRange.SystemRequire require;
	
	@Test
	public void exist_ImportableItemByItemNo() {
		
		new MockUp<ImportableItem>() {
			@Mock
			public boolean validate(DataItem dataItem) {
				return true;
			}
		};

		int sameItemNo = 0;
		List<ImportableItem> DUMMY_IMPORTABLEITEMS = Helper.DUMMY_ImportableItems(sameItemNo);
		
		new Expectations() {{
			require.getImportableItem(Helper.DUMMY.GROUP_ID, anyInt);
			result = DUMMY_IMPORTABLEITEMS;
		}};
		
		List<DataItem> DUMMY_DATEITEMS = Helper.DUMMY_dataItems(sameItemNo);
		
		boolean result = ValidateSystemRange.validate(
				require, 
				Helper.DUMMY.CONTEXT, 
				Helper.DUMMY_RECORD(DUMMY_DATEITEMS));
		
		assertThat(result).isTrue();
	}
}
