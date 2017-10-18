package find.person.setting.selectionitem.selection;

import lombok.Value;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection.SelectionItemOrder;

/**
 * 
 * @author tuannv
 *
 */

@Value
public class SelectionItemOrderDto {

	private String selectionID;
	private String histId;
	private int disporder;
	private int initSelection;

	public static SelectionItemOrderDto fromSelectionOrder(SelectionItemOrder domain) {
		return new SelectionItemOrderDto(domain.getSelectionID(), domain.getHistId(), domain.getDisporder().v(),
				domain.getInitSelection().value);
	}
}
