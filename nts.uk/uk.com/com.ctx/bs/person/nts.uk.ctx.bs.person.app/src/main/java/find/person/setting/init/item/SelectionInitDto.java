package find.person.setting.init.item;

import lombok.Value;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection.Selection;

@Value
public class SelectionInitDto {
	private String selectionId;
	private String selectionCode;
	private String selectionName;

	public static SelectionInitDto fromDomainSelection(Selection domain) {
		return new SelectionInitDto(domain.getSelectionID(), domain.getSelectionCD().toString(),
				domain.getSelectionName().toString());

	}
}
