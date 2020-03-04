package nts.uk.ctx.hr.notice.app.find.report;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ActionRole {
	
	HIDDEN(1),
	
	VIEW_ONLY(2),
	
	EDIT(3);
	
	public final int value;
	
}