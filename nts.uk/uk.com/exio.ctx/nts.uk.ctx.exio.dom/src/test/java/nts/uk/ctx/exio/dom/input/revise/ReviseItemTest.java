package nts.uk.ctx.exio.dom.input.revise;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.importableitem.ItemType;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;

public class ReviseItemTest {
	@Mocked ReviseValue reviseValue;
	
	@Injectable
	private ReviseItem.Require require;
	
	static class Dummy{
		private static String COM_ID = "comcomcom";
		private static ExternalImportCode EXI_CODE = new ExternalImportCode("exiexiexi");
		private static int EXI_ITEM_NO = 765;
		private static ItemType ITEM_TYPE = ItemType.STRING;
		private static ReviseValue REVISE_VALUE;
		private static ImportingGroupId EXI_GROUP = ImportingGroupId.TASK;
		private static ImportingMode EXI_MODE = ImportingMode.INSERT_ONLY;
		private static ExecutionContext EXE_CONTEXT = new ExecutionContext(COM_ID, EXI_CODE.v(), EXI_GROUP, EXI_MODE);
		private static RevisedValueResult REVISE_V_RESULT;
	}

	@Test
	public void NotCodeConvert() {
		
		val reviseItem = new ReviseItem(
				Dummy.COM_ID, 
				Dummy.EXI_CODE, 
				Dummy.EXI_ITEM_NO, 
				Dummy.ITEM_TYPE,
				Dummy.REVISE_VALUE, 
				Optional.empty());
		
		String targetValue = "value";
		
		new Expectations() {{
			reviseValue.revise(targetValue);
			result = Dummy.REVISE_V_RESULT;
		}};
		
		RevisedItemResult result = reviseItem.revise(require, Dummy.EXE_CONTEXT, targetValue);
		
		assertThat(result.isSuccess()).isEqualTo(true);
	}

}
