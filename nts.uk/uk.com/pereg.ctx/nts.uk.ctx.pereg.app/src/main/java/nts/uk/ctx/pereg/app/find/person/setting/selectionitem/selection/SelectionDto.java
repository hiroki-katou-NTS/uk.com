package nts.uk.ctx.pereg.app.find.person.setting.selectionitem.selection;

import lombok.Value;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.Selection;

/**
 * 
 * @author tuannv
 *
 */

@Value
public class SelectionDto {
	private String selectionID;
	private String histId;
	private String selectionCD;
	private String selectionName;
	private String externalCD;
	private String memoSelection;
	public static SelectionDto fromDomainSelection(Selection domain) {
		return new SelectionDto(domain.getSelectionID(), domain.getHistId(), domain.getExternalCD().v(),
				domain.getSelectionName().v(), domain.getExternalCD().v(), domain.getMemoSelection().v());

	}

}
