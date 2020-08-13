package nts.uk.ctx.at.schedule.app.command.shiftmanagement.shiftwork.shiftpalet;

import lombok.Value;

@Value
public class DuplicateOrgShiftPaletCommand {

	// ページ元番号
	private int originalPage;
	
	// 対象組織  ----------
	private String targetID;
	//WORKPLACE:0, WORKPLACE_GROUP1
	private int targetUnit;
	//---------------
	
	// ページ先番号
	private int destinationPage;
	// ページ先名称
	private String destinationName;
	// 上書きするか
	private boolean overwrite;

}
