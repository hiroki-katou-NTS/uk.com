package nts.uk.ctx.at.schedule.app.command.shiftmanagement.shiftwork.shiftpalet;

import lombok.Value;

@Value
public class DuplicateComShiftPaletCommand {
	
	//ページ元番号
	private int originalPage;
	//ページ先番号
	private int destinationPage;
	//ページ先名称
	private String destinationName;
	//上書きするか
	private boolean overwrite;

}
