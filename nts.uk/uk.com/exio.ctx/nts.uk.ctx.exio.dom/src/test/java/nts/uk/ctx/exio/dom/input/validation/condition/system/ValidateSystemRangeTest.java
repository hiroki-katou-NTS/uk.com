package nts.uk.ctx.exio.dom.input.validation.condition.system;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.validation.condition.system.helper.Helper;

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
			require.getDefinition(Helper.DUMMY.COMPANY_ID,Helper.DUMMY.GROUP_ID);
			result = DUMMY_IMPORTABLEITEMS;
		}};
		
		List<DataItem> DUMMY_DATEITEMS = Helper.DUMMY_dataItems(sameItemNo);
		
		boolean result = ValidateSystemRange.validate(
				require, 
				Helper.DUMMY.CONTEXT, 
				Helper.DUMMY_RECORD(DUMMY_DATEITEMS));
		
		assertThat(result).isTrue();
	}
	
	@Test
	public void notExist_ImportableItemByItemNo() {

		int diffItemNo1 = 1;
		int diffItemNo2 = 2;
		List<ImportableItem> DUMMY_IMPORTABLEITEMS = Helper.DUMMY_ImportableItems(diffItemNo1);
		
		new Expectations() {{
			require.getDefinition(Helper.DUMMY.COMPANY_ID,Helper.DUMMY.GROUP_ID);
			result = DUMMY_IMPORTABLEITEMS;
		}};
		
		List<DataItem> DUMMYDATEITEMS = Helper.DUMMY_dataItems(diffItemNo2);
		try {
			 ValidateSystemRange.validate(
						require, 
						Helper.DUMMY.CONTEXT, 
						Helper.DUMMY_RECORD(DUMMYDATEITEMS));
			 assertThat(false).isTrue();
		}catch(NoSuchElementException e) {
			assertThat(true).isTrue();
		}
	}
}
