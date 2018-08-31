package nts.uk.ctx.at.shared.app.command.specialholiday.grantinformation;

import lombok.Value;

@Value
public class ElapseYearCommand {
	/** 付与テーブルコード */
	private String grantDateCode;
	
	private int elapseNo;
	
	/** 付与テーブルコード */
	private int grantedDays;
	
	/** 付与テーブルコード */
	private int months;
	
	/** 付与テーブルコード */
	private int years;
}
