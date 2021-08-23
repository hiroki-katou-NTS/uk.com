package nts.uk.ctx.exio.dom.input.errors;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;

@Value
public class ExternalImportError {

	int csvRowNo;
	Integer itemNo;
	String message;
	
	public static ExternalImportError record(int csvRowNo, String message) {
		return new ExternalImportError(csvRowNo, null, message);
	}
	
	public static ExternalImportError of(int csvRowNo, ItemError error) {
		return new ExternalImportError(csvRowNo, error.getItemNo(), error.getMessage());
	}
	
	public static ExternalImportError of(RecordError error) {
		return new ExternalImportError(error.getCsvRowNo(), null, error.getMessage());
	}
	
	public boolean isRecord() {
		return itemNo == null;
	}
	
	public boolean isItem() {
		return !isRecord();
	}
	
	public void toText(RequireToText require, ExecutionContext context, StringBuilder sb) {
		
		sb.append(String.format("%08d 行", csvRowNo));
		
		sb.append("\t");
		
		if (isItem()) {
			String itemName = require.getImportableItem(context.getDomainId(), itemNo).getItemName();
			sb.append(itemName);
		}
		
		sb.append("\t");
		
		sb.append(message);
		
		sb.append("\r\n");
	}
	
	public static interface RequireToText {
		
		ImportableItem getImportableItem(ImportingDomainId domainId, int itemNo);
	}
}
