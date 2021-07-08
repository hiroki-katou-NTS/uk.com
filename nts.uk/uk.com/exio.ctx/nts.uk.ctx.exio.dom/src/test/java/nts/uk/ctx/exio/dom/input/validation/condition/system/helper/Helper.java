package nts.uk.ctx.exio.dom.input.validation.condition.system.helper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.DataItemList;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.importableitem.ItemType;
import nts.uk.ctx.exio.dom.input.setting.assembly.RevisedDataRecord;

public class Helper {
	
	public static RevisedDataRecord DUMMY_RECORD(List<DataItem> itemList) {
		return new RevisedDataRecord(0, new DataItemList(itemList));
	}
	
	public static List<ImportableItem> DUMMY_ImportableItems(int itemNo){
		return Arrays.asList(
				new ImportableItem(
						Helper.DUMMY.GROUP_ID,
						itemNo,
						Helper.DUMMY.NAME,
						Helper.DUMMY.ITEM_TYPE,
						Helper.DUMMY.REQUIRED,
						Optional.empty())
			);  
	}
	
	public static List<DataItem> DUMMY_dataItems(int itemNo){
		return Arrays.asList(new DataItem(
				itemNo, 
				Helper.DUMMY.VALUE));
	}
	
	public static class DUMMY{
		public static String COMPANY_ID = "company";
		public static String SETTING_CODE = "settingCode";
		public static ImportingGroupId GROUP_ID = ImportingGroupId.TASK;
		public static int ROW_NO = 999; 
		public static boolean REQUIRED = true; 
		public static Object VALUE = 999;
		public static String NAME = "name";
		public static ItemType ITEM_TYPE = ItemType.TIME_POINT;
		public static ImportingMode MODE =  ImportingMode.INSERT_ONLY;
		public static ExecutionContext CONTEXT = new ExecutionContext(COMPANY_ID, SETTING_CODE, GROUP_ID, MODE);
	}
}
