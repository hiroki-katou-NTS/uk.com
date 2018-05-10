package nts.uk.ctx.pereg.app.find.person.setting.selectionitem.selection;

import lombok.Value;

@Value
public class SelectionQuery {

	private String selectionItemId;
	private int selectionItemRefType;

	private String baseDate;
	
	private String categoryCode;

}
