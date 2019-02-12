package nts.uk.file.at.app.export.attendanceitemprepare;

import lombok.Data;

@Data
public class AttItemNameByAuthId {
	/** 利用する */
	private boolean toUse;
	/** 他人が変更できる */
	private boolean canBeChangedByOthers;
	/** 本人が変更できる */
	private boolean youCanChangeIt;
	private int attendanceItemId;
	private String authId;
}
