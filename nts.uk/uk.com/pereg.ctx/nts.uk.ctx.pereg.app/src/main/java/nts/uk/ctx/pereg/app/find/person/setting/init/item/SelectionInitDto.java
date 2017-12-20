package nts.uk.ctx.pereg.app.find.person.setting.init.item;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.Selection;

@Getter
@Setter
public class SelectionInitDto {
	private String selectionId;
	private String selectionCode;
	private String selectionName;
	private String selectionItemName;

	public static SelectionInitDto fromDomainSelection(Selection domain) {
		return new SelectionInitDto(domain.getSelectionID(), domain.getSelectionCD().toString(),
				domain.getSelectionName().toString(), domain.getSelectionItemName());

	}
	
	public static SelectionInitDto fromDomainSelection1(Selection domain) {
		return new SelectionInitDto(domain.getSelectionID(), domain.getSelectionCD().toString(),
				domain.getSelectionName().toString(),
				domain.getSelectionItemName());

	}

	public SelectionInitDto(String selectionId, String selectionCode, String selectionName) {
		super();
		this.selectionId = selectionId;
		this.selectionCode = selectionCode;
		this.selectionName = selectionName;
	}

	public SelectionInitDto(String selectionId, String selectionCode, String selectionName, String selectionItemName) {
		super();
		this.selectionId = selectionId;
		this.selectionCode = selectionCode;
		this.selectionName = selectionName;
		this.selectionItemName = selectionItemName;
	}
}
