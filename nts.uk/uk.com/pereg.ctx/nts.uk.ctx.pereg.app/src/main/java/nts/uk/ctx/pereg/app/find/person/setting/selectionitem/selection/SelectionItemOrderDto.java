package nts.uk.ctx.pereg.app.find.person.setting.selectionitem.selection;

import lombok.Value;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.Selection;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder.SelectionItemOrder;

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
	private String selectionName;
	private String selectionCD;
	private String externalCD;
	private String memoSelection;
	private int codeType;

	public static SelectionItemOrderDto fromSelectionOrder(SelectionItemOrder domain, Selection selectionDomain,
			int codeType) {
		return new SelectionItemOrderDto(domain.getSelectionID(), domain.getHistId(), domain.getDisporder().v(),
				domain.getInitSelection().value,
				selectionDomain != null ? selectionDomain.getSelectionName().v() : null,
				selectionDomain != null ? selectionDomain.getSelectionCD().v() : null,
				selectionDomain != null ? selectionDomain.getExternalCD().v() : null,
				selectionDomain != null ? selectionDomain.getMemoSelection().v() : null, 
				codeType);
	}

}
