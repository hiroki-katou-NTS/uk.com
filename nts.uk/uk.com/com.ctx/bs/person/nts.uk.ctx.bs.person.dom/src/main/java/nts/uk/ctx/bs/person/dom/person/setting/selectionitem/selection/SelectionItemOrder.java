package nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

@AllArgsConstructor
@Getter
public class SelectionItemOrder {
	private String selectionID;
	private String histId;
	private Disporder disporder;
	private InitSelection initSelection;

	public static SelectionItemOrder selectionItemOrder(String selectionID, String histId, int disporder,
			int initSelection) {
		return new SelectionItemOrder(histId, histId, new Disporder(disporder),
				EnumAdaptor.valueOf(initSelection, InitSelection.class));
	}
}
