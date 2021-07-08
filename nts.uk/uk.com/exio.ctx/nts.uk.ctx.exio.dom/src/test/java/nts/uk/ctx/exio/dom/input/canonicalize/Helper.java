package nts.uk.ctx.exio.dom.input.canonicalize;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;

import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.DataItemList;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.IntermediateResult;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.reviseddata.RevisedDataRecord;

public class Helper {

	public static class Dummy {
		public static final int ROW_NO = -1;
		
		public static final String COMPANY_ID = null;
	}
	
	public static ExecutionContext context(ImportingMode mode) {
		return new ExecutionContext("companyId", "settingCode", ImportingGroupId.TASK, mode);
	}
	
	public static class Revised {
		
		public static RevisedDataRecord item(int itemNo, Object value) {
			val item = new DataItem(itemNo, value);
			return new RevisedDataRecord(Dummy.ROW_NO, new DataItemList(Arrays.asList(item)));
		}
		
	}
	
	public static class ItemList {
		public static DataItemList of(DataItem... item) {
			return new DataItemList(Arrays.asList(item));
		}
	}
	
	public static class Interm {
		
		public static IntermediateResult of(
				DataItemList itemsAfterCanonicalize, DataItemList itemsBeforeCanonicalize, DataItemList itemsNotCanonicalize) {
			return new IntermediateResult(Dummy.ROW_NO, itemsAfterCanonicalize, itemsBeforeCanonicalize, itemsNotCanonicalize);
		}
	}
	
	public static AssertsIntermediateResult asserts(IntermediateResult result) {
		return new AssertsIntermediateResult(result);
	}

	@RequiredArgsConstructor
	public static class AssertsIntermediateResult {
		
		private final IntermediateResult result;
		
		public AssertsIntermediateResult equal(int itemNo, String expected) {
			String actual = result.getItemByNo(itemNo).get().getString();
			assertThat(actual).isEqualTo(expected);
			
			return this;
		}
	}
}
