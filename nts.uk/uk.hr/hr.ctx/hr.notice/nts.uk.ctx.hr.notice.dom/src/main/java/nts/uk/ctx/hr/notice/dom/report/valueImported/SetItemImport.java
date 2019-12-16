package nts.uk.ctx.hr.notice.dom.report.valueImported;

import java.util.List;

import lombok.Getter;

@Getter
public class SetItemImport extends ItemTypeStateImport {
	
	private List<String> items;
	
	private SetItemImport(List<String> items) {
		super();
		this.itemType = ItemTypeImport.SET_ITEM.value;
		this.items = items;
	}

	public static SetItemImport createFromJavaType(List<String> items) {
		return new SetItemImport(items);
	}
}
